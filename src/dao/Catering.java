package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class Catering extends Staff {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into  catering_staff(pid, skill) values(?, ?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("skill"));
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
                    " Delete from catering_staff where pid=?");
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
                    "update catering_staff set skill=? where pid =?");

            exe.setString(1, obj2.getString("skill"));

            exe.setInt(2, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    /*public static void main(String[] args)
    {
        Catering cl = new Catering();
        JSONObject o = new JSONObject();
        
        o.put("skill","Pakoda pro");
        
       
        
        cl.deletePerson(7);
        cl.updatePerson(o,8);
    }*/
}
