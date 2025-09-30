package com.tayyari.UserService.ENUMS;

public enum AttemptStatus {
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    ABANDONED("abandoned"),
    TIMEOUT("timeout");

    private final String value;

    AttemptStatus(String value) {
        this.value = value;
    }
}
