package com.example.splearn.application.required;

import com.example.splearn.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.example.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static com.example.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        String duplicateEmail = "jh@naver.com";
        Member member1 = Member.register(createMemberRegisterRequest(duplicateEmail), createPasswordEncoder());
        Member member2 = Member.register(createMemberRegisterRequest(duplicateEmail), createPasswordEncoder());

        memberRepository.save(member1);
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}