package com.example.splearn.application.member.required;

import com.example.splearn.domain.member.Profile;
import com.example.splearn.domain.shared.Email;
import com.example.splearn.domain.member.Member;
import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("""
    select m from Member m where m.detail.profile = :profile
    """)
    Optional<Member> findByProfile(Profile profile);
}
