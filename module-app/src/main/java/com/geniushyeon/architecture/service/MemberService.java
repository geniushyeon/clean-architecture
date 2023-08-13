package com.geniushyeon.architecture.service;

import com.geniushyeon.architecture.entity.Member;
import com.geniushyeon.architecture.enumerable.EnumErrorCode;
import com.geniushyeon.architecture.exception.ClientException;
import com.geniushyeon.architecture.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.geniushyeon.architecture.enumerable.EnumErrorCode.*;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ClientException.NotFound(MEMBER_NOT_FOUND));
    }
}
