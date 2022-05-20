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
public class User {
    private DBConnector db;

    public User(DBConnector db) {
        this.db = db;
    }

    public String[] getUserFarmID() {
        Random r = new Random();
        String size = db.SELECT("SELECT COUNT(*) FROM `users_farms`");
        int rand = r.nextInt(Integer.parseInt(size.replace("#", "")));
        String arr[] = db.SELECT("SELECT user_id_fk, farm_id_fk FROM `users_farms` LIMIT 1 OFFSET " + rand).split("#");
        
        return arr;
    }

}
