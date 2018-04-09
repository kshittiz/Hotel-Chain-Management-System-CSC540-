package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;

public class People {

    private static Connection c = null;

    /*public enum type {
        customer, staff, chairman;
    }*/

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        int peopleId = 0;
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into people(name,ssn, type) values(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, obj1.getString("name"));
            exe.setInt(2, obj1.getInt("SSN"));
            exe.setString(3, obj1.getString("type"));
            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if (result.next())
                peopleId = result.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return peopleId;
    }

    public static String getTypeBySSN(String ssn) {
        String type = null;
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

    public void deletePerson(int pid) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from people where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void updatePerson( JSONObject obj2 ) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "update people set name=?,SSN=?,type=? where pid =?");
            
        
            exe.setString(1, obj2.getString("name"));
            exe.setInt(2, obj2.getInt("SSN"));
            exe.setString(3,obj2.getString("type"));
            exe.setInt(4,obj2.getInt("pid"));

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
  /*  public static void main(String[] args)
    {
        People p = new People();
        JSONObject o = new JSONObject();
        
        o.put("name","ojas Ambani");
        o.put("SSN", 221311231);
        o.put("type", "customer");
        o.put("pid", 14);
        
        p.updatePerson(o);
    }*/
}
