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

    public int addRoomServiceLinks(int room_num, int service_id) throws SQLException {
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

}
