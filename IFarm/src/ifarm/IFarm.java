/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        
//        sequential(farmerObj);
        concurrent(farmerObj);
        
    }
    
    public static void concurrent (Farmer[] farmerObj) {

        ExecutorService pool = Executors.newFixedThreadPool(THREAD);
        Timer timer = new Timer();
        int farmerCounter = 0;
        int handlerCounter = 0;
        List<Future<List<String[]>>> activities = new ArrayList<Future<List<String[]>>>() ;
        List<Callable<List<String>>> farmer = new ArrayList<Callable<List<String>>>(100);
        
        System.out.println("Concurrent");
                  
        try {
            timer.start();
            
            while (farmerCounter<10 || handlerCounter<10) {
                
                if (farmerCounter<10){
                    farmer.add(farmerObj[farmerCounter]); 
                    activities.add(pool.submit(farmerObj[farmerCounter]));
                    farmerCounter++;
//                    System.out.println(farmerCounter);
                }
                
                if (activities.get(handlerCounter).isDone()) {
                    Runnable handle = new DataEntryHandler(activities.get(handlerCounter).get());
                    pool.execute(handle); 
                    handlerCounter++;
//                    System.out.println(handlerCounter);
                }
               
            }
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.DAYS);
            timer.end();
        } catch (InterruptedException ex) {
            Logger.getLogger(IFarm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(IFarm.class.getName()).log(Level.SEVERE, null, ex);
        }
        p2.shutdown();
        
//        ExecutorService pool = Executors.newFixedThreadPool(THREAD);
//        Timer timer = new Timer();
//        
//        try {
//            timer.start();
//            for (Farmer farmers : farmerObj) {
//                pool.execute(farmers);
//            }
//            pool.shutdown();
//            pool.awaitTermination(1, TimeUnit.DAYS);
//            timer.end();
//        } catch (InterruptedException e){
//            
//        }
        
        // compare time
        if (pool.isTerminated()){
            System.out.println("Concurrently " + timer.elapsed() + " miliseconds");
        }
    }
    
        
    
    public static void sequential (Farmer[] farmerObj) {
        Timer times = new Timer();
        int i = 0;
        
        System.out.println("Sequential");
        
        
            times.start();
            for (Farmer farmer : farmerObj) {
                
                try {
                    DataEntryHandler handle;
//                    System.out.println("farmer" + i);
                    handle = new DataEntryHandler(farmer.call());
                    handle.run();
//                    System.out.println("handler" + i);
                } catch (Exception ex) {
                    Logger.getLogger(IFarm.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            times.end();
        
            System.out.println("Sequentially " + times.elapsed() + " miliseconds");
            
        System.out.println();
    }
}
