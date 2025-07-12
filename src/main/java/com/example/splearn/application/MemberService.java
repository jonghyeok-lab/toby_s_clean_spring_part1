package com.example.splearn.application;

import com.example.splearn.application.provided.MemberRegister;
import com.example.splearn.application.required.EmailSender;
import com.example.splearn.application.required.MemberRepository;
import com.example.splearn.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

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

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등룍을 완룍해주세요.", "아래 링크를 클릭해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        memberRepository.findByEmail(new Email(registerRequest.email()))
                .ifPresent(member -> {throw new DuplicateEmailException(); });
    }
}
