package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class FrontDesk extends Staff {
    public int addPerson(JSONObject obj1) {

        Connection c = Database.getConnection();
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

    public void deletePerson(int pid) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from front_desk where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    /*
     * public static void main(String[] args) {
     * 
     * FrontDesk f = new FrontDesk(); JSONObject obj = new JSONObject();
     * 
     * obj.put("pid", 15); obj.put("gender", "male");
     * 
     * f.addPerson(obj);
     * 
     * System.out.println(obj);
     * 
     * 
     * 
     * }
     */

    public void updatePerson(JSONObject obj2, int pid) {
        Connection c = Database.getConnection();
        try {

            PreparedStatement exe = c.prepareStatement(
                    "update front_desk set gender=? where pid =?");

            exe.setString(1, obj2.getString("gender"));

            exe.setInt(2, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
   /* public static void main(String[] args)
    {
        FrontDesk f = new FrontDesk();
        JSONObject o = new JSONObject();
        
        o.put("gender","male");
        
       
        
        f.deletePerson(4);
        f.updatePerson(o,5);
    }*/

}
