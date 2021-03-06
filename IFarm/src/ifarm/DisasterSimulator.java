/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Hafizul
 * 
 * Simulate disasters where farmer operations fail in the middle. 
 * Implement a workaround to recover your failed threads. 
 * Justify how you attempt to resolve the disasters to 
 * allow farmers to continue operations from the point where they failed.
 * Ensure the atomicity of data.
 * 
 * Generate a disaster and recover the failed thread 
 * 
 * 
 * Handle scenario if database disconnected.
 * Handle scenario where 1 thread pauses without
 * affecting other thread, then the thread continues.
 */
public class DisasterSimulator{
    private DBConnector db;
    AtomicInteger atomicInteger;
    Activity activityClass = new Activity();
    String joined;
    String insert_DB;
    private String temp;
    String temp_Farm;
    int count_disaster;
    ArrayList<String> DisasterData = new ArrayList<String>();
    ArrayList<String> DisasterCommand = new ArrayList<String>();
    Random r = new Random();
    Timer timer = new Timer();
    int Disaster_time;
    
    public DisasterSimulator(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
        db = new DBConnector();
    }
    
    
    public int Disaster(String[] s, String insert_DB, AtomicInteger atomicInteger){
        int disaster_chance = r.nextInt(100); //100 - 5% chance for disaster
            if(disaster_chance < 5 && count_disaster == 0){ //timer to avoid 2 disaster happen at the same time...can do but hmmm
                count_disaster = 0;
                joined =  "User-" + s[0] + " Farm-" +  s[1] + " " +s[4] + " " + s[5] + " " +  s[3] + " " + s[6] + "" + s[7] + " " + s[8] + " " + s[9] + " " + atomicInteger.getAndIncrement();
                temp = Thread.currentThread().getName();
                temp_Farm = s[1]; //error when change farm -not needed
                System.out.println("DISASTER HAPPEN: "+ Thread.currentThread().getName()+ " " + joined);
                DisasterData.add(joined);
                
                //this one error when want save first disaster
                //DisasterCommand.add(insert_DB);
                ++count_disaster;
                activityClass.toTxt_Disaster(joined, s[0]);
                timer.start_sleep();
                return 11;
            }else{
                if(timer.sleep_time() < 0){ 
                    timer.end_sleep();
                                                //max  +1  - min  + min
                    Disaster_time = r.nextInt(5000 + 1 - 500) + 500;
                    System.out.println("Disaster time is: " + Disaster_time);
                } if(Thread.currentThread().getName().equals(temp) && timer.sleep_time() < Disaster_time){ 
                    ++count_disaster;
                        try{
                            Thread.sleep(20);
                        }catch(InterruptedException e){
                        }
                    timer.end_sleep();
                    joined =  "User-" + s[0] + " Farm-" +  s[1] + " " +s[4] + " " + s[5] + " " +  s[3] + " " + s[6] + "" + s[7] + " " + s[8] + " " + s[9] + " " + atomicInteger.getAndIncrement() ;
                    DisasterData.add(joined);
                    DisasterCommand.add(insert_DB);
                    System.out.println("Disaster still happen in this thread. " +Thread.currentThread().getName() +" " + joined);
                    activityClass.toTxt_Disaster(joined, s[0]); 
                }else{
                    if(timer.sleep_time() > Disaster_time && count_disaster != 0){ //error after disaster...
                        //after the disaster
                        count_disaster = 0;
                        System.out.println("Disaster end");
                        System.out.println("Sleep: "+timer.sleep_time());
                        for(String data: DisasterData){
                            System.out.println("RECOVER DATA AFTER DISASTER: "+ temp +" "+ data);
                        }
                        for(String into_DB: DisasterCommand){
                            try{
                                db.INSERT(into_DB);
                            } catch(Exception e){
                                 System.out.println("Error insert data");
                            }
                        }
                        DisasterData.clear();
                        DisasterCommand.clear();
                        return 99;
                    }else{ //for chance thread when disaster still running
                        if(!Thread.currentThread().getName().equals(temp) && count_disaster > 0){
                            if(timer.sleep_time() < Disaster_time){
                                //check disaster done or not - still have disaster but different thread
                                try{
                                    Thread.sleep(20);
                                }catch(InterruptedException e){
                                } 
                                timer.end_sleep();
                                 System.out.println("" +timer.sleep_time() );
                            }else if(timer.sleep_time() > Disaster_time){
                                //disaster done
                                System.out.println("Change thread-2");
                                for(String data: DisasterData){
                                    System.out.println("RECOVER DATA AFTER DISASTER: " + data);
                                }
                                for(String into_DB: DisasterCommand){
                                    try{
                                        db.INSERT(into_DB);
                                    } catch(Exception e){
                                         System.out.println("Error insert data");
                                    }
                                }
                                DisasterData.clear();
                                DisasterCommand.clear();
                                count_disaster = 0;
                                return 99;
                            }  
                        }
                        try{
                                Thread.sleep(20);
                            }catch(InterruptedException e){
                            } 
                        return 0;
                    }
                }
            }
        return 1;
    }
    
}  

