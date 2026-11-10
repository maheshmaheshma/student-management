package com.example.sms.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Student(int id, String name, int age, String email, String phone,
                      String department, int year, LocalDate admissionDate,
                      BigDecimal marks, Status status) {}
