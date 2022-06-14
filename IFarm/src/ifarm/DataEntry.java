/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class DataEntry implements Runnable{
    private DataEntryHandler handler;
    private List<String[]> activities;

    public DataEntry(List<String[]> activities, DataEntryHandler handler) {
        this.handler = handler;
        this.activities = activities;
    }
    
    @Override
    public void run() {
        handler.insertToDatabase(activities);
    }
}
