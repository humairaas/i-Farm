/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.sql.Date;  
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class DataEntryHandler  {
    ReentrantLock lock;
    AtomicInteger atomicInteger;
    Activity activityClass = new Activity();
    String DB_disaster;
    String insert_DB;
    Random r = new Random();
    Timer timer = new Timer();
    DisasterSimulator Disaster = new DisasterSimulator(atomicInteger);

    public DataEntryHandler(AtomicInteger atomicInteger, ReentrantLock lock) {
        this.lock = lock;
        this.atomicInteger = atomicInteger;
    }
    
    public void insertToDatabase(List<String[]> activities){
        lock.lock();
        try {
            for (String[] s : activities){
                String joined =  "User-" + s[0] + " Farm-" +  s[1] + " " +s[4] + " " + s[5] + " " +  s[3] + " " + s[6] + "" + s[7] + " " + s[8] + " " + s[9] + " " + atomicInteger.getAndIncrement() ;
                System.out.println("DATA ENTRY: "+Thread.currentThread().getName() + ": " + joined);
                Date date = Date.valueOf(s[4]);
                activityClass.toDB(atomicInteger.get(), date, s[5], s[3], s[7], Integer.parseInt(s[6]), Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
                activityClass.toTxt(joined, s[0]);
            }
//            for (String[] s : activities){
//                Date date = Date.valueOf(s[4]);
//                insert_DB = activityClass.SQLCommand(atomicInteger.get(), date, s[5], s[3], s[7], Integer.parseInt(s[6]), Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
//                int has_disaster = Disaster.Disaster(s, insert_DB, atomicInteger);  
//
//                // check boolean or int where 0 == no disaster, 1 == still in disaster, 99 = after disaster, 11 first disaster
//                if(has_disaster == 0){
//                    String joined =  "User-" + s[0] + " Farm-" +  s[1] + " " +s[4] + " " + s[5] + " " +  s[3] + " " + s[6] + "" + s[7] + " " + s[8] + " " + s[9] + " " + atomicInteger.getAndIncrement() ;
//                    System.out.println("DATA ENTRY: "+Thread.currentThread().getName() + ": " + joined);
//                    //activityClass.toTxt(joined, s[0]);
//                    activityClass.toDB(atomicInteger.get(), date, s[5], s[3], s[7], Integer.parseInt(s[6]), Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
//                }if(has_disaster == 1){
//
//                }if(has_disaster == 11){
//                    System.out.println("first disaster");
//                }
//                if(has_disaster == 99){
//                    System.out.println("Done insert data after Disaster.");
//                }
//            }
        } finally {
            lock.unlock();
        }
    }   
}
