package com.cryptory.be.user.service;

import com.cryptory.be.user.domain.Role;
import com.cryptory.be.user.domain.User;
import com.cryptory.be.user.dto.PrincipalUserDetails;
import com.cryptory.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("username: {}", username);

        User admin = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Admin"));

        if (admin.getRole() == Role.ADMIN) {
            return new PrincipalUserDetails(admin);
        } else {
            log.error("Not Admin");
            throw new UsernameNotFoundException("Not Admin");
        }
    }
}
