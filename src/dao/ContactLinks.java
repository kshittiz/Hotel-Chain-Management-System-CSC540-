package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ContactLinks {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int CreateContactLinks(int link_id,
            int contact_id, String type) {

        int id = 0;

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into contact_links(link_id,contact_id, type) values(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, link_id);
            exe.setInt(2, contact_id);
            exe.setString(3, type);
            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if (result.next())
                id = result.getInt(1);

        } catch (Exception e) {
            System.out.println(e);
        }
        return id;

    }

}
