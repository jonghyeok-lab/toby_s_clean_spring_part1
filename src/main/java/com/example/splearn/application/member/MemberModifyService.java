package com.example.splearn.application.member;

import com.example.splearn.application.member.provided.MemberFinder;
import com.example.splearn.application.member.provided.MemberRegister;
import com.example.splearn.application.member.required.EmailSender;
import com.example.splearn.application.member.required.MemberRepository;
import com.example.splearn.domain.member.DuplicateEmailException;
import com.example.splearn.domain.member.Member;
import com.example.splearn.domain.member.MemberRegisterRequest;
import com.example.splearn.domain.member.PasswordEncoder;
import com.example.splearn.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.findById(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등룍을 완룍해주세요.", "아래 링크를 클릭해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        memberRepository.findByEmail(new Email(registerRequest.email()))
                .ifPresent(member -> {throw new DuplicateEmailException(); });
    }
}
