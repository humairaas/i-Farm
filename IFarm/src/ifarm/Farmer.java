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
    int count;
    int rand = 1 + r.nextInt(getRowNum());

    @Override
    public void run() {
        int step = 0;
        for (int i=0; i<10; i++) {
            System.out.print(activities[step] + "\t");
            getFarmUserID();
            getType(step);
            System.out.println("");
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
            this.count = rs.getInt("row");
        } catch (SQLException ex) {
            Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.count;
    }
    
    public void getFarmUserID() {
        try {
            db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
            String sql = "SELECT * FROM `users_farms` WHERE id=" + rand;            
            db.stmt = db.conn.prepareStatement(sql);
            
            ResultSet rs = db.stmt.executeQuery(sql);
            rs.next();
            System.out.print(rs.getString("farm_id_fk") + "\t" + rs.getString("user_id_fk"));
        } catch (SQLException ex) {
            Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getType(int step) {
//        if (step == 1) {
//            try {
//                db.conn = DriverManager.getConnection(db.DB_URL, db.USERNAME, db.PASSWORD);
//                String sql = "SELECT * FROM `farms_fertilizers`";            
//                db.stmt = db.conn.prepareStatement(sql);
//
//                ResultSet rs = db.stmt.executeQuery(sql);
//                rs.next();
//                System.out.print(rs.getString("farm_id_fk") + "\t" + rs.getString("user_id_fk"));
//            } catch (SQLException ex) {
//                Logger.getLogger(Farmer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
}
