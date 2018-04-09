package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class RoomCategory {

    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean createRoomCategory(int hid, String room_category, int occupancy,
            int nightly_rate) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into room_category(hotel_id, room_category, occupancy,nightly_rate) values(?,?,?,?)");
            exe.setInt(1, hid);
            exe.setString(2, room_category);
            exe.setInt(3, occupancy);
            exe.setInt(4, nightly_rate);
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;

    }

}

