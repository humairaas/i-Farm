/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class DisasterHandler implements Runnable{
    private final DisasterSimulator2 dis;
    private Random r;


    public DisasterHandler(DisasterSimulator2 dis) {
        this.dis = dis;
        r = new Random();
    }
    
    @Override
    public void run() {
        int time = r.nextInt(30000-10000)+10000;
        //System.out.println("time : "+  time);
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(DisasterHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        dis.fixConnection();
    }
    
}
