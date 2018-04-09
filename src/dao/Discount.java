package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Discount {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean discount(String billing_type,
            int discount) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into discount(billing_type,discount) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, billing_type);
            exe.setInt(2, discount);
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
