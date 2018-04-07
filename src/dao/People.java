package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import org.json.JSONObject;

public class People {
    public enum type{
        customer,staff ;
    }
    public  boolean addPerson(JSONObject obj1) {
        
		        Connection c = Database.getConnection();
		        try {
		            PreparedStatement exe = c.prepareStatement("insert into people(name,ssn, type) values(?, ?, ?)");
		            exe.setString(1, (String) (obj1.get("name")));
		            exe.setInt(2, (int) (obj1.get("SSN")));
		            exe.setString(3,(obj1.get("type")).toString());
		            exe.executeQuery();
		            c.close();
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return true;
		    }

       
   /* Iterator<?> iterator =obj1.keySet().iterator();while(iterator.hasNext())
    {
        String key = (String) iterator.next();

        // System.out.println(obj1.get(key));
    }*/



    public static void main(String[] args) {

		People p = new People();
		JSONObject obj = new JSONObject();

		obj.put("name", "foo");
		obj.put("SSN", 997999799);
		obj.put("type", type.staff);
	
		System.out.println(obj);
		boolean x= p.addPerson(obj);

	}

}
