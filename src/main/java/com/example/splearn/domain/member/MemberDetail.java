package com.example.splearn.domain.member;

import com.example.splearn.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Entity
@Getter
//@ToString(callSuper = true)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

    @Embedded
    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();

        return memberDetail;
    }

    void setActivatedAt() {
        Assert.isTrue(activatedAt == null, "ActivatedAt 이미 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivatedAt() {
        Assert.isTrue(deactivatedAt == null, "DeactivatedAt 이미 설정되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    public void updateInfo(MemberInfoUpdateRequest request) {
        this.profile = new Profile(request.introduction());
        this.introduction = Objects.requireNonNull(request.profileAddress());
    }
}
