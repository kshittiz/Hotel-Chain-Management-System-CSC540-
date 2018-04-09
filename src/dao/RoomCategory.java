package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import org.json.JSONObject;
public class RoomCategory {
    public void  createRoomCategory(String room_category,int occupancy,int nightly_rate) {
      
            
            Connection c = Database.getConnection();
            try {
                PreparedStatement exe = c.prepareStatement("insert into room_category(room_category, occupancy,nightly_rate) values(?, ?,?)");
                exe.setString(1, room_category);
                exe.setInt(2, occupancy);
                exe.setInt(3, nightly_rate);
                
               exe.executeQuery();
               
               
            } catch (Exception e) {
                System.out.println(e);
            }
    
    
    
   
}
 /*public static void main(String[] args) {
RoomCategory crc = new RoomCategory();
crc.createRoomCategory("Executive",3,300);
 }*/

}
/*CREATE TABLE room_category(
        room_category varchar(15),
        occupancy int NOT NULL CHECK(occupancy>=1 and occupancy<=4),
        nightly_rate int NOT NULL CHECK(nightly_rate>=0),
        PRIMARY KEY(room_category, occupancy)
     );*/