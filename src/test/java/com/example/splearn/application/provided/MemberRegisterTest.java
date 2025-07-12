package com.example.splearn.application.provided;

import com.example.splearn.SplearnTestConfiguration;
import com.example.splearn.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegisterTest(
        MemberRegister memberRegister,
        EntityManager entityManager
) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        assertThatThrownBy(() -> memberRegister.register(new MemberRegisterRequest("toby@splearn.app", "hi", "ds")))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void activate() {
        var member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
}

