package com.example.sms.dao;

import com.example.sms.db.Database;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnrollmentDao {
    private static final Logger log = LoggerFactory.getLogger(EnrollmentDao.class);
    public void enroll(int studentId, int courseId) throws SQLException {
        try(Connection c=Database.getConnection()){
            boolean original=c.getAutoCommit(); c.setAutoCommit(false);
            try {
                try(PreparedStatement p=c.prepareStatement("SELECT 1 FROM students WHERE student_id=?")){
                    p.setInt(1,studentId);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Student ID does not exist.");}
                }
                try(PreparedStatement p=c.prepareStatement("SELECT 1 FROM courses WHERE course_id=?")){
                    p.setInt(1,courseId);try(ResultSet r=p.executeQuery()){if(!r.next())throw new SQLException("Course ID does not exist.");}
                }
                try(PreparedStatement p=c.prepareStatement("INSERT INTO enrollments(student_id,course_id) VALUES(?,?)")){
                    p.setInt(1,studentId);p.setInt(2,courseId);p.executeUpdate();
                }
                c.commit(); System.out.println("Enrollment saved.");
            } catch(SQLException | RuntimeException ex) {
                try { c.rollback(); } catch(SQLException rollbackEx) { ex.addSuppressed(rollbackEx); }
                throw ex;
            } finally {
                try { c.setAutoCommit(original); }
                catch(SQLException resetEx) { log.warn("Could not restore auto-commit after enrollment", resetEx); }
            }
        }
    }
    public boolean remove(int studentId,int courseId) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM enrollments WHERE student_id=? AND course_id=?")){
            p.setInt(1,studentId);p.setInt(2,courseId);return p.executeUpdate()>0;
        }
    }
    public void listForStudent(int studentId) throws SQLException {
        String q="SELECT c.course_id,c.course_name,e.enrollment_date,e.enrollment_status FROM enrollments e INNER JOIN courses c ON c.course_id=e.course_id WHERE e.student_id=? ORDER BY c.course_name";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,studentId);try(ResultSet r=p.executeQuery()){while(r.next())System.out.printf("%d | %s | %s | %s%n",r.getInt(1),r.getString(2),r.getDate(3),r.getString(4));}
        }
    }
    public void studentsByCourse(int courseId) throws SQLException {
        String q="SELECT s.student_id,s.student_name,s.department,s.marks FROM students s INNER JOIN enrollments e ON e.student_id=s.student_id WHERE e.course_id=? ORDER BY s.student_name";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,courseId);try(ResultSet r=p.executeQuery()){while(r.next())System.out.printf("%d | %s | %s | %s%n",r.getInt(1),r.getString(2),r.getString(3),r.getBigDecimal(4));}
        }
    }
    public void countsByCourse() throws SQLException {
        String q="SELECT c.course_name,COUNT(e.student_id) AS student_count FROM courses c LEFT JOIN enrollments e ON e.course_id=c.course_id GROUP BY c.course_id,c.course_name ORDER BY c.course_name";
        try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery(q)){
            while(r.next())System.out.printf("%s: %d%n",r.getString(1),r.getInt(2));
        }
    }
}
