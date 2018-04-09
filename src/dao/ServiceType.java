package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    public static String[] getServices() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            PreparedStatement exe = c.prepareStatement("Select type from service_type");
            ResultSet result = exe.executeQuery();
            while (result.next())
                list.add(result.getString(1));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return list.toArray(new String[list.size()]);
    }
}
