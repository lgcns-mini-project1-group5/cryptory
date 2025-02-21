package com.cryptory.be.post.controller;

import com.cryptory.be.post.dto.CreatePostDto;
import com.cryptory.be.post.dto.PostDto;
import com.cryptory.be.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{coinId}/posts")
public class PostController {

    private final PostService postService;


    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable("coinId") Long coinId) {
        List<PostDto> posts = postService.getPosts(coinId);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@PathVariable("coinId") Long coinId,
                                                @RequestBody CreatePostDto createPostDto) {
//        PostDto post = postService.createPost(coinId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable("coinId") Long coinId,
                                              @PathVariable("postId") Long postId) {
        postService.deletePost(coinId, postId);
    }
}
