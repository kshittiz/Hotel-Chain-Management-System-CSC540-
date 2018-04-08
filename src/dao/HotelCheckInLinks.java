package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelCheckInLinks {
    public int addHotelCheckInLinks(int hotel_id, int cid) {
        int id = 0;
        Connection c = Database.getConnection();
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

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }
    public static void main(String[] args) {
    HotelCheckInLinks cil = new HotelCheckInLinks();
    cil.addHotelCheckInLinks(1, 1);
    
}

}
/*
 * CREATE TABLE hotel_checkin_links( id int PRIMARY KEY AUTO_INCREMENT, hotel_id
 * int NOT NULL, cid int NOT NULL, CONSTRAINT FK_hotel_checkin_links1 FOREIGN
 * KEY (hotel_id) REFERENCES hotel(hotel_id), CONSTRAINT FK_hotel_checkin_links2
 * FOREIGN KEY (cid) REFERENCES checkin(cid) );
 */