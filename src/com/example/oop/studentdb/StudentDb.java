/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.oop.studentdb;
import java.sql.*;
/**
 *
 * @author FORHAD
 */
public class StudentDb {
    static Connection con;
    static String driver="com.mysql.jdbc.Driver";
    static String url="jdbc:mysql://localhost:3306/studentinfo";
    static String uname="root";
    static String pass="forhad";
    public static Connection getConnection() throws Exception{
        if(con==null){
            Class.forName(driver);
            con=DriverManager.getConnection(url,uname,pass);
        }
        return con;
    }
}
