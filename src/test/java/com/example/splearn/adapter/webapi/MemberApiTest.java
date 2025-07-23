package com.example.splearn.adapter.webapi;

import com.example.splearn.application.member.provided.MemberRegister;
import com.example.splearn.application.member.required.MemberRepository;
import com.example.splearn.domain.member.Member;
import com.example.splearn.domain.member.MemberFixture;
import com.example.splearn.domain.member.MemberRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // 전파의 REQUIRES_NEW, NESTED / 백그라운드
@RequiredArgsConstructor
public class MemberApiTest {
    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final MemberRepository memberRepository;
    final MemberRegister memberRegister;

    @Test
    void register() throws JsonProcessingException, UnsupportedEncodingException {
        MemberRegisterRequest registerRequest = MemberFixture.createMemberRegisterRequest();
        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcTestResult result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest).exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.id", value -> assertThat(value).isNotNull());
//                .hasPathSatisfying("$.email", value -> assertThat(value).isEqualTo(registerRequest.email()));

        Member response = objectMapper.readValue(result.getResponse().getContentAsString(), Member.class);
        Member found = memberRepository.findById(response.getId()).orElseThrow();
        assertThat(response.getId()).isEqualTo(found.getId());
        assertThat(found.getEmail().address()).isEqualTo(registerRequest.email());
    }

    @Test
    void duplicateEmail() throws JsonProcessingException {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        MemberRegisterRequest registerRequest = MemberFixture.createMemberRegisterRequest();
        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        MvcTestResult result = mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest).exchange();

        assertThat(result)
                .apply(MockMvcResultHandlers.print())
                .hasStatus(HttpStatus.CONFLICT);
    }
    
}
