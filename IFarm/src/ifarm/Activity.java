/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.*;

/**
 *
 * @author User
 */
public class Activity {
    private DBConnector db;
    private String action;
    private String type;
    private String unit;
    private int quantity;
    private int field;
    private int row;
    private int farmID;
    private int userID;

    public Activity(DBConnector db) {
        this.db = db;
    }
    
  
    public void toDB(String action, String type, String unit, int quantity, int field, int row, int farmID, int userID){
       db.INSERT("INSERT INTO `activities` (`action`, `type`, `unit`, `quantity`, `field`, `row`, `farm_id_fk`, `user_id_fk`) VALUES ('"+action+"','"+type+"','"+unit+"','"+quantity+"','"+field+"','"+row+"','"+farmID+"','"+userID+"');");
    }
    
    public void toTxt(String text){
        try {
            //Change this according to your own directory path
            FileWriter myWriter = new FileWriter("C:\\Users\\USER\\Desktop\\NetBeans\\WIF3003\\i-Farm\\IFarm\\src\\ifarm\\txtFiles\\Activities.txt", true);
            myWriter.write(text + "\n");
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Activity{" + "action=" + action + ", type=" + type + ", unit=" + unit + ", quantity=" + quantity + ", field=" + field + ", row=" + row + ", farmID=" + farmID + ", userID=" + userID + '}';
    }
    
    



    
    

    
    
    
}