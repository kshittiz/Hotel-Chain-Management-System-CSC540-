package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Service {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean addService(int service_num, int hotel_id, String type) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into service(service_num, hotel_id,type) values(?, ?,?)");
            exe.setInt(1, service_num);
            exe.setInt(2, hotel_id);
            exe.setString(3, type);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
