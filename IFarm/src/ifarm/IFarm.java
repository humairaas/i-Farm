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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */
public class IFarm {
    
    static final int THREAD = 10;
    
    public static void main(String[] args) {
        
        // establish connection with databse
        DBConnector db = new DBConnector();
        
        // check if Activities table is empty
        db.isEmpty("Activities");
        
        // call class that implements farmer simulator interface to create many farmer at once
        FarmerSimulator simulator = new FarmerSimulator(db);
        
        // number of farmer
        int totalFarmer = 10;
        
        // generate farmers based on number of farmer for sequential and concurrent process
//        Farmer[] farmerObjSeq = simulator.generateFarmers(totalFarmer);
        Farmer[] farmerObjCon = simulator.generateFarmers(totalFarmer);
        
        // initializing atomic integer for activity id
        AtomicInteger atomicInteger = new AtomicInteger();
        
        // initializing lock explicitly
        ReentrantLock lock = new ReentrantLock();
        
        // initializing class for handling generated data
        DataEntryHandler handler = new DataEntryHandler(atomicInteger, lock);
                
        //SEQUENTIALLY
        
        // initializing timer for sequential process
//        Timer timerSeq = new Timer();
//        
//        // start recording time for sequential process
//        timerSeq.start(); 
//            
//            // for every farmer created
//            // execute call method in Farmer class
//            // results will be used for initializing data entry class
//            // execute run method in data entry class to start inserting data into database and tezt file(log)
//            // exception is thrown since runnable is used 
//        
//            for (Farmer farmers : farmerObjSeq) {
//                try {
//                    Runnable entry1 = new DataEntry(farmers.call(), handler); 
//                    entry1.run();
//                } catch (Exception ex) {
//                    Logger.getLogger(IFarm.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//        // stop recording time for sequential process
//        timerSeq.end();
                    
        
        //CONCURRENTLY
        
        //Thread pool for generating activities
        ExecutorService p1 = Executors.newFixedThreadPool(THREAD);
        
         //Thread pool for handling data entry
        ExecutorService p2 = Executors.newFixedThreadPool(THREAD);
        
        // list of activities that
        List<Future<List<String[]>>> list = new ArrayList<Future<List<String[]>>>();
        
        // initializing timer for concurrent process
        Timer timerCon = new Timer();

        // start recording time for concurrent process
        timerCon.start();

        // for each farmer
        for (Farmer farmers : farmerObjCon) {
            Callable<List<String[]>> worker = farmers;
            Future<List<String[]>> submit = p1.submit(worker);
            list.add(submit);
         }
        p1.shutdown();

        // to constantly check when future is done
//        while(!list.isEmpty()){
//            for(int i=0 ; i<list.size(); i++){
//                
//                Future<List<String[]>> future = list.get(i);
//                if(future.isDone()){
//                    try{
//                        // Feed to Data Entry Handler
//                        Runnable entry = new DataEntry(future.get(), handler);
//                        p2.execute(entry);
//                        list.remove(i);
//                    }catch(InterruptedException e){
//                        e.printStackTrace();
//                    }catch(ExecutionException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//       
//        p2.shutdown();
//        
//        try {
//            p2.awaitTermination(1, TimeUnit.DAYS);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(IFarm.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        timerCon.end();
//       
//        // Start data visualization
//        DataVisualization visualize = new DataVisualization(db);
//        visualize.start();   
        
    }
}
