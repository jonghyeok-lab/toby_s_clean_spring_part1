package com.example.splearn.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Profile(@Column(length = 15) String address) {

    private static final Pattern PROFILE_PATTERN =
            Pattern.compile("[a-z0-9]+");

    public Profile {
        if (!PROFILE_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("프로필 주소가 바르지 않습니다: " + address);
        }

        if (address.length() > 15) throw new IllegalArgumentException("프로필 주소는 최대 15자리 입니다.");
    }

    public String url() {
        return "@" + address ;
    }
}
