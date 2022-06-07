/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 * @author User
 */
public class DataEntryHandler implements Runnable {
    
    private List<String[]> activities;
    private Activity activity_class;

    public DataEntryHandler(List<String[]> activities) {
        this.activities = activities;
    }
    
    @Override
    public void run() {
        
        for (String[] s : activities){
            
            String joined = String.join("", s);
            
            
//            activity_class.toTxt(joined);
            System.out.println(Thread.currentThread().getName() + ": " + joined );
//            activity_class.toDB(s[8], data[2], quantity[1], Integer.parseInt(quantity[0]), randRow, randField, Integer.parseInt(data[1]), Integer.parseInt(data[0]));
            
        }

        

        System.out.println(Thread.currentThread().getName()+"Next entry");
    }
    
}
