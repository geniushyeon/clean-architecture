package com.geniushyeon.architecture.service;

import com.geniushyeon.architecture.dto.response.JwtResponseDTO;
import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.util.JwtExpiration;
import com.geniushyeon.architecture.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    private final int ACCESS_TOKEN_EXPIRATION = 60;
    private final int REFRESH_TOKEN_EXPIRATION = 120;

    public JwtResponseDTO createJwt(Member member) {
        String accessToken = jwtUtil.createAccessToken(member, JwtExpiration.minutesOf(ACCESS_TOKEN_EXPIRATION));
        String refreshToken = jwtUtil.createRefreshToken(member, JwtExpiration.minutesOf(REFRESH_TOKEN_EXPIRATION));

        return new JwtResponseDTO(accessToken, refreshToken);
    }

}
