package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Hotel {
    private static Connection c = null;
    private static String[] strings = { "Hotel ID (*)", "Hotel Name", "Hotel Address" };
    public static Vector<String> COLUMNS = new Vector<String>(Arrays.asList(strings));

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addHotel(String name, String address) throws SQLException {
        int hotelId = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into hotel(hotel_name,hotel_address) values(?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setString(1, name);
        exe.setString(2, address);

        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            hotelId = result.getInt(1);

        return hotelId;
    }

    public boolean deleteHotel(int hotel_id) {
        try {
            PreparedStatement exe = c.prepareStatement(" Delete from hotel where hotel_id=?",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, hotel_id);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updateHotel(int hotel_id, String hotel_name, String hotel_address) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "update hotel set hotel_name=?,hotel_address=? where hotel_id =?",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, hotel_name);
            exe.setString(2, hotel_address);
            exe.setInt(3, hotel_id);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public Vector<Vector<Object>> getHotelDetails(int hid) {
        Vector<Vector<Object>> data = null;
        try {
            PreparedStatement exe = null;
            if (hid == 0)
                exe = c.prepareStatement("Select * from hotel");
            else {
                exe = c.prepareStatement("Select * from hotel where hotel_id = ?");
                exe.setInt(1, hid);
            }
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
