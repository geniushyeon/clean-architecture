package com.geniushyeon.architecture.dto.request;

import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.entity.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .role(UserRole.USER)
                .build();
    }
}
