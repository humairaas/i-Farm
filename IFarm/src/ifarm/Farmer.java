/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author User
 */

public class Farmer implements Callable {    
    
    private DBConnector db;
    private Farm farm;
    private Activity activity;
    private Random r;
    private String action;
    private String[] data, quantity, UserFarmID;
    private String[][][] plant ;
    private DateTimeFormatter dtf;  
    private LocalDateTime now; 
    private int randRow, randField, counter;
    private List<String[]> activity_logs = new ArrayList<String[]>();
 
    Farmer(DBConnector db, String[] UserFarmID, Farm farm) {
        this.db = db;
        this.UserFarmID = UserFarmID;
        this.farm = farm;
        this.r = new Random();
        this.activity = new Activity();
        this.now = LocalDateTime.now(); 
        this.dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        this.plant = new String[this.farm.getRow()][this.farm.getField()][];
    }

    @Override
    public List<String[]> call() throws Exception {  
        for (int i=0; i<10; i++) {
            randRow = r.nextInt(farm.getRow());
            randField = r.nextInt(farm.getField());
            
            if (farm.getArea(randRow, randField) == null) {
                action = "sowing";
                data = getPlantData();
                quantity = getQuantity("g");
                farm.setArea(randRow, randField, "0");
                
            } else {
                String status = farm.getArea(randRow, randField);
                
                if (status.equals("0")) {
                    action = "fertilizer";
                    data = getData("fertilizer",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, "1");
                    
                } else if (status.equals("1")) {
                    action = "pesticide";
                    data = getData("pesticide",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("ml");
                    farm.setArea(randRow, randField, "2");
                    
                } else if (status.equals("2")) {
                    action = "harvest";
                    data = getPlantData();
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, "3");
                    
                } else if (status.equals("3")) {
                    action = "sales";
                    data  = getPlantData();
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, null);
                }
            }   
            
            // Randomly generated days to be incremented for each activity
            int days = 1 + r.nextInt(7);
            counter += days;
            LocalDateTime date = now.plusDays(counter);
            
            // try to resume if fail
            try {
                String[] activity_data = {data[0] , data[1] , data[2] , data[3] , dtf.format(date) , action  , quantity[0] , quantity[1] , Integer.toString(randRow) ,  Integer.toString(randField) };
                activity_logs.add(activity_data);
                String finalLog = "User-"+data[0]+" Farm-"+data[1]+" "+dtf.format(date)+" "+action+" "+data[3]+" "+quantity[0]+quantity[1]+" "+randRow+" "+randField;
                System.out.println("FARMER CLASS: "+Thread.currentThread().getName() + ": " + finalLog);
            } catch (NumberFormatException e) {
               
            }
            
        }
        return activity_logs;
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
    
    public String[] getQuantity(String unit) {
        Random r = new Random();
        String[] arr = new String[2];
        
        int rand = (5 + r.nextInt(50)) * 100;
        arr[0] = Integer.toString(rand);
        arr[1] = unit;
        return arr;
    }
    
    public String[] getPlantData () throws InterruptedException {
        if (farm.getArea(randRow, randField) == null){
            plant[randRow][randField] = getData("plant", UserFarmID[0], UserFarmID[1]);
        } 
        return plant[randRow][randField];
    }

}
