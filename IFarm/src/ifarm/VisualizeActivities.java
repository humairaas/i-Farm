/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifarm;
import java.sql.*;
/**
 *
 * @author User
 */
public class VisualizeActivities {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/i_farm_user?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=true";
	
	static final String USER = "root";
    static final String PASS = "";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
	    Statement stmt = null;
	    PreparedStatement ps=null;
//	    try{
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL,USER,PASS);
//            stmt = conn.createStatement();
//            String sql;
//            sql = "SELECT * FROM activity";
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                String action  = rs.getString("action");
//                String unit = rs.getString("unit");
//                int quantity = rs.getInt("quantity");
//                
//                
//                
//                System.out.print("action: " + action);
//                System.out.print(", unit: " + unit); 
//                System.out.print(", quantity: " + quantity); 
//                System.out.print("\n");
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        }catch(SQLException se){
//            se.printStackTrace();
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            try{
//                if(stmt!=null) stmt.close();
//            }catch(SQLException se2){
//            }
//            try{
//                if(conn!=null) conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//	    System.out.print("搜索完毕\n");
//	    
        try{
            
            Class.forName(JDBC_DRIVER);
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            stmt = conn.createStatement();
            String sql;
            
//            sql ="SELECT * FROM activity where unit='g'";
//             ResultSet ts = stmt.executeQuery(sql);
//            while(ts.next()){
//                String action  = ts.getString("action");
//                String unit = ts.getString("unit");
//                int quantity = ts.getInt("quantity");
//                int quan = quantity*2;
//                
//                
//                
//                System.out.print("action: " + action);
//                System.out.print(", unit: " + unit); 
//                System.out.print(", quantity: " + quan); 
//                System.out.print("\n");
//            }
//            ts.close();
            sql = "SELECT * FROM activity ";
            ResultSet rs = stmt.executeQuery(sql);
            
   while(rs.next()){ 
       
                Double quantity = rs.getDouble("quantity");
               
               Double quan = quantity/1000;
               Double quan1 = quantity*500/1000;
             
         
              
                
                
               sql="UPDATE activity SET quantity=? WHERE unit = 'g'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
          ps.setDouble(1,quan);
          int id = rs.getInt("id");
          ps.setInt(2, id);
          
            
//          
        	ps.executeUpdate();
            
             sql="UPDATE activity SET unit='kg' WHERE unit='g'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
            ps.setInt(1, id);
        	ps.executeUpdate();
//            
////            
            sql="UPDATE activity SET quantity=? WHERE unit='ml'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
            
            ps.setDouble(1,quan);
            
          ps.setInt(2, id);
        	ps.executeUpdate();

            
                  sql="UPDATE activity SET unit='l' WHERE unit='ml'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
            ps.setInt(1, id);
        	ps.executeUpdate();
            
            
            
            
            sql="UPDATE activity SET quantity=? WHERE unit='pack(500g)'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
            
            ps.setDouble(1,quan1);
            
          ps.setInt(2, id);
        	ps.executeUpdate();

            
                  sql="UPDATE activity SET unit='kg' WHERE unit='pack(500g)'and id = ?"; 
            
            ps=conn.prepareStatement(sql);
            ps.setInt(1, id);
        	ps.executeUpdate();
         
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
           
            }
           ps.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            
            se.printStackTrace();
        }catch(Exception e){
        
            e.printStackTrace();
        }finally{
           
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Sucess!");
     
        
}
}
