package com.geniushyeon.architecture.util;

import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.exception.ClientException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.geniushyeon.architecture.enumerable.EnumErrorCode.*;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    public String createAccessToken(Member member, JwtExpiration expiration) {
        return create(accessTokenSecret, member, expiration);
    }

    public String createRefreshToken(Member member, JwtExpiration expiration) {
        return create(refreshTokenSecret, member, expiration);
    }

    private String create(String secret, Member member, JwtExpiration expiration) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .claim("id", member.getId())
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(expiration.getExpiration())
                .compact();
    }

    public Claims validateAccessToken(String token) {
        return validate(accessTokenSecret, token);
    }

    public Claims validateRefreshToken(String token) {
        return validate(refreshTokenSecret, token);
    }

    private Claims validate(String secret, String token) throws ClientException.Unauthorized {
        if (ObjectUtils.isEmpty(token)) {
            throw new ClientException.Unauthorized(TOKEN_REQUIRED);
        }

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ClientException.Unauthorized(TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new ClientException.Unauthorized(TOKEN_INVALID);
        }
    }

}
