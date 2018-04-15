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

    public int addRoomServiceLinks(int room_num, int service_id,int hid,int pid) throws SQLException {
        int id = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into room_service_links(room_num,service_num,hotel_id_room, hotel_id_service,staff_id ) values(?, ?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setInt(1, room_num);
        exe.setInt(2, service_id);
        exe.setInt(3, hid);
        exe.setInt(4, hid);
        exe.setInt(5, pid);



        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            id = result.getInt(1);

        return id;

    }

}
