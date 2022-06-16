package ifarm;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <p>
 *
 * </p>
 */
public class DataVisualization {

    public static void main(String[] args) {
        DataVisualization dataVisualization = new DataVisualization(new DBConnector());
        dataVisualization.start();
    }

    private DBConnector dbConnector;
    private Scanner scanner;
    private Integer choice = 0;

    public DataVisualization(DBConnector db){
        this.dbConnector = db;
        this.scanner = new Scanner(System.in);
    }
     /*
    public void Activities() {
        try {
            // To check if activities table exists
            DatabaseMetaData meta = db.conn.getMetaData();
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
    */

    public void start(){
        while(true){
            System.out.println("\nTo visualize the activites select a particular number for to view the data (enter -1 to exit)\n");
            System.out.println("Enter 1 for displaying all activity logs for a target farm");
            System.out.println("Enter 2 for displaying all activity logs for a target farmer");
            System.out.println("Enter 3 for displaying all activity logs for a target farm and plant / fertilizer / pesticide");
            System.out.println("Enter 4 for displaying all activity logs for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive)");
            System.out.println("Enter 5 for displaying summarized logs by plants, fertilizers and pesticides for a target farm and plant / fertilizer / pesticide between date A and date B (inclusive) for selected field and row number\n");

            System.out.print("Input: ");

            try{
                choice = scanner.nextInt();
            } catch(InputMismatchException e){
                System.out.println("\nInput error");
                break;
            }

            if(1 ==  choice){
                while (true){
                    System.out.print("\nEnter Farm ID: ");
                    String farmId = scanner.next();
                    // Get activities based on farm id
                    ResultSet resultSet = dbConnector.SELECTrs("SELECT * FROM activities" +
                            " WHERE farm_id_fk = " + farmId +
                            " ORDER BY date ASC");
                    printActivityLog(resultSet);
                    System.out.println("Continue？ y/n");
                    String stillGo = scanner.next();
                    if("n".equals(stillGo)){
                        break;
                    }
                    else if ("y".equals(stillGo)){
                        continue;
                    }
                    else{
                        System.err.println("Invalid input\n");
                    }
                }


            }
            else if (2 == choice){
                while(true){
                    System.out.print("\nEnter Farmer ID: ");
                    String farmerId = scanner.next();
                    ResultSet resultSet = dbConnector.SELECTrs("SELECT * FROM activities" +
                            " WHERE user_id_fk = " + farmerId +
                            " ORDER BY date ASC");
                    printActivityLog(resultSet);
                    System.out.println("Continue？ y/n");
                    String stillGo = scanner.next();
                    if("n".equals(stillGo)){
                        break;
                    }
                    else if ("y".equals(stillGo)){
                        continue;
                    }
                    else{
                        System.err.println("Invalid input\n");
                    }
                }

            }
            else if (3 == choice){
                while (true){
                    System.out.print("\nEnter Farm ID: ");
                    String farmId = scanner.next();
                    System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                    String typeName = scanner.next();
                    ResultSet resultSet = dbConnector.SELECTrs("SELECT * FROM activities" +
                            " WHERE farm_id_fk = " + farmId +
                            " AND LOWER(type) = LOWER('" + typeName + "')" +
                            " ORDER BY date ASC");

                    printActivityLog(resultSet);
                    System.out.println("Continue？ y/n");
                    String stillGo = scanner.next();
                    if("n".equals(stillGo)){
                        break;
                    }
                    else if ("y".equals(stillGo)){
                        continue;
                    }
                    else{
                        System.err.println("Invalid input\n");
                    }
                }

            }
            else if (4 == choice){
                while (true){
                    System.out.print("\nEnter Farm ID: ");
                    String farmId = scanner.next();
                    System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                    String typeName = scanner.next();
                    System.out.print("\nEnter start date: ");
                    String fromDate = scanner.next();
                    System.out.print("\nEnter end date: ");
                    String toDate = scanner.next();
                    ResultSet resultSet = dbConnector.SELECTrs(String.format(
                            "SELECT * FROM activities WHERE farm_id_fk = %s AND LOWER(type) = LOWER('%s') AND date >= '%s' AND date <= '%s' ORDER BY date ASC",
                            farmId, typeName, fromDate, toDate));

                    printActivityLog(resultSet);
                    System.out.println("Continue？ y/n");
                    String stillGo = scanner.next();
                    if("n".equals(stillGo)){
                        break;
                    }
                    else if ("y".equals(stillGo)){
                        continue;
                    }
                    else{
                        System.err.println("Invalid input\n");
                    }
                }

            }
            else if(5 == choice){
                while (true){
                    System.out.print("\nEnter Farm ID: ");
                    String farmId = scanner.next();
                    System.out.print("\nEnter plant / fertilizer / pesticide name: ");
                    String typeName = scanner.next();
                    System.out.print("\nEnter start date: ");
                    String fromDate = scanner.next();
                    System.out.print("\nEnter end date: ");
                    String toDate = scanner.next();
                    System.out.print("\nEnter field number: ");
                    String fieldNumber = scanner.next();
                    System.out.print("\nEnter row number: ");
                    String rowNumber = scanner.next();
                    System.out.println("Field Number "+Integer.parseInt(fieldNumber));
                    System.out.println("Row Number "+Integer.parseInt(rowNumber));

                    ResultSet resultSet = dbConnector.SELECTrs(String.format(
                            "SELECT * FROM activities WHERE farm_id_fk = %s AND LOWER(type) = LOWER('%s') AND date >= '%s' AND date <= '%s' AND field = %d AND row = %d ORDER BY date",
                            farmId, typeName, fromDate, toDate, Integer.parseInt(fieldNumber), Integer.parseInt(rowNumber)));
                    printSummarizedActivityLog(resultSet);
                    System.out.println("Continue？ y/n");
                    String stillGo = scanner.next();
                    if("n".equals(stillGo)){
                        break;
                    }
                    else if ("y".equals(stillGo)){
                        continue;
                    }
                    else{
                        System.err.println("Invalid input\n");
                    }
                }

            }
            else{
                System.err.println("Invalid input\n");
                break;
            }

        }
        scanner.close();
    }


    public void printActivityLog(ResultSet rs) {
        try {
            if ((null == rs) || !rs.isBeforeFirst()) {
                System.err.println("No records found");
                return ;
            }
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

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void printSummarizedActivityLog(ResultSet rs) {
        try {
            if ((null == rs)||!rs.isBeforeFirst()) {
                System.err.println("No records found");
                return ;
            }
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
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
