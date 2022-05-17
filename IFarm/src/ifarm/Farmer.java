/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author User
 */
public class Farmer implements Runnable {
    
    DBConnector db = new DBConnector();
    Random r = new Random();
    String[] activities = {"sowing\t", "fertilizers", "pesticides", "harvest\t", "sales\t"};
    
    int count, userID, farmID;
    int random = 1 + r.nextInt(getRowNum());

    @Override
    public void run() {
        int step = 0;
        for (int i=0; i<10; i++) {
            System.out.println(activities[step] + "\t" + getFarmID() + "\t" + getUserID());
            step++;
            if (step==5) {
                step = 0;
            }
        }
    }
    
    public int getRowNum() {
        try {
            db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
            String sql = "SELECT COUNT(*) AS row FROM `users_farms`";
            db.stmt = db.conn.prepareStatement(sql);
            ResultSet rs = db.stmt.executeQuery(sql);
            rs.next();
            count = rs.getInt("row");
        } catch (SQLException ex) {
            Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
        
    public int getUserID() {
        try {
            db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
            String sql = "SELECT user_id_fk AS userID FROM `users_farms` WHERE id=" + random;            
            db.stmt = db.conn.prepareStatement(sql);
            ResultSet rs = db.stmt.executeQuery(sql);
            rs.next();
            userID = rs.getInt("userID");
        } catch (SQLException ex) {
            Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userID;
    }
    
    public int getFarmID() {
        try {
            db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
            String sql = "SELECT farm_id_fk AS farmID FROM `users_farms` WHERE id=" + random;            
            db.stmt = db.conn.prepareStatement(sql);
            ResultSet rs = db.stmt.executeQuery(sql);
            rs.next();
            farmID = rs.getInt("farmID");
        } catch (SQLException ex) {
            Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return farmID;
    }
    
//    public int getTypeID(int step) {
//        if (step == 1) {
//            try {
//                db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
//                String sql = "SELECT COUNT(*) AS ferCount FROM `farms_fertilizers` WHERE farm_id_fk=" + farmID;
//                db.stmt = db.conn.prepareStatement(sql);
//                ResultSet rs = db.stmt.executeQuery(sql);
//
//                String sql = "SELECT fertilizer_id_fk AS ferID FROM `farms_fertilizers` WHERE farm_id_fk=" + farmID;            
//                db.stmt = db.conn.prepareStatement(sql);
//                ResultSet rs = db.stmt.executeQuery(sql);
//            } catch (SQLException ex) {
//                Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
