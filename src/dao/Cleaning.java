package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class Cleaning {
    public  void addPerson(JSONObject obj1) {
        
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into cleaning_staff(pid, type) values(?, ?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("type"));
           exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
        }

        
        
        
        
    }
 /*public static void main(String[] args) {

Cleaning c = new Cleaning();
JSONObject obj = new JSONObject();

obj.put("pid", 15);
obj.put("type", "Use cotton clothes");

c.addPerson(obj);

System.out.println(obj);



}
*/

}
