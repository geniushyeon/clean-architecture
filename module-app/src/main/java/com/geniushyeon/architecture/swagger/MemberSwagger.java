package com.geniushyeon.architecture.swagger;

import com.geniushyeon.architecture.dto.request.SignUpRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member", description = "Member API")
public interface MemberSwagger {

    @Operation(summary = "회원가입")
    ResponseEntity<Void> signUp(SignUpRequestDTO requestDTO);
}
