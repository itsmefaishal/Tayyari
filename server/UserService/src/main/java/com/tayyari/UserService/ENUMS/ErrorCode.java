package com.tayyari.UserService.ENUMS;

public enum ErrorCode {
    USER_ALREADY_ENROLLED("User is already enrolled in this quiz"),
    QUIZ_NOT_AVAILABLE("Quiz is not available"),
    INVALID_REQUEST("Invalid request data"),
    NO_AVAILABLE_ENROLLMENT("Enrollment Not found"),
    QUIZ_INACTIVE("Quiz is not Active Yet"),
    UNAUTHORIZED_ACCESS("You are not authorized for this Transaction");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

