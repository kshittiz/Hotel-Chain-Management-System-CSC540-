package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class Cleaning extends Staff {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into cleaning_staff(pid, type) values(?, ?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("type"));
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }
        return obj1.getInt("pid");

    }
    public void deletePerson(int pid) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from cleaning_staff where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void updatePerson(JSONObject obj2, int pid) {
        Connection c = Database.getConnection();
        try {

            PreparedStatement exe = c.prepareStatement(
                    "update cleaning_staff set type=? where pid =?");

            exe.setString(1, obj2.getString("type"));

            exe.setInt(2, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
   /* public static void main(String[] args)
    {
        Cleaning cl = new Cleaning();
        JSONObject o = new JSONObject();
        
        o.put("type","kachrawala");
        
       
        
        cl.deletePerson(11);
        cl.updatePerson(o,12);
    }*/

}
