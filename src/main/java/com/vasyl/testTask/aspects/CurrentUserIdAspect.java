package com.vasyl.testTask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.SoftException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

@Aspect
public class CurrentUserIdAspect {
    @Before("execution(public * *(.., @CurrentUserIdAnnotation (*), ..))")
    public void interceptMethodsWithAnnotatedParameters(JoinPoint thisJoinPoint) {
        MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[][] annotations;
        try {
            annotations = thisJoinPoint.getTarget().getClass().
                    getMethod(methodName, parameterTypes).getParameterAnnotations();
        } catch (Exception e) {
            throw new SoftException(e);
        }
        int i = 0;
        for (Object arg : thisJoinPoint.getArgs()) {
            for (Annotation annotation : annotations[i]) {
                if (annotation.annotationType() == CurrentUserIdAspect.class) {
                    System.out.println();
                }
            }
            i++;
        }
    }
}