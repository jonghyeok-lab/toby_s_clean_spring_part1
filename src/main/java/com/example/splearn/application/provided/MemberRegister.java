package com.example.splearn.application.provided;

import com.example.splearn.domain.Member;
import com.example.splearn.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);
}
