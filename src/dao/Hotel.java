package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Hotel {
    private static Connection c = null;

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

}
