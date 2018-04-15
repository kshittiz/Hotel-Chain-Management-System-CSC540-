package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import view.LoginHMS;

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

    public void updateHotel(HashMap<String, String> values) throws Exception {
        String query = "UPDATE hotel set";
        if (values.containsKey("hotel_name"))
            query = query + " hotel_name='" + values.get("hotel_name").toString() + "',";
        if (values.containsKey("hotel_address"))
            query = query + " hotel_address='" + values.get("hotel_address").toString() + "',";

        String finalQuery = query.subSequence(0, query.length() - 1).toString();
        finalQuery = finalQuery + " where hotel_id =" + LoginHMS.hid;
        Statement exe = c.createStatement();
        exe.executeQuery(finalQuery);
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
