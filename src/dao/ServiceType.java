package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServiceType {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean addServiceType(String type, int price) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into service_type(type, price) values(?, ?)");
            exe.setString(1, type);
            exe.setInt(2, price);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
