package com.example.sms.dao;

import com.example.sms.db.Database;
import com.example.sms.model.Student;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentDao {
    private Student map(ResultSet r) throws SQLException {
        return new Student(r.getInt("student_id"), r.getString("student_name"), r.getInt("age"),
            r.getString("email"), r.getString("phone_number"), r.getString("department"),
            r.getInt("student_year"), r.getDate("admission_date"), r.getBigDecimal("marks"), r.getString("status"));
    }
    public void add(Student s) throws SQLException {
        String q = "INSERT INTO students(student_id,student_name,age,email,phone_number,department,student_year,admission_date,marks,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement(q)) {
            p.setInt(1,s.id()); p.setString(2,s.name()); p.setInt(3,s.age()); p.setString(4,s.email());
            p.setString(5,s.phone()); p.setString(6,s.department()); p.setInt(7,s.year());
            p.setDate(8,s.admissionDate()==null?new Date(System.currentTimeMillis()):s.admissionDate());
            p.setBigDecimal(9,s.marks()); p.setString(10,s.status()); p.executeUpdate();
        }
    }
    public List<Student> all() throws SQLException { return query("SELECT * FROM students ORDER BY student_id", null); }
    public Optional<Student> byId(int id) throws SQLException {
        List<Student> xs=query("SELECT * FROM students WHERE student_id=?", p->p.setInt(1,id));
        return xs.stream().findFirst();
    }
    public List<Student> byName(String name) throws SQLException {
        return query("SELECT * FROM students WHERE student_name ILIKE ? ORDER BY student_name", p->p.setString(1,"%"+name+"%"));
    }
    public List<Student> byCourseName(String courseName) throws SQLException {
        if (courseName == null || courseName.isBlank()) return Collections.emptyList();
        return query("SELECT DISTINCT s.* FROM students s JOIN enrollments e ON e.student_id=s.student_id JOIN courses c ON c.course_id=e.course_id WHERE c.course_name ILIKE ? ORDER BY s.student_name",
            p -> p.setString(1, "%" + courseName.trim() + "%"));
    }
    public List<Student> byDepartment(String dept) throws SQLException {
        return query("SELECT * FROM students WHERE department ILIKE ? ORDER BY student_name", p->p.setString(1,dept));
    }
    public List<Student> byYear(int year) throws SQLException {
        return query("SELECT * FROM students WHERE student_year=? ORDER BY student_name", p->p.setInt(1,year));
    }
    public List<Student> aboveMarks(BigDecimal marks) throws SQLException {
        return query("SELECT * FROM students WHERE marks>? ORDER BY marks DESC", p->p.setBigDecimal(1,marks));
    }
    public List<Student> marksRange(BigDecimal low, BigDecimal high) throws SQLException {
        return query("SELECT * FROM students WHERE marks BETWEEN ? AND ? ORDER BY marks DESC", p->{p.setBigDecimal(1,low);p.setBigDecimal(2,high);});
    }
    public boolean update(int id, String name, int age, String email, String phone, String dept, int year, BigDecimal marks, String status) throws SQLException {
        String q="UPDATE students SET student_name=?,age=?,email=?,phone_number=?,department=?,student_year=?,marks=?,status=? WHERE student_id=?";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setString(1,name);p.setInt(2,age);p.setString(3,email);p.setString(4,phone);p.setString(5,dept);
            p.setInt(6,year);p.setBigDecimal(7,marks);p.setString(8,status);p.setInt(9,id);return p.executeUpdate()>0;
        }
    }
    public boolean delete(int id) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM students WHERE student_id=?")){
            p.setInt(1,id);return p.executeUpdate()>0;
        }
    }
    public void reports() throws SQLException {
        try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery(
            "SELECT COUNT(*) total, AVG(marks) avg_marks, MIN(marks) min_marks, MAX(marks) max_marks FROM students")){
            if(r.next()) System.out.printf("Total: %d | Average: %.2f | Lowest: %.2f | Highest: %.2f%n",
                r.getInt("total"),r.getDouble("avg_marks"),r.getDouble("min_marks"),r.getDouble("max_marks"));
        }
        reportQuery("Student count by department","SELECT department, COUNT(*) AS total FROM students GROUP BY department ORDER BY department");
        reportQuery("Average marks by department","SELECT department, ROUND(AVG(marks),2) AS average FROM students GROUP BY department ORDER BY department");
        reportQuery("Students without courses","SELECT s.student_id,s.student_name FROM students s LEFT JOIN enrollments e ON e.student_id=s.student_id WHERE e.student_id IS NULL ORDER BY s.student_id");
    }
    public void reportQuery(String title, String sql) throws SQLException {
        System.out.println("\n-- "+title+" --");
        try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){
            ResultSetMetaData m=r.getMetaData(); int n=m.getColumnCount();
            while(r.next()){ for(int i=1;i<=n;i++) System.out.print(m.getColumnLabel(i)+"="+r.getString(i)+(i<n?" | ":"")); System.out.println(); }
        }
    }
    private interface Binder { void bind(PreparedStatement p) throws SQLException; }
    private List<Student> query(String sql, Binder binder) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            if(binder!=null)binder.bind(p);
            try(ResultSet r=p.executeQuery()){List<Student> out=new ArrayList<>();while(r.next())out.add(map(r));return out;}
        }
    }
    public void print(List<Student> students) {
        if(students.isEmpty()){System.out.println("No students found.");return;}
        students.forEach(System.out::println);
    }
}
