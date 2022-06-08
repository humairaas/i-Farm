/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class DataEntry implements Runnable{
    private ReentrantLock lock;
    private DataEntryHandler handler;

    public DataEntry(DataEntryHandler handler, ReentrantLock lock) {
        this.handler = handler;
        this.lock = lock;
    }
    
    @Override
    public void run() {
        try {
            handler.insertToDatabase();
        } catch (IOException ex) {
            Logger.getLogger(DataEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
