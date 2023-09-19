package com.geniushyeon.architecture.service;

import com.geniushyeon.architecture.dto.request.SignUpRequestDTO;
import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.enumerable.EnumErrorCode;
import com.geniushyeon.architecture.exception.ClientException;
import com.geniushyeon.architecture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.geniushyeon.architecture.enumerable.EnumErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new ClientException.NotFound(MEMBER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(MEMBER_NOT_FOUND.getMessage()));

        return new User(member.getUsername(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Transactional
    public void signUp(SignUpRequestDTO requestDTO) {
        Optional<Member> byUsername = memberRepository.findByUsername(requestDTO.getUsername());

        if (byUsername.isPresent()) {
            throw new ClientException.Conflict(MEMBER_ALREADY_EXISTS);
        }

        Member member = requestDTO.toEntity();
        member.encodePassword(passwordEncoder);

        memberRepository.save(member);
        log.info("회원가입 완료");
    }
}
