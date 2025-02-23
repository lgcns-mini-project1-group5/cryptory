package com.cryptory.be.user.controller;

import com.cryptory.be.user.dto.UpdateUserDto;
import com.cryptory.be.user.dto.UserInfoDto;
import com.cryptory.be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDto> getUser(Principal principal) {
        log.info("principal: {}", principal);
        UserInfoDto userInfoDto = userService.getUser(principal.getName());
        return ResponseEntity.ok(userInfoDto);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.ok("delete success");
    }

    @PatchMapping("/me/nickname")
    public ResponseEntity<?> updateNickname(Principal principal, @RequestBody UpdateUserDto updateUserDto) {
        log.info("principal: {}", principal);
        userService.updateNickname(principal.getName(), updateUserDto.getNickname());
        return ResponseEntity.ok("update success");
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateImage(Principal principal, @RequestPart MultipartFile file) {
        userService.updateImage(principal.getName(), file);
        return ResponseEntity.ok("update success");
    }
}
