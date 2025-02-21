package com.cryptory.be.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostDto {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    private MultipartFile image;
}
