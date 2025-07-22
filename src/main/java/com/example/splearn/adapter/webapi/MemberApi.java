package com.example.splearn.adapter.webapi;

import com.example.splearn.application.member.provided.MemberRegister;
import com.example.splearn.domain.member.Member;
import com.example.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public Member registerMember(@RequestBody @Valid MemberRegisterRequest registerRequest) {
        Member register = memberRegister.register(registerRequest);

        return register;
    }
}
