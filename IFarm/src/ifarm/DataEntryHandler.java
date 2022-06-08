/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class DataEntryHandler {
    ReentrantLock lock;

    public DataEntryHandler(ReentrantLock lock) {
        this.lock = lock;
    }
    
    public synchronized void insertToDatabase() throws FileNotFoundException, IOException{
        System.out.println(Thread.currentThread().getName()+" is waiting to get the lock.");
        
        lock.lock();
        System.out.println(Thread.currentThread().getName()+" has got the lock.");
        
        //Insert to database here
        //Use atomic counter to increment activity id 
        /*
        String currentThread = "user-"+user+"-"+Thread.currentThread().getName();
         try {
            File myObj = new File("C:\\Users\\User\\Documents\\NetBeansProjects\\i-Farm\\IFarm\\src\\ifarm\\txtFiles\\user-1-pool-1-thread-1.txt");
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    //System.out.println(data);
                }
            }
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        */
        
        
        System.out.println(" has queued Threads = "+lock.hasQueuedThreads());
        System.out.println(Thread.currentThread().getName()+" has released the lock.");
        lock.unlock();
    }

}
