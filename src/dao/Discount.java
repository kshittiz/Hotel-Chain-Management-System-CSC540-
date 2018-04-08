package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Discount {
    public void discount(String billing_type,
            int discount) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into discount(billing_type,discount) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, billing_type);
            exe.setInt(2, discount);
            exe.executeQuery();
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
       

    }
    public static void main(String[] args)
    {
       Discount d = new Discount();
       d.discount("Credit Card",5);
    }
    

}
/*
 * CREATE TABLE discount( billing_type varchar(30) PRIMARY KEY, discount int NOT
 * NULL DEFAULT 0 );
 */