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
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author User
 */
public class IFarm {
    
    static final int THREAD = 3;
    
    public static void main(String[] args) {
        DBConnector db = new DBConnector();
                
        //SEQUENTIALLY
        /*
        Timer times = new Timer();
        
            times.start();
            for (Farmer farmers : farmerObj) {
                farmers.run();
            }
            
            times.end();
        
        System.out.println();
        */
        
        //CONCURRENTLY
        //Thread pool for generating activities
        ExecutorService p1 = Executors.newFixedThreadPool(THREAD);
        List<Future<List<String[]>>> list = new ArrayList<Future<List<String[]>>>();
        
        FarmerSimulator simulator = new FarmerSimulator(db);
        Farmer[] farmerObj = simulator.generateFarmers(10);
        
        for (Farmer farmers : farmerObj) {
            Callable<List<String[]>> worker = farmers;
            Future<List<String[]>> submit = p1.submit(worker);
            list.add(submit);
         }
        p1.shutdown();
        
        //Thread pool for handling data entry
        ExecutorService p2 = Executors.newFixedThreadPool(THREAD);
        
        ReentrantLock lock = new ReentrantLock();
        DataEntryHandler handler = new DataEntryHandler(lock);
        
        
        for (Future<List<String[]>> future : list) {
            System.out.println("each farmer");
            while (!future.isDone()){
                try {
                    if(future.isDone()){
                        //Feed to data entry handler
                        DataEntry entry = new DataEntry(future.get(), handler, lock);
                        p2.execute(entry);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

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
//        if (pool.isTerminated()){
//            System.out.println("Sequentially " + times.elapsed() + " miliseconds");
//            System.out.println("Concurrently " + timer.elapsed() + " miliseconds");
//        }
    }
}
