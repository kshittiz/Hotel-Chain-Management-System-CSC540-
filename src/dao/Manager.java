package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


import org.json.JSONObject;

public class Manager extends Staff {
    public  int addPerson(JSONObject obj1) {
        Connection c = Database.getConnection();
        try {
            
            PreparedStatement exe = c.prepareStatement("insert into manager(pid, privilege) values(?,?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("privilege"));
           exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        
        return obj1.getInt("pid");
    }
}
