package com.example.splearn.domain.member;

public record MemberInfoUpdateRequest(
        String nickname,
        String profileAddress,
        String introduction
) {
}
