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
    private DisasterSimulator2 dis;
    
    private DateTimeFormatter dtf;  
    private LocalDateTime now; 
    
    private Random r;
    private String action;
    private String[] quantity;
    private int randRow;
    private int randField;
    private int counter;
    private String[] data;
    private String[] UserFarmID;
    private String[][][] plant ;
    
    private List<String[]> activity_logs = new ArrayList<String[]>();
 
    public Farmer(DBConnector db, String[] UserFarmID, Farm farm) {
        this.db = db;
//        this.farm = farm;
        this.UserFarmID = UserFarmID;
        
        now = LocalDateTime.now(); 
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
        
        this.farm = new Farm();
        r = new Random();
        activity = new Activity();
        plant = new String[farm.getRow()][farm.getField()][];
        dis = new DisasterSimulator2();
    }

    @Override
    public List<String[]> call() throws Exception {  
        for (int i=0; i<100; i++) {
            randRow = r.nextInt(farm.getRow());
            randField = r.nextInt(farm.getField());
            
            if (farm.getArea(randRow, randField) == null) {
                action = "sowing";
                data = getPlantData();
                quantity = getQuantity("kg");
                farm.setArea(randRow, randField, "0|" + data[2]);

            } else {
                String[] temp = farm.getArea(randRow, randField).split("|");
                String status = temp[0];
                String plantID = temp[1];
                if (status.equals("0")) {
                    action = "fertilizer";
                    data = getData("fertilizer",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("kg");
                    farm.setArea(randRow, randField, "1|"+ data[2]);
                    
                } else if (status.equals("1")) {
                    action = "pesticide";
                    data = getData("pesticide",UserFarmID[0],UserFarmID[1]);
                    quantity = getQuantity("l");
                    farm.setArea(randRow, randField, "2|"+ data[2]);
                    
                } else if (status.equals("2")) {
                    action = "harvest";
                    data = getPlantData();
                    quantity = getQuantity("kg");
                    farm.setArea(randRow, randField, "3|"+ data[2]);
                    
                } else if (status.equals("3")) {
                    action = "sales";
                    data  = getPlantData();
                    quantity = getQuantity("kg");
                    farm.setArea(randRow, randField, null);
                }
            }   
            
            // Randomly generated days to be incremented for each activity
            int days = 1 + r.nextInt(30);
            counter += days;
            LocalDateTime date = now.plusDays(counter);
            
            // if disaster happen
            // call the disaster
            // thread will wait
            // thread wait for notify call
            // notify call, continue the loop
            
            int dchance = r.nextInt(100);
            //System.out.println("chance:" +dchance);
            if(dchance < 2){
                Runnable dhandler = new DisasterHandler(dis);
                Thread t1 = new Thread(dhandler);
                t1.start();
                dis.internetConnError();
            }
   
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
        
        float rand = (r.nextFloat(50)) / 10;
        arr[0] = String.format("%.2f", rand);
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
