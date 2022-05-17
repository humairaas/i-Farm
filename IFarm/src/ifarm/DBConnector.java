/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

//import java.io.File;
//import java.io.FileNotFoundException;
import java.sql.*;
//import java.util.ArrayList;
//import java.util.Random;
//import java.util.Scanner;

public final class DBConnector {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/i_farm?zeroDateTimeBehavior=convertToNull";
    public static final String USERNAME = "i_farm_user";
    public static final String PASSWORD = "ifarm123";
    
    java.sql.Connection conn = null;
    java.sql.Statement stmt = null;

    public DBConnector() {
        connect();
    }

    public void connect() {
        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");
            
        }catch(SQLException e){
            System.out.println("Not connected to database.");
            System.err.println(e);
        }
    }
    
    public void INSERT(String query){
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            System.out.println("INSERT Successful.");
            stmt.close();
            
        }catch(SQLException e){
            System.out.println("Error inserting to database.");
            System.err.println(e);
        }
    }
    
    public String SELECT(String query){
        String data = "";
        
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("SELECT Successful.");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                for(int i = 1; i < columnsNumber; i++)
                    data += rs.getString(i) + " ";
                data += '\n';
            }
            stmt.close();
            
        }catch(SQLException e){
            System.out.println("Error selecting from database.");
            System.err.println(e);
        }
        return data;
    }
    
    /*
    public void insertDataToDatabase() {
        try{
            stmt = conn.createStatement();
            try {
                File myObj = new File("C:\\Users\\User\\Documents\\NetBeansProjects\\i-Farm\\IFarm\\src\\ifarm\\txtFiles\\Fertilizers.txt");
                try (Scanner myReader = new Scanner(myObj)) {
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String sql = "INSERT INTO `fertilizers` (`fertilizer_id`, `name`, `unit_type`, `timestamp_create`, `timestamp_update`) VALUES (NULL, '"+data+"', 'pack', current_timestamp(), NULL);";
                        stmt.executeUpdate(sql);
                        System.out.println("Inserted "+data+" records into the table");
                    }
                }
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
              }
            
        }catch(SQLException e){
            System.out.println("Not connected to database.");
            System.err.println(e);
        }
    }
    */
    /*
    public void randomizeUserData() {
        Random rand = new Random();
        int numbers[] = new int[100];
        
        for(int i=0; i<100; i++){
            numbers[i] = i+1;
        }
        
        try{
            stmt = conn.createStatement();
            
            for(int j=1; j<11; j++){
                int num = rand.nextInt(40)+10;
                
                for(int k=0; k<num; k++){
                    int index = rand.nextInt(100);
                    if(numbers[index]!=0){
                        int id = numbers[index];
                        String sql = "INSERT INTO `farms_fertilizers`(`id`, `farm_id_fk`, `fertilizer_id_fk`) VALUES (NULL,"+j+","+id+")";
                        stmt.executeUpdate(sql);
                        System.out.println("Inserted "+id+" into the table");
                        numbers[index] = 0;
                    }
                }
                for(int i=0; i<100; i++){
                    numbers[i] = i+1;
                }
            }
            
        }catch(SQLException e){
            System.err.println(e);
        }
    }
    */
    
  
}
    
   
    
        

