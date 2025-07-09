package com.example.splearn.application.provided;

import com.example.splearn.domain.Member;
import com.example.splearn.domain.MemberRegisterRequest;

public interface MemberRegister {

    Member register(MemberRegisterRequest registerRequest);
}
