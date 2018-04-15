package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomServiceLinks {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public static int addRoomServiceLinks(int room_num, int service_id) throws SQLException {
        int id = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into room_service_links(room_num,service_id) values(?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setInt(1, room_num);
        exe.setInt(2, service_id);
        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            id = result.getInt(1);

        return id;

    }

    public static int getServiceNumber(int temphid, int tempRoomNo) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select service_num from room_service_links where hotel_id_room=? and room_num=?");
            exe.setInt(1, (temphid));
            exe.setInt(2, (tempRoomNo));
            ResultSet result = exe.executeQuery();
            if (result.next()) {
                return result.getInt("service_num");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

}
