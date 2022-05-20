/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author User
 */
public class Farm {
    
    private String[][] fields;
    private int row;
    private int col;
//    private int randStatus;
    private Random r;

    public Farm() {
        row = 2;
        col = 2;
        r = new Random();
        fields = new String[row][col];
        
//        for (int i=0; i<this.row; i++) {
//            for (int j=0; j<this.col; j++) {
//                randStatus = r.nextInt(4);
//                fields[i][j] = Integer.toString(randStatus) + "|3";
//                System.out.println("status created" + randStatus);
//            }
//        }
    }

    public String getField(int row, int col) {
        return fields[row][col];
    }
    
    public void setField(int row, int col, String status) {
        this.fields[row][col] = status;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

  
    
    
}
