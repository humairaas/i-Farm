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
import java.util.concurrent.TimeUnit;
public class Timer {
    
    long startTime;
    long endTime;
    long timeElapsed;
    
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
    
}

 
