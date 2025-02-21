package com.cryptory.be.post.service;

import com.cryptory.be.global.util.DateFormat;
import com.cryptory.be.global.util.FileUtils;
import com.cryptory.be.post.domain.Post;
import com.cryptory.be.post.domain.PostFile;
import com.cryptory.be.post.dto.*;
import com.cryptory.be.post.repository.PostFileRepository;
import com.cryptory.be.post.repository.PostRepository;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final ModelMapper modelMapper;
    private final FileUtils fileUtils;

//    public List<PostDto> getPosts(Long coinId) {
//        List<Post> posts = postRepository.findAllByCoinId(coinId);
//
//        return posts.stream()
//                .filter(Post::isNotDeleted)
//                .map(post -> new PostDto(
//                        post.getId(),
//                        post.getTitle(),
//                        post.getUser().getNickname(),
//                        DateFormat.formatDate(post.getCreatedAt())
//                )).toList();
//    }

    @Transactional
    public PostDto createPost(Long coinId, String nickname, CreatePostDto createPostDto, List<MultipartFile> files) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Post post = postRepository.save(Post.builder()
                .title(createPostDto.getTitle())
                .body(createPostDto.getBody())
                .user(user)
                .build());

        if (files != null) {
            for (MultipartFile file : files) {
                String storedDir = fileUtils.saveFile(file);

                String fileType = storedDir.substring(storedDir.lastIndexOf(".") + 1);

                PostFile postFile = PostFile.builder()
                        .originalFilename(file.getOriginalFilename())
                        .storedDir(storedDir)
                        .storedFilename(fileUtils.getStoredFileName(storedDir))
                        .post(post)
                        .fileType(fileType)
                        .build();

                postFileRepository.save(postFile);

            }
        }

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getUser().getNickname(),
                DateFormat.formatDate(post.getCreatedAt())
        );
    }

    @Transactional
    public void deletePost(Long coinId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        post.delete();
    }

    public PostDetailDto getPost(Long coinId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        List<PostFileDto> postFiles = postFileRepository.findAllByPostId(postId).stream()
                .map(postFile -> new PostFileDto(
                        postFile.getId(),
                        postFile.getOriginalFilename(),
                        postFile.getStoredDir(),
                        DateFormat.formatDate(postFile.getCreatedAt())
                )).toList();

        return new PostDetailDto(post.getTitle(), post.getBody(), post.getUser().getNickname(), DateFormat.formatDate(post.getCreatedAt()), postFiles);
    }

    @Transactional
    public void updatePost(Long coinId, Long postId, UpdatePostDto updatePostDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        post.update(updatePostDto.getTitle(), updatePostDto.getBody());

        // todo: 파일 업데이트(클라이언트 요청에 따라)
    }
}
