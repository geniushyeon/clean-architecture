package com.geniushyeon.architecture.service;

import com.geniushyeon.architecture.dto.response.JwtResponseDTO;
import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.entity.RefreshToken;
import com.geniushyeon.architecture.repository.RefreshTokenRepository;
import com.geniushyeon.architecture.util.JwtExpiration;
import com.geniushyeon.architecture.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final int ACCESS_TOKEN_EXPIRATION = 60;
    private final int REFRESH_TOKEN_EXPIRATION = 120;

    public JwtResponseDTO createJwt(Member member) {
        String accessToken = jwtUtil.createAccessToken(member, JwtExpiration.minutesOf(ACCESS_TOKEN_EXPIRATION));
        String refreshToken = jwtUtil.createRefreshToken(member, JwtExpiration.minutesOf(REFRESH_TOKEN_EXPIRATION));

        refreshTokenRepository.save(new RefreshToken(refreshToken, member.getId()));

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtUtil.validateAccessToken(token);
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        User principal = new User(claims.get("memberId").toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
