package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }
    public int addBilling(int cid, int amount, int extra_discount,
            int tax, String billing_address,
            String billing_type) {
        int invoice_id = 0;
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into billing(cid,amount,extra_discount,tax,billing_address,billing_type) values(?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, cid);
            exe.setInt(2, amount);
            exe.setInt(3, extra_discount);
            exe.setInt(4, tax);
            exe.setString(5, billing_address);
            exe.setString(6, billing_type);

            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if (result.next())
                invoice_id = result.getInt(1);

          
        } catch (Exception e) {
            System.out.println(e);
        }

        return invoice_id;
    }

}
