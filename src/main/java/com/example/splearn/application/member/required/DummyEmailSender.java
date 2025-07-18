package com.example.splearn.application.member.required;

import com.example.splearn.domain.shared.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {

    }
}
