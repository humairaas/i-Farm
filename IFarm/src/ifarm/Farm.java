/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;

/**
 *
 * @author User
 */
public class Farm {
    
    private String[][] area;
    private int row;
    private int field;
    private Random r;

    public Farm() {
        row = 2;
        field = 2;
        r = new Random();
        area = new String[row][field];
    }

    public synchronized String getArea(int row, int field) {
        return area[row][field];
    }
    
    public void setArea(int row, int field, String status) {
        this.area[row][field] = status;
    }

    public int getRow() {
        return row;
    }

    public int getField() {
        return field;
    }

  
    
    
}
