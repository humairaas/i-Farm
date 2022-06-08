/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.time.*;
import java.time.format.*;
import java.util.concurrent.Callable;

/**
 *
 * @author User
 */
public class Farmer implements Callable<Boolean> {
    
    private DBConnector db;
    private Farm farm;
    private Activity activity;
    
    private Random r;
    private String action;
    private String[] quantity;
    private int randRow;
    private int randField;
    private String[] data;
    private String[][][] plant ;
    private DateTimeFormatter dtf;  
    private LocalDateTime now; 
    private String[] UserFarmID;
    
    public Farmer(DBConnector db, String[] UserFarmID) {
        this.db = db;
        farm = new Farm();
        r = new Random();
        activity = new Activity();
        activity = new Activity();
        now = LocalDateTime.now(); 
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        plant = new String[farm.getRow()][farm.getField()][];
        this.UserFarmID = UserFarmID;
    }

//    public Farmer(String userID) {
//        this.userID = userID;
//    }
    
    @Override
    public Boolean call() {
        
        for (int i=0; i<10; i++) {
            randRow = r.nextInt(farm.getRow());
            randField = r.nextInt(farm.getField());
            
            if (farm.getArea(randRow, randField) == null) {
                action = "sowing";
                data = getPlantData();
                quantity = getQuantity("g");
                farm.setArea(randRow, randField, "0|" + data[2]);

            } else {
                String[] temp = farm.getArea(randRow, randField).split("|");
                String status = temp[0];
                String plantID = temp[1];
                if (status.equals("0")) {
                    action = "fertilizer";
                    data = getData("fertilizer",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, "1|"+ data[2]);
                    
                } else if (status.equals("1")) {
                    action = "pesticide";
                    data = getData("pesticide",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("ml");
                    farm.setArea(randRow, randField, "2|"+ data[2]);
                    
                } else if (status.equals("2")) {
                    action = "harvest";
                    data = getPlantData();
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, "3|"+ data[2]);
                    
                } else if (status.equals("3")) {
                    action = "sales";
                    data  = getPlantData();
                    quantity = getQuantity("g");
                    farm.setArea(randRow, randField, null);
                }
            }   
            
            // try to resume if fail
            try {
                String finalLog = "User-"+data[0]+" Farm-"+data[1]+" "+dtf.format(now)+" "+action+" "+data[3]+" "+quantity[0]+quantity[1]+" "+randRow+" "+randField;
                activity.toTxt(finalLog, data[0]);
                //activity.toDB(action, data[2], quantity[1], Integer.parseInt(quantity[0]), randRow, randField, Integer.parseInt(data[1]), Integer.parseInt(data[0]));
                System.out.println(Thread.currentThread().getName() + ": " + finalLog);
            } catch (NumberFormatException e) {
               
            }
        }
        //If false, handle disaster
        return true;
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
        
        int rand = (5 + r.nextInt(50)) * 100;
        arr[0] = Integer.toString(rand);
        arr[1] = unit;
        return arr;
    }
    
    public String[] getPlantData () {
        
        if (farm.getArea(randRow, randField) == null){
            plant[randRow][randField] = getData("plant", UserFarmID[0], UserFarmID[1]);
        } 
        
        return plant[randRow][randField];
    }
}
