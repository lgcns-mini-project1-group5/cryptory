package com.cryptory.be.post.repository;

import com.cryptory.be.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCoinId(Long coinId, Pageable pageable);

    long countByCoinId(Long coinId);
}
