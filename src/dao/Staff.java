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

}
