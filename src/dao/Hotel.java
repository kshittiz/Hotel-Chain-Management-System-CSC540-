package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.ResultSet;

public class Hotel {
    public int addHotel(String name, String address) {
        int hotelId = 0;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into hotel(hotel_name,hotel_address) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, name);
            exe.setString(2, address);

            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if (result.next())
                hotelId = result.getInt(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return hotelId;
    }

    public void deleteHotel(int hotel_id) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from hotel where hotel_id=?",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, hotel_id);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateHotel(int hotel_id, String hotel_name,
            String hotel_address) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "update hotel set hotel_name=?,hotel_address=? where hotel_id =?",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setString(1, hotel_name);
            exe.setString(2, hotel_address);
            exe.setInt(3, hotel_id);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /*
     * public static void main(String[] args) {
     * 
     * Hotel h = new Hotel();
     * 
     * h.addHotel("lolol", "lollo");
     * 
     * }
     */
}
