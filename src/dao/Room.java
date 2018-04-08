package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Room {
    public void createRoom(int room_num,int hotel_id,String room_category,int occupancy,String availability) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into room(room_num, hotel_id,room_category,occupancy,availability) values(?, ?,?,?,?)");
            exe.setInt(1, room_num);
            exe.setInt(2, hotel_id);
            exe.setString(3, room_category);
            exe.setInt(4, occupancy);
            exe.setString(5,availability);
            
           exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        
        
    }
    public static void main(String[] args) {
        Room cr = new Room();
        cr.createRoom(1,2,"Executive",3,"available");
        }

}






/*CREATE TABLE room(
   room_num int PRIMARY KEY,
   hotel_id int NOT NULL,
   room_category varchar(15) NOT NULL,
   occupancy int NOT NULL,
   availability varchar(10) DEFAULT "available",
   CONSTRAINT FK_hotelroom FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
   CONSTRAINT FK_room_category FOREIGN KEY (room_category, occupancy) REFERENCES room_category(room_category, occupancy)
);*/
