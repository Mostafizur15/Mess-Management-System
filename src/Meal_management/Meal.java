/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Meal_management;

/**
 *
 * @author FORHAD
 */
public class Meal{
    private String Date,Name;
    private int ID,Morning,Launch,Dinner;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMorning() {
        return Morning;
    }

    public void setMorning(int Mourning) {
        this.Morning = Mourning;
    }

    public int getLaunch() {
        return Launch;
    }

    public void setLaunch(int Launch) {
        this.Launch = Launch;
    }

    public int getDinner() {
        return Dinner;
    }

    public void setDinner(int Dinner) {
        this.Dinner = Dinner;
    }
    
}
