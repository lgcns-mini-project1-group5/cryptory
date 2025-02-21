package com.cryptory.be.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetailDto {

    private String title;
    private String body;
    private String nickname;
    private String createdAt;
    private List<PostFileDto> files;

    public PostDetailDto(String title, String body, String nickname, String createdAt, List<PostFileDto> postFiles) {
        this.title = title;
        this.body = body;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.files = postFiles;
    }
}
