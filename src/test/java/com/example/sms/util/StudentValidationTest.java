package com.example.sms.util;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class StudentValidationTest {
 @Test void acceptsValidEmail(){assertTrue(StudentValidation.isValidEmail("mahesh@example.com"));}
 @Test void rejectsNullEmail(){assertFalse(StudentValidation.isValidEmail(null));}
 @Test void rejectsMalformedEmail(){assertFalse(StudentValidation.isValidEmail("not-an-email"));}
 @Test void normalizesEmail(){assertEquals("mahesh@example.com",StudentValidation.normalizeEmail(" Mahesh@Example.com "));}
 @Test void rejectsNullEmailWhenNormalizing(){assertThrows(IllegalArgumentException.class,()->StudentValidation.normalizeEmail(null));}
 @Test void validatesAgeBoundaries(){assertTrue(StudentValidation.isValidAge(15));assertTrue(StudentValidation.isValidAge(100));assertFalse(StudentValidation.isValidAge(14));}
 @Test void validatesMarksRange(){assertTrue(StudentValidation.isValidMarks(new BigDecimal("0")));assertTrue(StudentValidation.isValidMarks(new BigDecimal("100")));assertFalse(StudentValidation.isValidMarks(new BigDecimal("100.1")));assertFalse(StudentValidation.isValidMarks(null));}
 @Test void rejectsBlankName(){assertFalse(StudentValidation.isValidName("  "));}
 @Test void rejectsBlankEmail(){assertFalse(StudentValidation.isValidEmail("  "));}
 @Test void validatesYearBoundaries(){assertTrue(StudentValidation.isValidYear(1));assertTrue(StudentValidation.isValidYear(8));assertFalse(StudentValidation.isValidYear(0));assertFalse(StudentValidation.isValidYear(9));}
 @Test void rejectsNullMarks(){assertFalse(StudentValidation.isValidMarks(null));}
}
