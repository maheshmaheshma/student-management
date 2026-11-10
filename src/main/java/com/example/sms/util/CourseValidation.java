package com.example.sms.util;

public final class CourseValidation {
    private CourseValidation() {}

    public static boolean isValidName(String name) { return name != null && !name.isBlank(); }
    public static boolean isValidCredits(int credits) { return credits >= 1 && credits <= 10; }
}
