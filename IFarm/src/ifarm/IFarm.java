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
public class IFarm {
        
    public static void main(String[] args) {
        Runnable farmer = new Farmer();
        
        Thread t1 = new Thread(farmer);
        t1.start();
        
    }
    
    
}
