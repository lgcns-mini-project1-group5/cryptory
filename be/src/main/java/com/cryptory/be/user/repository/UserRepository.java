package com.cryptory.be.user.repository;

import com.cryptory.be.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    @Query("SELECT u FROM User u WHERE u.providerId = :providerId AND u.providerName = :providerName")
    Optional<User> findByProviderIdAndProviderName(@Param("providerId") String providerId, @Param("providerName") String providerName);

    void deleteByUserId(String userId);
}
