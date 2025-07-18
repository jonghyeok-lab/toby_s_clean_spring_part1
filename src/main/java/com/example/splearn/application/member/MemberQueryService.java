package com.example.splearn.application.member;

import com.example.splearn.application.member.provided.MemberFinder;
import com.example.splearn.application.member.required.MemberRepository;
import com.example.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional(readOnly = true)
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found ID: " + memberId));
    }
}
