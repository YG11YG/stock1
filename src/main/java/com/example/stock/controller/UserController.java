package com.example.stock.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        // 여기서 principal을 통해 사용자 정보에 접근할 수 있습니다.
        return "User Info: " + principal.getAttributes();
    }
}
