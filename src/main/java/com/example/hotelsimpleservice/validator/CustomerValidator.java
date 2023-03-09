package com.example.hotelsimpleservice.validator;

import com.example.hotelsimpleservice.dto.CustomerDto;
import com.example.hotelsimpleservice.exceptions.AppException;
import com.example.hotelsimpleservice.exceptions.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Service
public class CustomerValidator {
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern CARD_NUMBER_REGEX = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

    public void validate(CustomerDto customerDto) {
        if (customerDto.getLogin() == null |
                customerDto.getLogin().length() >= 20 |
                customerDto.getLogin().length() <= 3) {
            log.error("problem with login validation ");
            throw new AppException(ErrorCode.USER_LOGIN_INVALID);
        }
        Matcher matcherPassword = PASSWORD_REGEX.matcher(customerDto.getPassword());
        if (!matcherPassword.matches()) {
            log.error("problem with password validation ");
            throw new AppException(ErrorCode.USER_PASSWORD_INVALID);
        }
        Matcher matcherEmail = EMAIL_REGEX.matcher(customerDto.getEmail());
        if (!matcherEmail.matches()) {
            log.error("problem with email validation");
            throw new AppException(ErrorCode.USER_EMAIL_INVALID);
        }
        Matcher matcherCardNumber = CARD_NUMBER_REGEX.matcher(customerDto.getCardNumber());
        if (!matcherCardNumber.matches()) {
            log.error("problem with card number validation");
            throw new AppException(ErrorCode.USER_CARD_NUMBER_INVALID);
        }
    }
}