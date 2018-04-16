package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class CheckIn {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int checkIn(int pid, int guests, Timestamp checkin, Timestamp checkout) throws SQLException {
        int cid = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into checkin(pid,guests,checkin,checkout) values(?, ?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setInt(1, pid);
        exe.setInt(2, guests);
        exe.setTimestamp(3, checkin);
        exe.setTimestamp(4, checkout);
        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            cid = result.getInt(1);

        return cid;
    }
    public boolean updateRoomAfterCheckIn(int hid, int room_num)
    {
    	try {
            PreparedStatement exe = c.prepareStatement(
                    "update room set availability='unavailable' where room_num=? and hotel_id=?");
            exe.setInt(1, room_num);
            exe.setInt(2,hid);
           

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    
}
