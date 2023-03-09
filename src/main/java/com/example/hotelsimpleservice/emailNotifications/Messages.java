package com.example.hotelsimpleservice.emailNotifications;

import lombok.Getter;

@Getter
public enum Messages {
    CREATE_ACCOUNT_MESSAGE("Congratulations, you have successfully registered on our service"),
    UPDATE_ACCOUNT_DATA_MESSAGE("Congratulations, you have successfully updated your account on our service"),
    DELETE_ACCOUNT_MESSAGE("You have successfully deleted your account from our service"),
    CREATE_BOOKING_MESSAGE("Congratulations, You have successfully created a booking on our service"),
    DELETE_BOOKING_MESSAGE("You have successfully deleted a booking on our service"),
    UPDATE_BOOKING_MESSAGE("Congratulations, You have successfully updated a booking on our service");

    private String message;

    Messages(String message) {
        this.message = message;
    }
}