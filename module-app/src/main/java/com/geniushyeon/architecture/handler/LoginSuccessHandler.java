package com.geniushyeon.architecture.handler;

import com.geniushyeon.architecture.dto.response.JwtResponseDTO;
import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.service.MemberService;
import com.geniushyeon.architecture.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Member member = memberService.getMember(principal.getUsername());
        JwtResponseDTO jwt = tokenService.createJwt(member);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Authorization", jwt.getAccessToken());
        response.setHeader("refreshToken", jwt.getRefreshToken());
        response.sendRedirect("/home");
    }
}
