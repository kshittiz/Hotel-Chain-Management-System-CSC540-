package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class Catering extends Staff {
    public  int addPerson(JSONObject obj1) {
    Connection c = Database.getConnection();
    try {
 
        PreparedStatement exe = c.prepareStatement("insert into  catering_staff(pid, skill) values(?, ?)");
        exe.setInt(1, obj1.getInt("pid"));
        exe.setString(2, obj1.getString("skill"));
       exe.executeQuery();
       
       
    } catch (Exception e) {
        System.out.println(e);
    }
return obj1.getInt("pid");
    
    
    
    
}
/*public static void main(String[] args) {

Catering ca = new Catering();
JSONObject obj = new JSONObject();

obj.put("pid", 38);
obj.put("skill", "Indian food");

ca.addPerson(obj);

System.out.println(obj);



}*/


}
