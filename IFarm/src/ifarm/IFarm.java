/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;


/**
 *
 * @author User
 */
public class IFarm {
        
    public static void main(String[] args) {
//        Runnable farmer = new Farmer();
//        
//        Thread t1 = new Thread(farmer);
//        t1.start();

        //DBConnector db = new DBConnector();
        Try a = new Try();
        a.start();
        //FarmerSimulator fs = new FarmerSimulator(); 
        //fs.generateFarmers(10);
        
//        for(int i=0; i<10;i++){
//            String size = db.SELECT("SELECT COUNT(*) FROM users_farms INNER JOIN farms_plants ON users_farms.farm_id_fk=farms_plants.farm_id_fk JOIN plants ON farms_plants.plant_id_fk=plants.plant_id WHERE users_farms.user_id_fk = 1");
//            //System.out.println(size);
//            Random r = new Random();
//            int rand = r.nextInt(Integer.parseInt(size.replaceAll("\\s+", "")));
//            //System.out.println("rowNum: "+rand);
//
//            String data = db.SELECT("SELECT users_farms.user_id_fk, users_farms.farm_id_fk, plants.name, plants.unit_type FROM users_farms INNER JOIN farms_plants ON users_farms.farm_id_fk=farms_plants.farm_id_fk JOIN plants ON farms_plants.plant_id_fk=plants.plant_id WHERE users_farms.user_id_fk = 1 LIMIT 1 OFFSET "+rand);
//            System.out.println(data);
//            System.out.println("");
//        }
        
//        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        
//        
//        System.out.println("Size of users_farms: "+size);
//        
        
//        
//        User user = new User(db);
//        System.out.println(user.getFarmId(rand));
//        String users_farms = user.getFarmId(rand);
//        String[] split = users_farms.split(" ");
//        
//        String[][] field = new String[2][2];
//        int row = r.nextInt(2);
//        int col = r.nextInt(2);
//        
//        System.out.println("field["+row+"]["+col+"]");
//        
//        String[] action = {"Sowing", "Fertilizers", "Pesticides", "Harvest" ,"Sales"};
//        int index = 0;
//        String act = action[index];
//        System.out.println("Action: "+act);
//        
//        String plants="";
//        int quan=0;
//        if(field[row][col]==null && index == 0){
//            String plant_size = db.SELECT("SELECT COUNT(*) FROM `farms_plants` WHERE farm_id_fk="+split[1]);
//            int plant_id = r.nextInt(Integer.parseInt(plant_size.replaceAll("\\s+", ""))+1);
//            System.out.println("plant_id: "+plant_id);
//            
//            plants = db.SELECT("SELECT `name`,`unit_type` FROM `plants` WHERE plant_id="+plant_id);
//            String[] sp2 = plants.split(" ");
//            String unitType = sp2[1];
//            
//            if("mass".equals(unitType)){
//                quan = r.nextInt(1000)+500;
//                System.out.println(quan + " g");
//            }
//            else{
//                quan = r.nextInt(10)+1;
//                System.out.println(quan + " pack/s");
//            }
//        }
//        
//        String activity = users_farms+" "+act+" "+plants+" "+Integer.toString(quan)+" "+Integer.toString(row)+" "+Integer.toString(col);
//        System.out.println(activity);
        
        

        
    }
    
    
}
