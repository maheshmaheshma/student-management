package com.example.sms.model;

import java.math.BigDecimal;
import java.sql.Date;

public record Student(int id, String name, int age, String email, String phone,
                      String department, int year, Date admissionDate,
                      BigDecimal marks, String status) {}
