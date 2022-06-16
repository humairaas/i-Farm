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
    private Random r;
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private String currentThread;
    public DisasterSimulator2() {
        databaseConn = true;
        internetConn = true;
        r = new Random();
        currentThread = null;
    }
    
    public boolean databaseConnError(){
        return false;
        
    }

    public Boolean getInternetConn() {
        return internetConn;
    }

    public void setInternetConn(Boolean internetConn) {
        this.internetConn = internetConn;
    }
    
    
    public void internetConnError() throws InterruptedException{
        internetConn = false;
        currentThread = Thread.currentThread().getName();
        System.out.println(currentThread+" : Your connection was interrupted.");
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
