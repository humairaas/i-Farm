/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

/**
 *
 * @author ndhsn
 */
public class Timer {
    
    long startTime;
    long endTime;
    long timeElapsed;
    long start_sleep;
    long end_sleep;
    long sleep_time;
    
    public void start(){
        startTime = System.currentTimeMillis();
//        System.out.println(startTime);
    }
    
    public void end(){
        endTime = System.currentTimeMillis();
//        System.out.println(endTime);
    }
    
    public long elapsed(){
        timeElapsed = endTime - startTime;
        return  timeElapsed;
    }
    
    public void start_sleep(){
        start_sleep = System.currentTimeMillis();
    }
    
    public void end_sleep(){
        end_sleep = System.currentTimeMillis();
    }
    
    public long sleep_time(){
        sleep_time = end_sleep - start_sleep;
        return  sleep_time;
    }
    
}

 
