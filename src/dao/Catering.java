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
}
