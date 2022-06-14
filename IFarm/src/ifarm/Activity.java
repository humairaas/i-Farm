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
    
    public Activity() {
        db = new DBConnector();
    }
  
    public void toDB(int activity_id, String action, String type, String unit, int quantity, int field, int row, int farmID, int userID){
       db.INSERT("INSERT INTO `activities` (`activity_id` ,`action`, `type`, `unit`, `quantity`, `field`, `row`, `farm_id_fk`, `user_id_fk`) VALUES ('"+activity_id+"','"+action+"','"+type+"','"+unit+"','"+quantity+"','"+field+"','"+row+"','"+farmID+"','"+userID+"')");
    }
    
    public void toTxt(String text, String user){
        try {
            //Change this according to your own directory path
            FileWriter myWriter = new FileWriter("C:\\Users\\User\\Documents\\NetBeansProjects\\i-Farm\\IFarm\\src\\ifarm\\txtFiles\\Farmer-"+user+".txt", true);
            myWriter.write(text + "\n");
            myWriter.close();
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
