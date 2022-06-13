/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.ArrayList;
import java.util.Iterator;
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
    
    static final int THREAD = 10;
    
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
        
        FarmerSimulator simulator = new FarmerSimulator(db);
        Farmer[] farmerObj = simulator.generateFarmers(50);
        List<Future<List<String[]>>> list = new ArrayList<Future<List<String[]>>>();
        
        for (Farmer farmers : farmerObj) {
            Callable<List<String[]>> worker = farmers;
            Future<List<String[]>> submit = p1.submit(worker);
            list.add(submit);
         }
        p1.shutdown();
        
        Timer timer = new Timer();
        //Thread pool for handling data entry
        ExecutorService p2 = Executors.newFixedThreadPool(THREAD);
        
        ReentrantLock lock = new ReentrantLock();
        DataEntryHandler handler = new DataEntryHandler(lock);
        
        //int count =0;
        timer.start();
        while(!list.isEmpty()){
            for(int i=0 ; i<list.size(); i++){
                //System.out.println(count++);
                Future<List<String[]>> future = list.get(i);
                if(future.isDone()){
                    try{
                        //Feed to Data Entry Handler
                        Runnable entry = new DataEntry(future.get(), handler, lock);
                        p2.execute(entry);
                        list.remove(i);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }catch(ExecutionException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        p2.shutdown();
        timer.end();
        System.out.println("Data Entry " + timer.elapsed() + " miliseconds");
        
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
