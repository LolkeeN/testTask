package com.vasyl.testTask.controller;

import com.vasyl.testTask.converters.UserMapper;
import com.vasyl.testTask.dto.UserDto;
import com.vasyl.testTask.dto.UserDtoWithPassword;
import com.vasyl.testTask.entity.User;
import com.vasyl.testTask.service.impl.UserServiceImpl;
import com.vasyl.testTask.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserRestController {
    private UserServiceImpl userService;
    private UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(x -> userMapper.toDto(x))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void createUser(@ModelAttribute UserDtoWithPassword userDto, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        userDto.setAvatar(fileName);
        User user = userService.createUser(userDto);
        String uploadDir = saveImageAndGetUploadDir(file, fileName, user);
        user.setAvatar(uploadDir);
        userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public void changeUser(@PathVariable int id, @RequestBody UserDtoWithPassword userDto) {
        userService.updateUser(id, userDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        return userMapper.toDto(user);
    }

    @PutMapping("/{id}/avatar")
    public void changeUserAvatar(@PathVariable int id, @RequestParam("file") MultipartFile file) throws IOException {
        User user = userService.findById(id);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uploadDir = saveImageAndGetUploadDir(file, fileName, user);
        user.setAvatar(uploadDir);
        userService.createUser(user);
    }

    private static String saveImageAndGetUploadDir(MultipartFile file, String fileName, User user) throws IOException {
        String uploadDir = "./user-photos/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, file);
        return uploadDir;
    }
}
