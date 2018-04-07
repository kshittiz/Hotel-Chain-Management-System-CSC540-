package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.Database;

public class ManagerService {
    public static String getNameLinkedwithSSN(String ssn) {
        String name = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("SELECT name from people natural join manager where ssn = ?");
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
}
