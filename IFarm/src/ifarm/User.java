/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

/**
 *
 * @author User
 */
public class User {
    private DBConnector db;

    public User(DBConnector db) {
        this.db = db;
    }

    public String getFarmId(int id) {
        String farmId = db.SELECT("SELECT `user_id_fk`,`farm_id_fk` FROM `users_farms` WHERE id="+id);
        
        return farmId;
    }

}
