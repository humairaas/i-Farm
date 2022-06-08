/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

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
        /*
        //sequentially
        Timer times = new Timer();
        
        
            times.start();
            for (int i=0; i<5; i++) {
                Farmer farmer = new Farmer(db);
                farmer.run();
            }
            
            times.end();
        
        System.out.println();
        */
        
        //concurrently
        //Thread pool for generating activities
        ExecutorService p1 = Executors.newFixedThreadPool(THREAD);
        List<Future<Boolean>> list = new ArrayList<Future<Boolean>>();
        for (int i = 0; i < 5; i++) {
            Callable<Boolean> worker = new Farmer(db);
            Future<Boolean> submit = p1.submit(worker);
            list.add(submit);
        }
        p1.shutdown();
        
        //Thread pool for handling data entry
        ExecutorService p2 = Executors.newFixedThreadPool(THREAD);
        
        ReentrantLock lock = new ReentrantLock();
        DataEntryHandler handler = new DataEntryHandler(lock);
        
        for (Future<Boolean> future : list) {
            try {
                if(future.get()){
                    //Feed to data entry handler
                    DataEntry entry = new DataEntry(handler, lock);
                    p2.execute(entry);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        p2.shutdown();
        /*
        ExecutorService pool = Executors.newFixedThreadPool(THREAD);
        Timer timer = new Timer();
        
        try {
            timer.start();
            for (int i=0; i<5; i++) {
                Farmer farmer = new Farmer(db);
                pool.execute(farmer);   
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
*/

        
    }
    
    
}
