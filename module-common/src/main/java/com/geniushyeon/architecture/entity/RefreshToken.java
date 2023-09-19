package com.geniushyeon.architecture.entity;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 2)
public class RefreshToken {

    @Id
    private String refreshToken;
    private long memberId;
}
