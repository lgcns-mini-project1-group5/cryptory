package com.cryptory.be.post.service;

import com.cryptory.be.post.domain.Post;
import com.cryptory.be.post.dto.CreatePostDto;
import com.cryptory.be.post.dto.PostDto;
import com.cryptory.be.post.repository.PostRepository;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PostDto createPost(String nickname, CreatePostDto writePostDto) {



        return modelMapper.map(postRepository.save(post), PostDto.class);
    }
}
