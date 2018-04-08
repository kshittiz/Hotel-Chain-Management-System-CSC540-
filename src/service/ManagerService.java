package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import dao.Catering;
import dao.Cleaning;
import dao.Database;
import dao.FrontDesk;
import dao.People;
import dao.Staff;

public class ManagerService {
    public static String getNameLinkedwithSSN(String ssn) {
        String name = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "SELECT name from people natural join manager where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            if (result.next())

                name = result.getString(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public static boolean addNewStaff(JSONObject obj) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in people heirarchy
            c.setAutoCommit(false);
            People p = new People();
            p.addPerson(obj);
            p = new Staff();
            p.addPerson(obj);
            if ("front_desk".equals(obj.getString("department"))) {
                p = new FrontDesk();
                p.addPerson(obj);
            } else if ("catering".equals(obj.getString("department"))) {
                p = new Catering();
                p.addPerson(obj);
            } else {
                p = new Cleaning();
                p.addPerson(obj);
            }
            c.commit();
            // transaction ends
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Database.endConnnection(c);
        }
    }
}
