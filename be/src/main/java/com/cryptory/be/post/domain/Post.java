package com.cryptory.be.post.domain;

import com.cryptory.be.coin.domain.Coin;
import com.cryptory.be.global.entity.BaseTimeEntity;
import com.cryptory.be.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Long viewCnt;

    private Long likeCnt;

    private boolean isDeleted;

    // todo: 코인 엔티티 추가되면 주석 해제
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "coin_id")
//    private Coin coin;

    @Builder
    public Post(String title, String body, User user) {
        this.title = title;
        this.body = body;
        this.user = user;

        this.viewCnt = 0L;
        this.likeCnt = 0L;
        this.isDeleted = false;
    }

    public void delete() {
        this.isDeleted = true;
    }

    public boolean isNotDeleted() {
        return !this.isDeleted;
    }

    public void update(String title, String body) {
        this.title = title != null ? title : this.title;
        this.body = body != null ? body : this.body;
    }

    public void increaseViewCnt() {
        this.viewCnt++;
    }

}
