package com.example.sms.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseValidationTest {
    @Test void validatesCourseName() {
        assertTrue(CourseValidation.isValidName("Java"));
        assertFalse(CourseValidation.isValidName(" "));
        assertFalse(CourseValidation.isValidName(null));
    }

    @Test void validatesCreditBoundaries() {
        assertTrue(CourseValidation.isValidCredits(1));
        assertTrue(CourseValidation.isValidCredits(10));
        assertFalse(CourseValidation.isValidCredits(0));
        assertFalse(CourseValidation.isValidCredits(11));
    }
}
