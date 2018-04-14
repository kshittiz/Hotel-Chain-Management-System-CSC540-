package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Billing {
    

    private static Connection c = null;

    public static void setConnnection(Connection conn) {
       c = conn;
    }

    public static  int addBilling(int cid, int amount, int extra_discount, int tax, String billing_address,
            String billing_type) throws SQLException {
        
        int invoice_id = 0;
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
        c.close();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            invoice_id = result.getInt(1);

        return invoice_id;
    }
    public static void main(String[] args) throws SQLException
    {
        Billing.addBilling(2, 2000, 5, 5, "2713 Brigadoon Drive", "cash");
    }
   

}
