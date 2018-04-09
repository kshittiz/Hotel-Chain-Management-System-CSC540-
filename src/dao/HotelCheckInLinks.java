package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelCheckInLinks {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }
    public int addHotelCheckInLinks(int hotel_id, int cid) {
        int id = 0;

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into hotel_checkin_links(hotel_id,cid) values(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, hotel_id);
            exe.setInt(2, cid);

            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if(result.next())
                id=result.getInt(1);

          
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }
  

}
