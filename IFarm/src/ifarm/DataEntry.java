/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.io.IOException;
import java.util.List;
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
    private List<String[]> activities;

    public DataEntry(List<String[]> activities, DataEntryHandler handler, ReentrantLock lock) {
        this.handler = handler;
        this.lock = lock;
        this.activities = activities;
    }
    
    @Override
    public void run() {
        handler.insertToDatabase(activities);
    }
}
