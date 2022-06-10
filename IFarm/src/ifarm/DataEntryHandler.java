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
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class DataEntryHandler  {
    
    private List<String[]> activities;
    private Activity activity_class;
    ReentrantLock lock;

    public DataEntryHandler(ReentrantLock lock) {
        this.lock = lock;
    }
    
    
//        int y =  atomicInteger.get();
        
        

//        System.out.println(Thread.currentThread().getName()+"Next entry");


//    
    public synchronized void insertToDatabase(List<String[]> activities) throws FileNotFoundException, IOException{
        
//        lock.lock();
        System.out.println(Thread.currentThread().getName()+" is waiting to get the lock.");
        
        for (String[] s : activities){
            
            String joined =  "User-" + s[0] + " Farm-" +  s[1] + " " +s[4] + " " + s[5] + " " +  s[3] + " " + s[6] + " " + s[7] + " " + s[8] + " " + s[9] + " " + s[10] ;
            System.out.println(Thread.currentThread().getName() + ": " + joined );
//            activity_class.toTxt(joined);    
//            activity_class.toDB( id ,s[5], s[2], s[7], Integer.parseInt(s[6]), Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[1]), Integer.parseInt(s[0]));
            
//         "User-" , data[0] , " Farm-" , data[1] , " " , dtf.format(now) , " " , action , " " , data[3] , " " , quantity[0] , quantity[1] , " " , Integer.toString(randRow) , " " , Integer.toString(randField)  
//                   String[] activity_data = {data[0] , data[1] , data[2] , data[3] , dtf.format(now) , action  , quantity[0] , quantity[1] , Integer.toString(randRow) ,  Integer.toString(randField) };
        }
        
//        System.out.println(Thread.currentThread().getName()+" has got the lock.");
        
        System.out.println(" has queued Threads = "+lock.hasQueuedThreads());
        System.out.println(Thread.currentThread().getName()+" has released the lock.");
//        lock.unlock();
        
    }

}
