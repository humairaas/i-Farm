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
    private Farmer[] farmer;
    private Random r;

    public FarmerSimulator(DBConnector db) {
        this.db = db;
        this.r = new Random();
    }
    
    @Override
    public Farmer[] generateFarmers(int numberOfFarmers) {
        this.farmer = new Farmer[numberOfFarmers];
        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        for (int i = 0; i < numberOfFarmers; i++) {
            int rand = r.nextInt(Integer.parseInt(size.replace("#", "")));
            String arr[] = db.SELECT("SELECT user_id_fk, farm_id_fk FROM `users_farms` LIMIT 1 OFFSET " + rand).split("#");
            farmer[i] = new Farmer(db, arr);
//            System.out.println("Farmer " + (i+1) + ": " + Arrays.deepToString(arr));
        }
        return farmer;
    }
    
}
