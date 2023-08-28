package com.geniushyeon.architecture.service;

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
import org.springframework.stereotype.Service;

import java.util.List;

import static com.geniushyeon.architecture.enumerable.EnumErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ClientException.NotFound(MEMBER_NOT_FOUND));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new ClientException.NotFound(MEMBER_NOT_FOUND));

        // TODO: 추후 권한 관련 로직 추가
        return new User(member.getUsername(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
