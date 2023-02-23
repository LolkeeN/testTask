package com.vasyl.testTask.aspects;

import com.vasyl.testTask.aspects.annotations.CurrentUserId;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.exceptions.UserNotFoundException;
import com.vasyl.testTask.repository.UserRepository;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class CurrentUserIdAspect {

    @Autowired
    private UserRepository repository;

    @SneakyThrows
    @Around("execution(public * *(.., @com.vasyl.testTask.aspects.annotations.CurrentUserId (*), ..))")
    public Object interceptMethodsWithAnnotatedParameters(ProceedingJoinPoint joinPoint) {
        Annotation[][] annotations = getAnnotations(joinPoint);
        Object[] newArgs = getNewArgs(joinPoint, annotations);
        return joinPoint.proceed(newArgs);
    }

    private static Annotation[][] getAnnotations(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] annotations;
        try {
            annotations = joinPoint.getTarget().getClass().
                    getMethod(methodName, parameterTypes).getParameterAnnotations();
        } catch (Exception e) {
            throw new SoftException(e);
        }
        return annotations;
    }

    private Object[] getNewArgs(ProceedingJoinPoint joinPoint, Annotation[][] annotations) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int i = 0;
        Object[] newArgs = new Object[joinPoint.getArgs().length];
        for (Object arg : joinPoint.getArgs()) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType() == CurrentUserId.class) {
                    newArgs[i] = getUserId(authentication);
                } else {
                    newArgs[i] = arg;
                }
            }
            i++;
        }
        return newArgs;
    }

    private Integer getUserId(Authentication authentication) {
        User user = repository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UserNotFoundException("No user found")
        );
        return user.getId();
    }
}