package com.cryptory.be.post.service;

import com.cryptory.be.global.util.DateFormat;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostDto> getPosts(Long coinId) {
        List<Post> posts = postRepository.findAllByCoinId(coinId);

        return posts.stream()
                .map(post -> new PostDto(
                        post.getId(),
                        post.getTitle(),
                        post.getUser().getNickname(),
                        DateFormat.formatDate(post.getCreatedAt())
                )).toList();
    }

//    @Transactional
//    public PostDto createPost(Long coinId) {
//
//
//    }
}
