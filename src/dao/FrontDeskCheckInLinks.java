package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FrontDeskCheckInLinks {
    public int addFrontDeskCheckInLinks(int pid, int cid) {
        int id = 0;
        Connection c = Database.getConnection();
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
            System.out.println(id);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }
    public static void main(String[] args) {
        FrontDeskCheckInLinks cil = new FrontDeskCheckInLinks();
        cil.addFrontDeskCheckInLinks(4, 1);

}
    }
/*
 * CREATE TABLE frontdesk_checkin_links( id int PRIMARY KEY AUTO_INCREMENT, pid
 * int NOT NULL, cid int NOT NULL, CONSTRAINT FK_frontdesk_checkin_links1
 * FOREIGN KEY (pid) REFERENCES front_desk(pid), CONSTRAINT
 * FK_frontdesk_checkin_links2 FOREIGN KEY (cid) REFERENCES checkin(cid) );
 */