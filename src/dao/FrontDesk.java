package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class FrontDesk extends Staff {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into front_desk(pid, gender) values(?, ?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("gender"));
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }

        return obj1.getInt("pid");

    }

    public boolean deletePerson(int pid) {
        
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from front_desk where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

      
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updatePerson(JSONObject obj2, int pid) {
       
        try {

            PreparedStatement exe = c.prepareStatement(
                    "update front_desk set gender=? where pid =?");

            exe.setString(1, obj2.getString("gender"));

            exe.setInt(2, pid);

            exe.executeQuery();

          
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
