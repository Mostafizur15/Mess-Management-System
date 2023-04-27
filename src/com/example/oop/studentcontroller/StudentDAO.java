/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.oop.studentcontroller;
import com.example.oop.model.Student;
import java.util.*;
/**
 *
 * @author FORHAD
 */
public interface StudentDAO {
    public void save(Student students);
    public void update(Student students);
    public void delete(Student students);
    public Student get(int id);
    public List<Student> list();
}
