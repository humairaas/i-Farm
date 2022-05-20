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
	// 数据库的用户名与密码
	//用户名：root  密码：123456
	//请根据实际数据库的信息进行修改
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
//            sql = "SELECT * FROM activity";//搜索login表
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                String action  = rs.getString("action");//得到“id”列的值
//                String unit = rs.getString("unit");//得到“password”列的值
//                int quantity = rs.getInt("quantity");//得到所有值
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
//	    //开始修改数据
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            //连接数据库
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            //实例化Statement对象
            stmt = conn.createStatement();
            String sql;
            
//            sql ="SELECT * FROM activity where unit='g'";
//             ResultSet ts = stmt.executeQuery(sql);
//            while(ts.next()){
//                String action  = ts.getString("action");//得到“id”列的值
//                String unit = ts.getString("unit");//得到“password”列的值
//                int quantity = ts.getInt("quantity");//得到所有值
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
            sql = "SELECT * FROM activity ";//搜索login表
            ResultSet rs = stmt.executeQuery(sql);
            
   while(rs.next()){ 
       
                Double quantity = rs.getDouble("quantity");//得到所有值
               
               Double quan = quantity/1000;
               Double quan1 = quantity*500/1000;
             
         
              
                
                
               sql="UPDATE activity SET quantity=? WHERE unit = 'g'and id = ?"; 
            
            ps=conn.prepareStatement(sql);//修改数据预处理
          ps.setDouble(1,quan);
          int id = rs.getInt("id");
          ps.setInt(2, id);
          
            
//          
        	ps.executeUpdate();//执行修改数据
            
             sql="UPDATE activity SET unit='kg' WHERE unit='g'and id = ?"; 
            
            ps=conn.prepareStatement(sql);//修改数据预处理
            ps.setInt(1, id);
        	ps.executeUpdate();//执行修改数据
//            
////            
            sql="UPDATE activity SET quantity=? WHERE unit='ml'and id = ?"; 
            //向login表里修改数据^
            //注：几个问号几个ps.setString，上面的语句中有两个?,所以下面有两个ps.setString
            ps=conn.prepareStatement(sql);//修改数据预处理
            
            ps.setDouble(1,quan);
            
          ps.setInt(2, id);
        	ps.executeUpdate();
//执行修改数据
            // 完成后关闭
            
                  sql="UPDATE activity SET unit='l' WHERE unit='ml'and id = ?"; 
            
            ps=conn.prepareStatement(sql);//修改数据预处理
            ps.setInt(1, id);
        	ps.executeUpdate();//执行修改数据
            // 完成后关闭      
            
            
            
            sql="UPDATE activity SET quantity=? WHERE unit='pack(500g)'and id = ?"; 
            //向login表里修改数据^
            //注：几个问号几个ps.setString，上面的语句中有两个?,所以下面有两个ps.setString
            ps=conn.prepareStatement(sql);//修改数据预处理
            
            ps.setDouble(1,quan1);
            
          ps.setInt(2, id);
        	ps.executeUpdate();
//执行修改数据
            // 完成后关闭
            
                  sql="UPDATE activity SET unit='kg' WHERE unit='pack(500g)'and id = ?"; 
            
            ps=conn.prepareStatement(sql);//修改数据预处理
            ps.setInt(1, id);
        	ps.executeUpdate();//执行修改数据
            // 完成后关闭   
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
           
            }
           ps.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("数据修改成功");
        //修改数据结束
        
}
}
