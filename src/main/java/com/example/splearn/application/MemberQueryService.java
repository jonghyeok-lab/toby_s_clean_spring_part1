package com.example.splearn.application;

import com.example.splearn.application.provided.MemberFinder;
import com.example.splearn.application.required.MemberRepository;
import com.example.splearn.domain.Member;
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
