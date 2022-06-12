import test.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.io.IOException;
import java.util.InputMismatchException;



public class DataVisualization {
    // Initializing the mysql connection class
      DBConnector db = new DBConnector();
    Statement stmt = db.conn();

    public DataVisualization() {
    }

    public void Activities() {
        try {
            // To check if activities table exists
            DatabaseMetaData meta = db.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "activity", new String[] { "TABLE" });
            if (!resultSet.next()) {
                System.out.println("Activity table does not exist!");
            } else {
               

                
                String changeSQL = "UPDATE `activity` SET `quantity`='[value-6]'/1000 WHERE `unit` = 'g' AND `unit` = 'ml'"+
                                     "UPDATE `activity` SET `quantity`='[value-6]'/2 WHERE `unit` = 'pack (500g)'"+
                                     " UPDATE `activity` SET  `unit`= replace(unit,'g','kg')"+
                                     " UPDATE `activity` SET  `unit`= replace(unit,'ml','g')"+
                                     " UPDATE `activity` SET  `unit`= replace(unit,'pack (500g)','pack (1000g)')";

                System.out.println("Change data in activity table...");
                stmt.executeUpdate(changeSQL);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet displayActivityLogsFarm(String farmId) {
        ResultSet rs = null;
        try {
            // Get activities based on farm id
            String sqlQuery = "SELECT * FROM activity" +
                    " WHERE farmId = " + farmId +
                    " ORDER BY date ASC";
            rs = stmt.executeQuery(sqlQuery);
//            System.out.print("1");
            printActivityLog(rs);
//             System.out.println(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }
        public ResultSet displayActivityLogsFarmer(String farmerId) {
        ResultSet rs = null;
        try {
            // Get activities based on farm id
            String sqlQuery = "SELECT * FROM activity" +
                    " WHERE farmerId = " + farmerId +
                    " ORDER BY date ASC";
            rs = stmt.executeQuery(sqlQuery);
//            System.out.print("1");
            printActivityLog(rs);
//             System.out.println(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }



    public ResultSet displayActivityLogsFarmType(String farmId, String type) {
        ResultSet rs = null;
        try {
            // Get activities based on farm id & type of plant / fertilizer / pesticide
            String sqlQuery = "SELECT * FROM activity" +
                    " WHERE farmId = " + farmId +
                    " AND LOWER(type) = LOWER('" + type + "')" +
                    " ORDER BY date ASC";
            rs = stmt.executeQuery(sqlQuery);
//            System.out.print("3");
             printActivityLog(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public ResultSet displayActivityLogsFarmTypeDate(String farmId, String type, String fromDate, String toDate) {
        ResultSet rs = null;
        try {
            // Get activities based on farm id & type of plant / fertilizer / pesticide
            String sqlQuery = String.format(
                    "SELECT * FROM activity WHERE farmId = %s AND LOWER(type) = LOWER('%s') AND date >= '%s' AND date <= '%s' ORDER BY date ASC",
                    farmId, type, fromDate, toDate);
            rs = stmt.executeQuery(sqlQuery);
//            System.out.print("4");
             printActivityLog(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public ResultSet displayActivityLogsFarmTypeDateFieldRow(String farmId, String type, String fromDate, String toDate, String fieldNumber, String rowNumber) {
        ResultSet rs = null;
        try {
            System.out.println("Field Number "+Integer.parseInt(fieldNumber));
            System.out.println("Row Number "+Integer.parseInt(rowNumber));
            // Get activities based on farm id, type of plant / fertilizer / pesticide, start date, end date, field number & row number
            String sqlQuery = String.format(
                    "SELECT * FROM activity WHERE farmId = %s AND LOWER(type) = LOWER('%s') AND date >= '%s' AND date <= '%s' AND field = %d AND row = %d ORDER BY date ASC",
                    farmId, type, fromDate, toDate, Integer.parseInt(fieldNumber), Integer.parseInt(rowNumber));
            rs = stmt.executeQuery(sqlQuery);
//            System.out.print("5");
             printSummarizedActivityLog(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public String printActivityLog(ResultSet rs) {
        String output = ""; 
        try {
            if (!rs.isBeforeFirst()) {
                output += "No records found";
            } else {
                while (rs.next()) {
                    String action = rs.getString("action");
                    String type = rs.getString("type");
                    int field = rs.getInt("field");
                    int row = rs.getInt("row");
                    int quantity = rs.getInt("quantity");
                    String unit = rs.getString("unit");
                    Date date = rs.getDate("date");

                    System.out.println( action + " " + type + " Field " + field + " Row " + row + " " + quantity + " "
                    + unit + " " + date.toString());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return output;
    }

    public String printSummarizedActivityLog(ResultSet rs) {
        String output = "";
        try {
            if (!rs.isBeforeFirst()) {
                output += "No records found";
            } else {
                int quantitySum = 0;
                String action = rs.getString("action");
                String type = rs.getString("type");
                int field = rs.getInt("field");
                int row = rs.getInt("row");
                String unit = rs.getString("unit");

                while (rs.next()) {
                    quantitySum += rs.getInt("quantity");
                }

                System.out.println(action + " " + type + " Field " + field + " Row " + row + " " + quantitySum + " " + unit + " ");
}
          
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return output;
    }

    public static void main(String[] args) throws SQLException {

  DataVisualization visualize = new DataVisualization();
        
        visualize.Activities();

        
         int input = 0;
         Scanner s = new Scanner(System.in);

        // // while loop to run the commands until exit
         while(true){
             System.out.println("\nTo visualize the activites select a particular number for to view the data (enter -1 to exit)\n");
             System.out.println("Enter 1 for displaying all activity logs for a target farm");
             System.out.println("Enter 2 for displaying all activity logs for a target farmer");
             System.out.println("Enter 3 for displaying all activity logs for a target farm and plant / fertilizer / pesticide");
             System.out.println("Enter 4 for displaying all activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive)");
             System.out.println("Enter 5 for displayign summarized logs by plants, fertilizers and pesticides for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) for selected field and row number\n");

             System.out.print("Input: ");

            
             try{
                 input = s.nextInt();
             } catch(InputMismatchException e){
                 System.out.println("\nInput error");
                 break;
             }
            
        //     // program end
            if(input == -1){
                 System.out.println("Program ending");
                 break;
             }
            
             String farmId,farmerId, typeName, fromDate, toDate, fieldNumber, rowNumber;

             // switch case to perform sql commands
             switch (input) {
                 case 1:
                     System.out.print("\nEnter Farm ID: ");
                     do{
                         farmId = s.next();
                     } while(farmId.isEmpty());
                     visualize.displayActivityLogsFarm(farmId);    
                     break;
                     
                 case 2:
                     System.out.print("\nEnter Farmer ID: ");
                     do{
                         farmerId = s.next();
                     } while(farmerId.isEmpty());
                     visualize.displayActivityLogsFarm(farmerId);    
                     break;  
                     
                 case 3:
                     System.out.print("\nEnter Farm ID: ");
                     do{
                         farmId = s.next();
                     } while(farmId.isEmpty()); 
                     System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                     do{
                         typeName = s.next();
                     } while(typeName.isEmpty());
                     visualize.displayActivityLogsFarmType(farmId, typeName);
                     break;
                 case 4:
                     System.out.print("\nEnter Farm ID: ");
                     do{
                         farmId = s.next();
                         s.nextLine();
                     } while(farmId.isEmpty()); 
                     System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                     do{
                         typeName = s.next();
                         s.nextLine();
                    } while(typeName.isEmpty());
                     System.out.print("\nEnter start date: ");
                     do{
                         fromDate = s.next();
                         s.nextLine();
                     } while(fromDate.isEmpty());
                     System.out.print("\nEnter end date: ");
                     do{
                         toDate = s.next();
                         s.nextLine();
                     } while(toDate.isEmpty());            

                     visualize.displayActivityLogsFarmTypeDate(farmId, typeName, fromDate, toDate);
                     break;
                 case 5:
                     System.out.print("\nEnter Farm ID: ");
                     do{
                         farmId = s.next();
                         s.nextLine();
                     } while(farmId.isEmpty()); 
                     System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                     do{
                         typeName = s.next();
                         s.nextLine();
                     } while(typeName.isEmpty());
                     System.out.print("\nEnter start date: ");
                     do{
                         fromDate = s.next();
                         s.nextLine();
                     } while(fromDate.isEmpty());
                     System.out.print("\nEnter end date: ");
                     do{
                         toDate = s.next();
                         s.nextLine();
                     } while(toDate.isEmpty());
                     System.out.print("\nEnter field number: ");
                     do{
                         fieldNumber = s.next();
                         s.nextLine();
                     } while(toDate.isEmpty());
                     System.out.print("\nEnter row number: ");
                     do{
                         rowNumber = s.next();
                         s.nextLine();
                     } while(toDate.isEmpty());

                     visualize.displayActivityLogsFarmTypeDateFieldRow(farmId, typeName, fromDate, toDate, fieldNumber, rowNumber);
                     break;
                 default:
                     System.out.println("Invalid input\n");
                     break;
             }
         }
         s.close();
            
     
     
    }
    
    


}
