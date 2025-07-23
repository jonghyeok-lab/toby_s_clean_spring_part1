package com.example.splearn.adapter.webapi;

import com.example.splearn.application.member.provided.MemberRegister;
import com.example.splearn.domain.member.Member;
import com.example.splearn.domain.member.MemberFixture;
import com.example.splearn.domain.member.MemberRegisterRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberApi.class)
@RequiredArgsConstructor
class MemberApiWebMvcTest {

    @MockitoBean
    MemberRegister memberRegister;

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @Test
    void register() throws JsonProcessingException {
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);

        MemberRegisterRequest registerRequest = MemberFixture.createMemberRegisterRequest();
        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.id")
//                .asNumber()
                .isEqualTo(1);
    }

    @Test
    void registerFail() throws JsonProcessingException {
        MemberRegisterRequest registerRequest = MemberFixture.createMemberRegisterRequest("invalid email");
        String jsonRequest = objectMapper.writeValueAsString(registerRequest);

        assertThat(mvcTester.post().uri("/api/members").contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

}