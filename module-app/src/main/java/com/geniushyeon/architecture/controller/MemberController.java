package com.geniushyeon.architecture.controller;

import com.geniushyeon.architecture.dto.request.SignUpRequestDTO;
import com.geniushyeon.architecture.service.MemberService;
import com.geniushyeon.architecture.swagger.MemberSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController implements MemberSwagger {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDTO requestDTO) {
        memberService.signUp(requestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
