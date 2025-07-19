package com.example.splearn.application.member.provided;

import com.example.splearn.domain.member.Member;
import com.example.splearn.domain.member.MemberInfoUpdateRequest;
import com.example.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest updateRequest);
}
