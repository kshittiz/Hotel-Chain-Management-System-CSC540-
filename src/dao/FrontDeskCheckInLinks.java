package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FrontDeskCheckInLinks {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addFrontDeskCheckInLinks(int pid, int cid) {
        int id = 0;

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into frontdesk_checkin_links(pid,cid) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, pid);
            exe.setInt(2, cid);

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
