/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.util.concurrent.*;


/**
 *
 * @author User
 */
public class IFarm {
    
    static final int THREAD = 3;
    
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
        ExecutorService pool = Executors.newFixedThreadPool(THREAD);
        
        for (int i=0; i<3; i++) {
            Farmer farmer = new Farmer(db);
            pool.execute(farmer);   
        }
        pool.shutdown();

    }
    
    
}
