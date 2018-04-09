package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Room {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean createRoom(int room_num, int hotel_id,
            String room_category, int occupancy,
            String availability) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into room(room_num, hotel_id,room_category,occupancy,availability) values(?, ?,?,?,?)");
            exe.setInt(1, room_num);
            exe.setInt(2, hotel_id);
            exe.setString(3, room_category);
            exe.setInt(4, occupancy);
            exe.setString(5, availability);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
