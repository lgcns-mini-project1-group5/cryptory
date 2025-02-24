package com.cryptory.be.user.domain;

import com.cryptory.be.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // providerId는 인증 서버마다 다르고, 중복 위험이 있으므로(maybe) UUID로 unique 값
    private String userId;

    private String nickname;

    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    // 리소스 서버가 준 사용자의 고유 식별값
    private String providerId;

    // 리소스 서버 이름
    private String providerName;

    private boolean isDenied;

    @Column(nullable = true) // 관리자용 패스워드
    private String password;

    @Builder
    public User(String nickname, String imageUrl, String providerId, String providerName) {
        this.role = Role.USER;
        this.isDenied = false;
        this.userId = UUID.randomUUID().toString();

        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
        this.providerName = providerName;
    }

    // 관리자 생성시 사용되는 정적 팩토리 메서드
    public static User createAdmin(String userId, String encodedPassword, String nickname) {
        User admin = new User();
        admin.isDenied = false;
        admin.userId = userId;
        admin.nickname = nickname;
        admin.password = encodedPassword;
        admin.role = Role.ADMIN;
        return admin;
    }

    public void deny(boolean status) {
        this.isDenied = status;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
