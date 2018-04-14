package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Vector;

public class ContactInfo {
    private static Connection c = null;
    private static String[] contacts = { "Contact ID", "Phone Number", "Email" };
    public static Vector<String> COLUMNS = new Vector<String>(Arrays.asList(contacts));

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addContactInfo(String phone_number, String email) throws SQLException {
        int contact_id = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into contact_info(phone_number,email) values(?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        if (!phone_number.isEmpty())
            exe.setString(1, phone_number);
        else
            exe.setNull(1, Types.VARCHAR);
        if (!email.isEmpty())
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

    public Vector<Vector<Object>> getContactDetails(int id, String type) throws Exception {
        Vector<Vector<Object>> data = null;

        PreparedStatement exe = c.prepareStatement(
                " select contact_id,phone_number,email from contact_links natural join contact_info where link_id = ? and type = ?");
        exe.setInt(1, id);
        exe.setString(2, type);
        ResultSet result = exe.executeQuery();
        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();
        // Data of the table
        data = new Vector<Vector<Object>>();
        while (result.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(result.getObject(i));
            }
            data.add(vector);
        }

        return data;
    }
}
