/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Meal_management;

import com.example.oop.studentdb.StudentDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import javafx.util.Pair;
/**
 *
 * @author FORHAD
 */
public class meal_template_imp implements meal_template {
    public Pair<List<Object[]>,Double> calculation(Calculate obj){
        List<Object[]>list=new ArrayList<>();
        double rate=0;
        try{
            Connection con=StudentDb.getConnection();
            String sql_count_meal="select sum(morning+lunch+Dinner) from mealinfo where date>=? and date<=?";
            String sql_total_taka="select sum(entry_taka) from students";
            String sql_per_head_rate="SELECT DISTINCT id, name, SUM(morning + lunch + dinner) FROM mealinfo WHERE date >= ? AND date <= ? GROUP BY id, name;";
            PreparedStatement ps = con.prepareStatement(sql_count_meal);
            ps.setString(1,obj.getFrom());
            ps.setString(2,obj.getTo());
            ResultSet rs=ps.executeQuery();
            rs.next();
            int totalmeal=rs.getInt(1);
            ps=con.prepareStatement(sql_total_taka);
            rs=ps.executeQuery();
            rs.next();
            int totaltaka=rs.getInt(1);
            ps=con.prepareStatement(sql_per_head_rate);
            ps.setString(1,obj.getFrom());
            ps.setString(2,obj.getTo());
            rs=ps.executeQuery();
            //System.out.println(totalmeal+" "+totaltaka);
            try{
                rate=totaltaka/totalmeal;
                
                while(rs.next()){
                    int id=rs.getInt(1);
                    String name=rs.getString(2);
                    int totalmeal_for_this_id=rs.getInt(3);
                    double total_taka_for_this_id = totalmeal_for_this_id*rate;
                    String sql_total_taka_of_this_id="select entry_taka from students where id=?";
                    ps=con.prepareStatement(sql_total_taka_of_this_id);
                    ps.setInt(1,id);
                    ResultSet rs1=ps.executeQuery();
                    rs1.next();
                    int taka_for_this_id=rs1.getInt(1);
                    double tmp=taka_for_this_id-total_taka_for_this_id;
                    double taka_plus=0,taka_minus=0;
                    if(tmp<0)
                        taka_minus=tmp;
                    else taka_plus=tmp;
                    Object[] rowData={id,name,totalmeal_for_this_id,total_taka_for_this_id,taka_plus,taka_minus};
                    //System.out.println(id+" "+name+" "+totalmeal_for_this_id);
                    list.add(rowData);
                    
                }
                
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return new Pair<>(list,rate);
    }
    /*public List<Calculate> list1(){
        
    }*/
    public void save(Meal obj){
        try{
            Connection con=StudentDb.getConnection();
            String sql="INSERT INTO Mealinfo(Date,Name,Id,Morning,Lunch,Dinner) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,obj.getDate());
            ps.setString(2,obj.getName());
            ps.setInt(3,obj.getID());
            ps.setInt(4,obj.getMorning());
            ps.setInt(5,obj.getLaunch());
            ps.setInt(6,obj.getDinner());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Saved");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public void update(Meal obj){
        try{
            Connection con =StudentDb.getConnection();
            String sql="update mealinfo set Date=?,Name=?,Morning=?,Lunch=?,Dinner=? where id=?";
            //System.out.println("asdf");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,obj.getDate());
            ps.setString(2,obj.getName());
            
            ps.setInt(3,obj.getMorning());
            ps.setInt(4,obj.getLaunch());
            ps.setInt(5,obj.getDinner());
            ps.setInt(6,obj.getID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Updated");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public void delete(Meal obj){
        try{
            Connection con =StudentDb.getConnection();
            String sql="delete from mealinfo WHERE ID=?";
            String chksql="select count(*) from mealinfo";
            //Statement st=con.createStatement();
      
            PreparedStatement ps=con.prepareStatement(sql);
            
            ps.setInt(1, obj.getID());
            int chk=ps.executeUpdate();
            ps=con.prepareStatement(chksql);
            ResultSet rs=ps.executeQuery(chksql);
            rs.next();
            int count=rs.getInt(1);
            if(count==0){
                //System.out.println("DHUKSE");
                ps.executeUpdate("Delete from mailinfo");
                ps.executeUpdate("ALTER TABLE students AUTO_INCREMENT = 1");
            }
            if(chk!=0)
            JOptionPane.showMessageDialog(null,"Deleted");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error");
        }
    }
    Date date;
    @Override

    public Meal get(int find,String FindDate){
        Meal st=new Meal();
        //System.out.println(FindDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                
                date = dateFormat.parse(FindDate);
                //System.out.println(date);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Date formate Not Correct");
                return st;
            }
        try{
            Connection con =StudentDb.getConnection();
            String sql="SELECT * from mealinfo WHERE ID=? and Date=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, find);
                //System.out.println(123);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(2, sqlDate);
            ResultSet rs=ps.executeQuery();
            
            int f=0;
            while(rs.next()){
                f=1;
                //System.out.println("OK");
                Date dateget=rs.getDate(1);
                 
                SimpleDateFormat formatter = new SimpleDateFormat("YYYY/MM/dd");
                String StrDate=formatter.format(dateget);
                st.setDate(StrDate);
                st.setName(rs.getString(2));
                st.setID(rs.getInt(3));
                st.setMorning(rs.getInt(4));
                st.setLaunch(rs.getInt(5));
                st.setDinner(rs.getInt(6));
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
    public List<Meal> list(){
        List<Meal> list= new ArrayList<>();
        try{
            Connection con=StudentDb.getConnection();
            String sql="SELECT * from mealinfo";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                Meal st=new Meal();
                st.setDate(rs.getString("Date"));
                st.setName(rs.getString("Name"));
                st.setID(rs.getInt("Id"));
                st.setMorning(rs.getInt("Morning"));
                st.setLaunch(rs.getInt("Lunch"));
                st.setDinner(rs.getInt("Dinner"));
                list.add(st);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error");
        }
        return list;
    }



}
