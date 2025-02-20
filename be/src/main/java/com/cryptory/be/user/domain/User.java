package com.cryptory.be.user.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String provider;

    private boolean isDenied;

    @Builder
    public User(String nickname, String imageUrl, String provider) {
        this.role = Role.USER;
        this.isDenied = false;

        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }

    public void deny() {
        this.isDenied = true;
    }
}
