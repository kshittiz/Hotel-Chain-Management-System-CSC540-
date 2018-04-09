package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ContactInfo {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }
    public int addContactInfo(int phone_number,
            String email) {
        int contact_id = 0;
        
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into contact_info(phone_number,email) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, phone_number);
            exe.setString(2, email);
            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if (result.next())
                contact_id = result.getInt(1);
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return contact_id;

    }

}
