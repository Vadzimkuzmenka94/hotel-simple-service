package com.example.hotelsimpleservice.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Service
@ControllerAdvice(basePackages = "com.example.hotelsimpleservice")
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public AppExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<CustomErrorResponse> handleProjectException(AppException ex) {
        CustomErrorResponse apiResponse = new CustomErrorResponse();
        apiResponse.setCode(ex.getErrorCode().getCode());
        apiResponse.setMessage(messageSource.getMessage(ex.getErrorCode().getCode(), ex.getParams(), LocaleContextHolder.getLocale()));
        apiResponse.setTime(LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, ex.getErrorCode().getHttpStatus());
    }
}