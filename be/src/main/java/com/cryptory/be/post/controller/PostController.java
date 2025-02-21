package com.cryptory.be.post.controller;

import com.cryptory.be.post.dto.CreatePostDto;
import com.cryptory.be.post.dto.PostDetailDto;
import com.cryptory.be.post.dto.PostDto;
import com.cryptory.be.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coins/{coinId}/posts")
public class PostController {

    private final PostService postService;


//    @GetMapping
//    public ResponseEntity<List<PostDto>> getPosts(@PathVariable("coinId") Long coinId) {
//        List<PostDto> posts = postService.getPosts(coinId);
//        return ResponseEntity.ok(posts);
//    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPost(@PathVariable("coinId") Long coinId,
                                                 @PathVariable("postId") Long postId) {
        PostDetailDto post = postService.getPost(coinId, postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(Principal principal,
                                              @PathVariable("coinId") Long coinId,
                                              @RequestPart(value = "post") CreatePostDto createPostDto,
                                              @RequestPart List<MultipartFile> files) {
        files.forEach(file -> log.info("file: {}", file.getOriginalFilename()));
        PostDto post = postService.createPost(coinId, principal.getName(), createPostDto, files);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("coinId") Long coinId,
                                        @PathVariable("postId") Long postId) {
        postService.deletePost(coinId, postId);

        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }
}
