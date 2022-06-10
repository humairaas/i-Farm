/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
    private List<String[]> str;

    public DataEntry(List<String[]> str, DataEntryHandler handler, ReentrantLock lock) {
        this.handler = handler;
        this.lock = lock;
        this.str = str;
    }
    
    @Override
    public void run() {
        try {
            handler.insertToDatabase(str);
        } catch (IOException ex) {
            Logger.getLogger(DataEntry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
