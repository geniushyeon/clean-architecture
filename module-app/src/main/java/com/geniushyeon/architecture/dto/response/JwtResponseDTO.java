package com.geniushyeon.architecture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDTO {

    private String accessToken;
    private String refreshToken;
}
