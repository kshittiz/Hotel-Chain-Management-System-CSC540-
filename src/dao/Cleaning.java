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

}
