package com.example.splearn.application.provided;

import com.example.splearn.domain.Member;

public interface MemberFinder {

    Member findById(Long memberId);
}
