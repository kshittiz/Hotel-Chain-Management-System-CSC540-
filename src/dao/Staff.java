package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import org.json.JSONObject;
public class Staff extends People {
   
    public  int addPerson(JSONObject obj1) {
        
      
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into staff(pid, job_title, hotel_serving, age, department) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("job_title"));
            exe.setInt(3,obj1.getInt("hotel_serving"));
            exe.setInt(4, obj1.getInt("age"));
            exe.setString(5, obj1.getString("department"));
            exe.executeQuery();
           
           
           
            c.close();        
        } catch (Exception e) {
            System.out.println(e);
        }
         return obj1.getInt("pid");   
    }
    
   
    public static void main(String[] args) {

        Staff s = new Staff();
        JSONObject obj = new JSONObject();

        obj.put("pid", 38);
        obj.put("job_title", "Chef");
        obj.put("hotel_serving", 1);
        obj.put("age",13);
        obj.put("department","catering");
        s.addPerson(obj);
    
        
       
  

    }
}
