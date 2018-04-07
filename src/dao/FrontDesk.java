package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


import org.json.JSONObject;

public class FrontDesk extends Staff {
    public  int addPerson(JSONObject obj1) {
        
                 Connection c = Database.getConnection();
                 try {
                     PreparedStatement exe = c.prepareStatement("insert into front_desk(pid, gender) values(?, ?)");
                     exe.setInt(1, obj1.getInt("pid"));
                     exe.setString(2, obj1.getString("gender"));
                    exe.executeQuery();
                    
                    
                 } catch (Exception e) {
                     System.out.println(e);
                 }
        
                 
                 
                 return obj1.getInt("pid");
                 
             }
   /* public static void main(String[] args) {

        FrontDesk f = new FrontDesk();
        JSONObject obj = new JSONObject();

        obj.put("pid", 15);
        obj.put("gender", "male");
       
        f.addPerson(obj);
    
        System.out.println(obj);
       
  

    }*/
    
    
    
    
    
    
}
