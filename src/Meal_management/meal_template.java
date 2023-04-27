/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Meal_management;

import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author FORHAD
 */
public interface meal_template {
    public void save(Meal obj);
    public void update(Meal obj);
    public void delete(Meal obj);
    public Meal get(int find,String FindDate);
    public List<Meal> list(); 
    public Pair<List<Object[]>,Double> calculation(Calculate obj);
}
