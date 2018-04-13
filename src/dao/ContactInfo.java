package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class ContactInfo {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addContactInfo(String phone_number, String email) throws SQLException {
        int contact_id = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into contact_info(phone_number,email) values(?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        if (phone_number != null)
            exe.setString(1, phone_number);
        else
            exe.setNull(1, Types.VARCHAR);
        if (email != null)
            exe.setString(2, email);
        else
            exe.setNull(2, Types.VARCHAR);
        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            contact_id = result.getInt(1);
        return contact_id;
    }

    public boolean deleteContact(int contact_id) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from contact_info where contact_id=?");
            exe.setInt(1, contact_id);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
