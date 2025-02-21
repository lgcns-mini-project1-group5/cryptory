package com.cryptory.be.user.controller;

import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        UserInfoDto userInfoDto = userService.getUser(principal.getName());
        return ResponseEntity.ok(userInfoDto);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.ok("delete success");
    }
}
