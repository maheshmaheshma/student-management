package com.example.sms.dao;

import com.example.sms.db.Database;
import java.sql.*;
import com.example.sms.util.StudentValidation;

public class CourseDao {
    public void add(String name, String department, int credits) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO courses(course_name,department,credits) VALUES(?,?,?)")){
            p.setString(1,name.trim());p.setString(2,StudentValidation.normalizeDepartment(department));p.setInt(3,credits);p.executeUpdate();
        }
    }
    public void list() throws SQLException {
        try(Connection c=Database.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM courses ORDER BY course_id")){
            while(r.next())System.out.printf("%d | %s | %s | %d credits%n",r.getInt("course_id"),r.getString("course_name"),r.getString("department"),r.getInt("credits"));
        }
    }
    public boolean exists(Connection c,int id) throws SQLException {
        try(PreparedStatement p=c.prepareStatement("SELECT 1 FROM courses WHERE course_id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next();}}
    }
}
