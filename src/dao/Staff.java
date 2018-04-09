package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.json.JSONObject;

public class Staff extends People {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into staff(pid, job_title, hotel_serving, age, department) values(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("job_title"));
            exe.setInt(3, obj1.getInt("hotel_serving"));
            exe.setInt(4, obj1.getInt("age"));
            exe.setString(5, obj1.getString("department"));
            exe.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return obj1.getInt("pid");
    }

    public boolean deletePerson(int pid) {

        try {
            PreparedStatement exe = c.prepareStatement(" Delete from staff where pid=?");
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
                    "update staff set job_title=?,hotel_serving=?,age=?,department=? where pid =?");

            exe.setString(1, obj2.getString("job_title"));
            exe.setInt(2, obj2.getInt("hotel_serving"));
            exe.setInt(3, obj2.getInt("age"));
            exe.setString(4, obj2.getString("department"));

            exe.setInt(5, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
