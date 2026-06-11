package vmm;
import java.sql.*;

public class DBLoader {
    
//    private static final String url = "jdbc:mysql://localhost:3306/?user=root/vmm"; //agr ye line use kar rahe ho toh database ka name likhna padega saath mein like this: vmm.students
    
    private static final String url = "jdbc:mysql://localhost:3306/kp_luxestays"; //agr aise likhenge toh database likhne ki zrurt nhi hogi like this: students
    private static final String username = "root";
    private static final String password = "Kashyap@123";
    
    public static ResultSet executeQuery(String smt){
 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully!!");

            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection build");

            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Statement created");

            ResultSet rs = stmt.executeQuery(smt);
            System.out.println("ResultSet created");

            return rs;
        }catch(ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
