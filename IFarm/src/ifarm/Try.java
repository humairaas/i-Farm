package ifarm;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Try {
    DBConnector db = new DBConnector();
    String[][] fieldID;
    
    public void start() {
        
        int row = 2;
        int col = 2;
        Random r = new Random();
        String data = "";
        String action = "";
        String quantity = "";
        fieldID = new String[row][col];
        int randRow = r.nextInt(row);
        int randCol = r.nextInt(col);
        
        String[] arr = getUserFarm();
        
        System.out.println(fieldID[randRow][randCol]);
        if (fieldID[randRow][randCol] == null) {
            action = "sowing";
            data = getData("plant",arr[0],arr[1]);
            quantity = getQuantity("g");
            fieldID[randRow][randCol] = "0|?";
            
        } else {
            String[] temp = fieldID[randRow][randCol].split("|");
            String status = temp[0];
            String plantID = temp[1];
            if (status.equals("0")) {
                action = "fertilizer";
                data = getData("fertilizer",arr[0],arr[1]);
                quantity = getQuantity("g");
                fieldID[randRow][randCol] = "1|?";
            } else if (status.equals("1")) {
                action = "pesticide";
                data = getData("pesticide",arr[0],arr[1]);
                quantity = getQuantity("ml");
                fieldID[randRow][randCol] = "2|?";
            } else if (status.equals("2")) {
                action = "harvest";
                data = getData("plant",arr[0],arr[1]);
                quantity = getQuantity("g");
                fieldID[randRow][randCol] = "3|?";
            } else if (status.equals("3")) {
                action = "sales";
                data  = getData("plant",arr[0],arr[1]);
                quantity = getQuantity("g");
                fieldID[randRow][randCol] = null;
            }
        } 
        String log = action+" "+data+""+quantity;
        String[] tLog = log.split(" ");
        String finalLog = "User-"+tLog[1]+" Farm-"+tLog[2]+" "+tLog[0]+" "+tLog[4]+" "+tLog[5];
        System.out.println(finalLog);
        
    }
    
    public String[] getUserFarm() {
        Random r = new Random();
        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        int rand = r.nextInt(Integer.parseInt(size.replaceAll("\\s+", "")));
        String arr[] = db.SELECT("SELECT user_id_fk, farm_id_fk FROM `users_farms` LIMIT 1 OFFSET " + rand).split(" ");
        return arr;
    }
    
    public String getData(String type, String userID, String farmID) {
        Random r = new Random();
        String size = db.SELECT("SELECT COUNT(*) FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID);
        int rand = r.nextInt(Integer.parseInt(size.replaceAll("\\s+", "")));
        return db.SELECT("SELECT users_farms.user_id_fk, users_farms.farm_id_fk, farms_"+type+"s."+type+"_id_fk, "+type+"s.name FROM users_farms LEFT JOIN farms_"+type+"s ON users_farms.farm_id_fk=farms_"+type+"s.farm_id_fk LEFT JOIN "+type+"s ON farms_"+type+"s."+type+"_id_fk="+type+"s."+type+"_id WHERE users_farms.user_id_fk = "+userID+" AND users_farms.farm_id_fk = "+farmID+" LIMIT 1 OFFSET " + rand);
    }
    
    public String getQuantity(String unit) {
        Random r = new Random();
        int rand = r.nextInt(5000)+500;
        return Integer.toString(rand)+unit;
    }
    
}
