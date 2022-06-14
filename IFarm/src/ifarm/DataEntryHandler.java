/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class DataEntryHandler  {
    ReentrantLock lock;
    AtomicInteger atomicInteger;
    Activity activityClass = new Activity();

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
                activityClass.toTxt(joined, s[0]);
                activityClass.toDB(atomicInteger.get(), s[5], s[3], s[7], Integer.parseInt(s[6]), Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
            }
        } finally {
            lock.unlock();
        }
    }   
}
