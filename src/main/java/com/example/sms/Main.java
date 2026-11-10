package com.example.sms;

import com.example.sms.dao.*;
import com.example.sms.model.Student;
import com.example.sms.util.StudentValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final Scanner in = new Scanner(System.in);
    private static final StudentDao students = new StudentDao();
    private static final CourseDao courses = new CourseDao();
    private static final EnrollmentDao enrollments = new EnrollmentDao();

    public static void main(String[] args) {
        try { Class.forName("org.postgresql.Driver"); }
        catch(ClassNotFoundException e){System.err.println("PostgreSQL JDBC driver missing. Run through Maven.");return;}
        while(true) {
            menu(); int choice=integer("Choice: ");
            try {
                switch(choice) {
                    case 1 -> addStudent();
                    case 2 -> students.print(students.all());
                    case 3 -> System.out.println(students.byId(integer("Student ID: ")).orElse(null));
                    case 4 -> students.print(students.byName(input("Name contains: ")));
                    case 5 -> updateStudent();
                    case 6 -> System.out.println(students.delete(integer("Student ID: "))?"Deleted.":"Student not found.");
                    case 7 -> courses.add(input("Course name: "),input("Department: "),integer("Credits: "));
                    case 8 -> courses.list();
                    case 9 -> enrollments.enroll(integer("Student ID: "),integer("Course ID: "));
                    case 10 -> System.out.println(enrollments.remove(integer("Student ID: "),integer("Course ID: "))?"Enrollment removed.":"Enrollment not found.");
                    case 11 -> enrollments.listForStudent(integer("Student ID: "));
                    case 12 -> students.print(students.byDepartment(input("Department: ")));
                    case 13 -> students.print(students.byYear(integer("Year: ")));
                    case 14 -> enrollments.studentsByCourse(integer("Course ID: "));
                    case 15 -> students.print(students.aboveMarks(decimal("Marks greater than: ")));
                    case 16 -> students.print(students.marksRange(decimal("Minimum marks: "),decimal("Maximum marks: ")));
                    case 17 -> students.reports();
                    case 18 -> enrollments.countsByCourse();
                    case 19 -> students.reportQuery("Sort by marks","SELECT student_id,student_name,marks FROM students ORDER BY marks DESC");
                    case 20 -> students.reportQuery("Sort by name","SELECT student_id,student_name FROM students ORDER BY student_name");
                    case 21 -> students.reportQuery("Sort by admission date","SELECT student_id,student_name,admission_date FROM students ORDER BY admission_date DESC");
                    case 22 -> { System.out.println("Goodbye."); return; }
                    case 23 -> students.print(students.byCourseName(input("Course name contains: ")));
                    default -> System.out.println("Invalid menu choice.");
                }
            } catch(SQLException e) {
                log.error("Database operation failed", e); System.out.println("Database operation failed. Check application logs and database configuration.");
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid input: "+e.getMessage());
            }
        }
    }
    private static void menu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1 Add Student | 2 View All | 3 Search by ID | 4 Search by Name");
        System.out.println("5 Update Student | 6 Delete Student | 7 Add Course | 8 View Courses");
        System.out.println("9 Enroll | 10 Remove Enrollment | 11 Student Enrollments | 12 By Department");
        System.out.println("13 By Year | 14 Students by Course | 15 Above Marks | 16 Marks Range");
        System.out.println("17 Student Reports | 18 Course Counts | 19 Sort by Marks | 20 Sort by Name");
        System.out.println("21 Sort by Admission Date | 22 Exit | 23 Search Students by Course");
    }
    private static void addStudent() throws SQLException {
        int id=integer("Student ID: "); String name=input("Name: "); int age=integer("Age: ");
        String email=input("Email: "); String phone=input("Phone: "); String dept=input("Department: ");
        int year=integer("Year: "); Date date=Date.valueOf(input("Admission date (YYYY-MM-DD): "));
        BigDecimal marks=decimal("Marks (0-100): "); String status=input("Status ACTIVE/INACTIVE/GRADUATED: ").toUpperCase();
        if(id<=0||!StudentValidation.isValidName(name)||!StudentValidation.isValidAge(age)||!StudentValidation.isValidEmail(email)||phone.isBlank()||dept.isBlank()||!StudentValidation.isValidYear(year)||!StudentValidation.isValidMarks(marks))
            throw new IllegalArgumentException("Please verify ID, name, age, email, phone, department, year and marks.");
        email = StudentValidation.normalizeEmail(email);
        students.add(new Student(id,name,age,email,phone,dept,year,date,marks,status)); System.out.println("Student added.");
    }
    private static void updateStudent() throws SQLException {
        int id=integer("Student ID to update: ");
        String name=input("Name: "); int age=integer("Age: "); String email=StudentValidation.normalizeEmail(input("Email: ")); String phone=input("Phone: "); String department=input("Department: "); int year=integer("Year: "); BigDecimal marks=decimal("Marks: "); String status=input("Status: ").toUpperCase();
        if (!StudentValidation.isValidName(name) || !StudentValidation.isValidAge(age) || phone.isBlank() || department.isBlank() || !StudentValidation.isValidYear(year) || !StudentValidation.isValidMarks(marks)) throw new IllegalArgumentException("Invalid student details.");
        boolean ok=students.update(id,name,age,email,phone,department,year,marks,status);
        System.out.println(ok?"Student updated.":"Student not found.");
    }
    private static String input(String prompt){System.out.print(prompt);return in.nextLine().trim();}
    private static int integer(String prompt){try{return Integer.parseInt(input(prompt));}catch(NumberFormatException e){throw new IllegalArgumentException("Expected an integer.");}}
    private static BigDecimal decimal(String prompt){try{return new BigDecimal(input(prompt));}catch(NumberFormatException e){throw new IllegalArgumentException("Expected a number.");}}
}
