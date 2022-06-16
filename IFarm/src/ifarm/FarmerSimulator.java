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
public class FarmerSimulator implements FarmerSimulatorInterface {
    
    private DBConnector db;
    private Farm[] farm;
    private Farm[] generatedFarm;
    private Farmer[] farmer;
    private String[] array;
    private Random r;
    private int random;
    private int farmID;
    private int numberOfFarms;

    public FarmerSimulator(DBConnector db) {
        this.db = db;
        this.r = new Random();
    }
    
    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {
        // Create array size equal to the number of farmers
        farmer = new Farmer[numberOfFarmers];
        
        // Count the number of row in the users_farms table in the database
        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        
        // Store the returned Farm object array
        generatedFarm = generateFarms();
        
        // Loop according to the number of farmers need to be generated
        for (int i = 0; i < numberOfFarmers; i++) {
            
            // Generate a random integer to determine which row in the database to retrieve farmer data from
            random = r.nextInt(Integer.parseInt(size.replace("#", "")));
            array = db.SELECT("SELECT user_id_fk, farm_id_fk FROM `users_farms` LIMIT 1 OFFSET " + random).split("#");
            
            // Obtain the farm ID to parse it into the newly generated farmer object
            farmID = Integer.parseInt(array[1]) - 1;
            farmer[i] = new Farmer(db, array, generatedFarm[farmID]);
        }
        System.out.println();
        return farmer;
    }
    
    public Farm[] generateFarms() {
        numberOfFarms = 10;
        farm = new Farm[numberOfFarms];
        for (int i = 0; i < numberOfFarms; i++) {
            farm[i] = new Farm();
        }
        return farm;
    }
    
}
