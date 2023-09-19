package com.geniushyeon.architecture.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 로그인 성공 테스트용 임시 클래스
@RequiredArgsConstructor
@RestController
public class HomeController {

    @RequestMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello");
    }
}
