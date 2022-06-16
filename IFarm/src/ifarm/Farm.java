/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Farm {
    
    private int row;
    private int field;
    private int flagGet, flagSet;
    private String id;
    private String[][] area;
    private Lock lock;
    private Condition condition;

    public Farm() {
        row = 2;
        field = 2;
        area = new String[row][field];
        lock = new ReentrantLock();
    }
    
    public synchronized String getArea(int row, int field)  {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Farm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return area[row][field];
    }

    public void setArea(int row, int field, String status) throws InterruptedException {
        this.area[row][field] = status;
    }
    
    //Return number of row to the farmer class
    public int getRow() {
        return row;
    }

    //Return number of field to the farmer class
    public int getField() {
        return field;
    }

    @Override
    public String toString() {
        return id;
    }
    
}
