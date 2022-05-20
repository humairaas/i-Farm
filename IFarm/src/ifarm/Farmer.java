/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.*;

/**
 *
 * @author User
 */
public class Farmer implements Runnable {
    
    private DBConnector db;
    private Farm farm;
    private Activity activity;
    
    private Random r;
    private String action;
    private String[] quantity;
    private int randRow;
    private int randCol;
    private String[] userFarmID;
    private String[] data;
    
    public Farmer(DBConnector db) {
        this.db = db;
        farm = new Farm();
        r = new Random();
        userFarmID = getUserFarm();
        activity = new Activity(db);
    }
    
    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            randRow = r.nextInt(farm.getRow());
            randCol = r.nextInt(farm.getCol());
            
            if (farm.getField(randRow, randCol) == null) {
                action = "sowing";
                data = getData("plant",userFarmID[0],userFarmID[1]);
                quantity = getQuantity("g");
                farm.setField(randRow, randCol, "0|" + data[2]);

            } else {
                String[] temp = farm.getField(randRow, randCol).split("|");
                String status = temp[0];
                String plantID = temp[1];
                if (status.equals("0")) {
                    action = "fertilizer";
                    data = getData("fertilizer",userFarmID[0],userFarmID[1]);
                    quantity = getQuantity("g");
                    farm.setField(randRow, randCol, "1|"+ data[2]);
                    
                } else if (status.equals("1")) {
                    action = "pesticide";
                    data = getData("pesticide",userFarmID[0],userFarmID[1]);
                    quantity = getQuantity("ml");
                    farm.setField(randRow, randCol, "2|"+ data[2]);
                    
                } else if (status.equals("2")) {
                    action = "harvest";
                    data = getData("plant",userFarmID[0],userFarmID[1]);
                    quantity = getQuantity("g");
                    farm.setField(randRow, randCol, "3|"+ data[2]);
                    
                } else if (status.equals("3")) {
                    action = "sales";
                    data  = getData("plant",userFarmID[0],userFarmID[1]);
                    quantity = getQuantity("g");
                    farm.setField(randRow, randCol, null);
                }
            } 
            String finalLog = "User-"+data[0]+" Farm-"+data[1]+" "+action+" "+data[3]+" "+quantity[0]+quantity[1]+" "+randRow+" "+randCol;
            activity.toDB(action, data[2], quantity[1], Integer.parseInt(quantity[0]), randRow, randCol, Integer.parseInt(data[1]), Integer.parseInt(data[0]));
            System.out.println(Thread.currentThread().getName() + ": " + finalLog);
        }
    }
    
    public String[] getUserFarm() {
        Random r = new Random();
        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        int rand = r.nextInt(Integer.parseInt(size.replace("#", "")));
        String arr[] = db.SELECT("SELECT user_id_fk, farm_id_fk FROM `users_farms` LIMIT 1 OFFSET " + rand).split("#");
        return arr;
    }
    
    public String[] getData(String type, String userID, String farmID) {
        Random r = new Random();
        String size = db.SELECT("SELECT COUNT(*) FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID);
        int rand = r.nextInt(Integer.parseInt(size.replace("#", "")));
        String arr[] = db.SELECT("SELECT users_farms.user_id_fk, users_farms.farm_id_fk, farms_"+type+"s."+type+"_id_fk, "+type+"s.name FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID+" LIMIT 1 OFFSET " + rand).split("#");
        return arr;
    }
    
    //For harvest and sales, get plant name based on id
//    public String[] getActivityData(String activityID, String userID, String farmID) {
//        Random r = new Random();
//        String size = db.SELECT("SELECT COUNT(*) FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID);
//        int rand = r.nextInt(Integer.parseInt(size.replace("#", "")));
//        String arr[] = db.SELECT("SELECT users_farms.user_id_fk, users_farms.farm_id_fk, farms_"+type+"s."+type+"_id_fk, "+type+"s.name FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID+" LIMIT 1 OFFSET " + rand).split("#");
//        return arr;
//    }
    
    public String[] getQuantity(String unit) {
        Random r = new Random();
        String[] arr = new String[2];
        
        int rand = r.nextInt(5000)+500;
        arr[0] = Integer.toString(rand);
        arr[1] = unit;
        return arr;
    }
}