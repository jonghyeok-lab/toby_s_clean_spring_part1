package com.example.splearn.application.member;

import com.example.splearn.application.member.provided.MemberFinder;
import com.example.splearn.application.member.provided.MemberRegister;
import com.example.splearn.application.member.required.EmailSender;
import com.example.splearn.application.member.required.MemberRepository;
import com.example.splearn.domain.member.*;
import com.example.splearn.domain.shared.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.findById(memberId);

        member.deactivate();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest updateRequest) {
        Member member = memberFinder.findById(memberId);

        checkDuplicateProfile(member, updateRequest.profileAddress());

        member.updateInfo(updateRequest);

        return memberRepository.save(member);
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if (profileAddress.isEmpty()) return;
        Profile currentProfile = member.getDetail().getProfile();
        if (currentProfile != null && currentProfile.address().equals(profileAddress)) return;

        if (memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 프로필 주소입니다.");
        }

    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등룍을 완룍해주세요.", "아래 링크를 클릭해주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        memberRepository.findByEmail(new Email(registerRequest.email()))
                .ifPresent(member -> {throw new DuplicateEmailException("중복~"); });
    }
}
