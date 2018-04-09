package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;

public class People {

    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        int peopleId = 0;
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into people(name,ssn, address, type) values(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, obj1.getString("name"));
            exe.setInt(2, obj1.getInt("SSN"));
            exe.setString(3, obj1.getString("address"));
            exe.setString(4, obj1.getString("type"));
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

    public boolean deletePerson(int pid) {

        try {
            PreparedStatement exe = c.prepareStatement(" Delete from people where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updatePerson(JSONObject obj2) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "update people set name=?,SSN=?,address=?, type=? where pid =?");

            exe.setString(1, obj2.getString("name"));
            exe.setInt(2, obj2.getInt("SSN"));
            exe.setString(3, obj2.getString("address"));
            exe.setString(4, obj2.getString("type"));
            exe.setInt(5, obj2.getInt("pid"));

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static int getPIDbySSN(String ssn) {
        int pid = 0;
        try {
            PreparedStatement exe = c.prepareStatement("SELECT pid from people where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            if (result.next())
                pid = result.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        return pid;
    }
}
