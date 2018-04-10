package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Vector;

public class Room {
    private static Connection c = null;
    private static String[] strings = { "Room Number (*)", "Category", "Occupancy",
            "Availability" };
    public static Vector<String> COLUMNS = new Vector<String>(Arrays.asList(strings));

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean createRoom(int room_num, int hotel_id, String room_category, int occupancy,
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

    public Vector<Vector<Object>> getRoomDetails() {
        Vector<Vector<Object>> data = null;
        try {

            PreparedStatement exe = c.prepareStatement(
                    "Select room_num, room_category,occupancy,availability from room");
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

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }
}
