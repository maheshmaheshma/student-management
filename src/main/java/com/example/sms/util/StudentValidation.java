package com.example.sms.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/** Centralized, testable validation for student fields. */
public final class StudentValidation {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private StudentValidation() {}
    public static boolean isValidEmail(String email) { return email != null && EMAIL.matcher(email.trim()).matches(); }
    public static boolean isValidName(String name) { return name != null && !name.isBlank(); }
    public static boolean isValidAge(int age) { return age >= 15 && age <= 100; }
    public static boolean isValidYear(int year) { return year >= 1 && year <= 8; }
    public static boolean isValidMarks(BigDecimal marks) { return marks != null && marks.compareTo(BigDecimal.ZERO) >= 0 && marks.compareTo(new BigDecimal("100")) <= 0; }
    public static String normalizeEmail(String email) { if (!isValidEmail(email)) throw new IllegalArgumentException("A valid, non-null email is required."); return email.trim().toLowerCase(java.util.Locale.ROOT); }
}
