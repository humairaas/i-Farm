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
public class Farmer implements Runnable {
        
    Random r = new Random();

    String[] action = {"sowing", "fertilizer", "pesticide", "harvest", "sales"};
    
    String[] plant = {"plant a", "plant b", "plant c", "plant d", "plant e"};
    String[] fertilizer = {"fertilizer a", "fertilizer b", "fertilizer c", "fertilizer d", "fertilizer e"};
    String[] pesticide = {"pesticide a", "pesticide b", "pesticide c", "pesticide d", "pesticide e"};
    
    int day = 0;
    
    @Override
    public void run() {
        for (int i=0; i<1000; i++) {
            int actrand = r.nextInt(action.length);
            
            System.out.print(Thread.currentThread().getName() + " | ");
            getDate();
            System.out.print(action[actrand] + " | ");
            getType(actrand);
        }
    }
    
    public void getDate() {
        int dayrand = r.nextInt(10) + 1;
        day += dayrand;
        System.out.print(java.time.LocalDate.now().plusDays(day) + " | ");    
    }
    
    public void getType(int actrand) {
        int ferrand = r.nextInt(fertilizer.length);
        int pesrand = r.nextInt(pesticide.length);
        int plarand = r.nextInt(plant.length);
        
        switch (actrand) {
            case 1:
                System.out.println(fertilizer[ferrand]);
                break;
            case 2:
                System.out.println(pesticide[pesrand]);
                break;
            default:
                System.out.println(plant[plarand]);
                break;
        }
    }
}
