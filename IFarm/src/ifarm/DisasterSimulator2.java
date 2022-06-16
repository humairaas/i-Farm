/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class DisasterSimulator2{
    private Boolean databaseConn;
    private Boolean internetConn;
    private String currentThread;
    
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    
    public DisasterSimulator2() {
        databaseConn = true;
        internetConn = true;
        currentThread = null;
    }
    
    public boolean databaseConnError(){
        return false;
    }
    
    public void internetConnError() throws InterruptedException{
        internetConn = false;
        currentThread = Thread.currentThread().getName();
        System.out.println(currentThread+" : [AutoSave] Your connection was interrupted. ");
        lock.lock();
        try {
            System.out.println(currentThread+" : Waiting for connection...");
            condition.await();
        } finally {
            lock.unlock();
        }
    }
    
    public void fixConnection(){
        lock.lock();
        try {
            internetConn = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
        System.out.println(currentThread+" : Internet connected");
    }
}
