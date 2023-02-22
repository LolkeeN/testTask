package com.vasyl.testTask.controller;

import com.vasyl.testTask.dto.AvatarDto;
import com.vasyl.testTask.exceptions.AvatarContentReadException;
import com.vasyl.testTask.service.AvatarService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users/avatar")
@AllArgsConstructor
public class AvatarController {

    private static final String CONTENT_DISPOSITION_FORMAT = "attachment; filename=%s";
    private final AvatarService avatarService;

    @PostMapping("/{userId}")
    public void upload(@PathVariable Integer userId, @RequestParam("avatar") MultipartFile avatar) {
        AvatarDto avatarDto = new AvatarDto();

        avatarDto.setUserId(userId);
        avatarDto.setFileName(avatar.getOriginalFilename());
        avatarDto.setContent(getAvatarContent(avatar));

        avatarService.createAvatar(avatarDto);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Resource> download(@PathVariable Integer userId) throws IOException {

        AvatarDto avatarDto = avatarService.get(userId);

        Resource resource = new ByteArrayResource(avatarDto.getContent());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, getContentDispositionValue(avatarDto.getFileName()));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(avatarDto.getContent().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private String getContentDispositionValue(String fileName) {
        return String.format(CONTENT_DISPOSITION_FORMAT, fileName);
    }

    @DeleteMapping("/{userId}")
    public void deleteAvatar(@PathVariable Integer userId) {
        avatarService.delete(userId);
    }

    private byte[] getAvatarContent(MultipartFile avatar) {
        try {
            return avatar.getBytes();
        } catch (IOException e) {
            throw new AvatarContentReadException("The content of avatar cannot be read", e);
        }
    }
}
