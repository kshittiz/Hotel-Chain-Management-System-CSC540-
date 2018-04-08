package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import org.json.JSONObject;

public class People {
    public enum type{
        customer,staff,chairman;
    }
    public  int addPerson(JSONObject obj1) {
       int peopleId=0; 
		        Connection c = Database.getConnection();
		        try {
		            PreparedStatement exe = c.prepareStatement("insert into people(name,ssn, type) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		            exe.setString(1, (String) (obj1.get("name")));
		            exe.setInt(2, (int) (obj1.get("SSN")));
		            exe.setString(3,(obj1.get("type")).toString());
		            exe.executeQuery();
		            ResultSet result = exe.getGeneratedKeys();
		            if(result.next())
		                peopleId=result.getInt(1);
		            c.close();
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return peopleId;
		    }

       
   /* Iterator<?> iterator =obj1.keySet().iterator();while(iterator.hasNext())
    {
        String key = (String) iterator.next();

        // System.out.println(obj1.get(key));
    }*/

    public static String getTypeBySSN(String ssn) {
        String type = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("SELECT type from people where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            if (result.next())
                type = result.getString(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return type;
    }
    public static boolean checkSSNAvailability(String ssn) {
        boolean check = false;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("SELECT name from people where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            check = result.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return check;
    }
    public static void main(String[] args) {

		People p = new People();
		JSONObject obj = new JSONObject();

		obj.put("name", "SShaivam");
		obj.put("SSN", 517777822);
		obj.put("type", type.staff);
	
		System.out.println(obj);
		int x= p.addPerson(obj);
		System.out.print(x);

	}
}
