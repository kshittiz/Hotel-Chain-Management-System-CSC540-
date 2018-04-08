package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CheckInAttributes {
    public void addCheckInAttributes(int cid,int hotel_id,int room_num) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into checkin_attributes(cid,hotel_id,room_num) values(?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, cid);
            exe.setInt(2, hotel_id);
            exe.setInt(3,room_num);
         
            exe.executeQuery();
           
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     public static void main(String[] args) {
    CheckInAttributes cia = new CheckInAttributes();
    cia.addCheckInAttributes(1, 1,1);
    
}
  

}
/*CREATE TABLE checkin_attributes(
        cid int,
        hotel_id int,
        room_num int,
        CONSTRAINT FK_checkin_attributes FOREIGN KEY (cid) REFERENCES checkin(cid),
        CONSTRAINT FK_checkin_attributes_hotel FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
        CONSTRAINT FK_checkin_attributes_hotel_rooms FOREIGN KEY (room_num) REFERENCES room(room_num),
        PRIMARY KEY(cid, hotel_id, room_num)
     );*/