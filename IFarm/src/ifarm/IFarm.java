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
                
        FarmerSimulator simulator = new FarmerSimulator(db);
        Farmer[] farmerObj = simulator.generateFarmers(10);
        
        //sequentially
        Timer times = new Timer();
        
            times.start();
            for (Farmer farmers : farmerObj) {
                farmers.run();
            }
            
            times.end();
        
        System.out.println();
            
        //concurrently
        ExecutorService pool = Executors.newFixedThreadPool(THREAD);
        Timer timer = new Timer();
        
        try {
            timer.start();
            for (Farmer farmers : farmerObj) {
                pool.execute(farmers);
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);
            timer.end();
        } catch (InterruptedException e){
            
        }
        
        // compare time
        if (pool.isTerminated()){
            System.out.println("Sequentially " + times.elapsed() + " miliseconds");
            System.out.println("Concurrently " + timer.elapsed() + " miliseconds");
        }

        
    }
    
    
}
