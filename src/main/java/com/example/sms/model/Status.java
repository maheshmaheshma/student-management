package com.example.sms.model;

public enum Status {
    ACTIVE, INACTIVE, GRADUATED;

    public static Status parse(String value) {
        try {
            return Status.valueOf(value.trim().toUpperCase(java.util.Locale.ROOT));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Status must be ACTIVE, INACTIVE or GRADUATED.");
        }
    }
}
