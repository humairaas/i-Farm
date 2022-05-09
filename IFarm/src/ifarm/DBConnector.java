/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class DBConnector {
    private final String DB_URL = "jdbc:mysql://localhost:3306/i_farm?zeroDateTimeBehavior=convertToNull";
    private final String USERNAME = "i_farm_user";
    private final String PASSWORD = "ifarm123";

    public void start() {
        java.sql.Connection conn = null;
        java.sql.Statement stmt = null;
        
        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");
            
            stmt = conn.createStatement();
//            try {
//                File myObj = new File("C:\\Users\\User\\Documents\\NetBeansProjects\\i-Farm\\IFarm\\src\\ifarm\\txtFiles\\Fertilizers.txt");
//                Scanner myReader = new Scanner(myObj);
//                while (myReader.hasNextLine()) {
//                  String data = myReader.nextLine();
//                  String sql = "INSERT INTO `fertilizers` (`fertilizer_id`, `name`, `unit_type`, `timestamp_create`, `timestamp_update`) VALUES (NULL, '"+data+"', 'pack', current_timestamp(), NULL);";
//                  stmt.executeUpdate(sql);
//                  System.out.println("Inserted "+data+" records into the table");
//                }
//                myReader.close();
//              } catch (FileNotFoundException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();
//              }
//        Random rand = new Random();
//        int rand_int1 = rand.nextInt(100)+1;
            

            String sql = "";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table");
            
        }catch(SQLException e){
            System.out.println("Not connected to database.");
            System.err.println(e);
        }
    }
    
  
}
    
   
    
        

