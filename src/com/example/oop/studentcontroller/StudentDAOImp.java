/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.oop.studentcontroller;

import com.example.oop.model.Student;
import com.example.oop.studentdb.StudentDb;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author FORHAD
 */
public class StudentDAOImp implements StudentDAO{

    @Override
    public void save(Student students) {
        try{
            Connection con=StudentDb.getConnection();
            String sql="INSERT INTO students(fname,department,session,entry_taka) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,students.getFname());
            ps.setString(2,students.getDepartment());
            ps.setString(3,students.getSession());
            ps.setInt(4, students.getTaka());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Saved");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error111");
        }
    }

    @Override
    public void update(Student students) {
        try{
            Connection con =StudentDb.getConnection();
            String sql="update students set fname=?,department=?,session=?,entry_taka=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,students.getFname());
            ps.setString(2,students.getDepartment());
            ps.setString(3,students.getSession());
            ps.setInt(4,students.getTaka());
            ps.setInt(5,students.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Updated");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void delete(Student students) {
        try{
            Connection con =StudentDb.getConnection();
            String sql="delete from students WHERE ID=?";
            String chksql="select count(*) from students";
            //Statement st=con.createStatement();
      
            PreparedStatement ps=con.prepareStatement(sql);
            
            ps.setInt(1, students.getId());
            int b=ps.executeUpdate();
            System.out.println(b);
            ps=con.prepareStatement(chksql);
            ResultSet rs=ps.executeQuery(chksql);
            rs.next();
            int count=rs.getInt(1);
            if(count==0){
                ps.executeUpdate("Delete from students");
                ps.executeUpdate("ALTER TABLE students AUTO_INCREMENT = 1");
            }
            if(b!=0)
            JOptionPane.showMessageDialog(null,"Deleted");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error");
        }
    }

    @Override
    public Student get(int id) {
        //System.out.println("Run");
        Student st=new Student();
        try{
            Connection con =StudentDb.getConnection();
            String sql="SELECT * from students WHERE ID=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();

            int f=0;
            while(rs.next()){
                f=1;
                System.out.println("OK");
                st.setId(rs.getInt(1));
                st.setFname(rs.getString(2));
                st.setDepartment(rs.getString(3));
                st.setSession(rs.getString(4));
                st.setTaka(rs.getInt(5));
            }
            if(f==0){
                JOptionPane.showMessageDialog(null,"Not Found");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error");
        }
        return st;
    }

    @Override
    public List<Student> list() {
        List<Student> list= new ArrayList<>();
        try{
            Connection con=StudentDb.getConnection();
            String sql="SELECT * from students ";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Student st=new Student();
                st.setId(rs.getInt("ID"));
                st.setFname(rs.getString("fname"));
                st.setDepartment(rs.getString("department"));
                st.setSession(rs.getString("session"));
                st.setTaka(rs.getInt("entry_taka"));
                list.add(st);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error");
        }
        return list;
    }
    
}
