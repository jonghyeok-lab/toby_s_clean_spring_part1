package com.example.splearn.adapter;

import com.example.splearn.domain.member.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({DuplicateEmailException.class, IllegalArgumentException.class})
    public ProblemDetail emailException(DuplicateEmailException exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    private ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        // 표준 RFC9457 에 정의.
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());

        problemDetail.setProperty("exception", exception.getClass().getSimpleName());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
