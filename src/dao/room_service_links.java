package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class room_service_links {
    public int addRoomServiceLinks(int room_num,int service_id) {
        int id=0; 
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into room_service_links(room_num,service_id) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, room_num);
            exe.setInt(2, service_id);
            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if(result.next())
                id=result.getInt(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(id);
        return id;
     
    }

    public static void main(String[] args)
    {
        room_service_links rsl = new room_service_links();
        rsl.addRoomServiceLinks(1,2);
    }
    
}


/*CREATE TABLE room_service_links(
   id int PRIMARY KEY AUTO_INCREMENT,
   room_num int NOT NULL,
   service_id int NOT NULL,
   CONSTRAINT FK_room_service_links1 FOREIGN KEY (room_num) REFERENCES room(room_num),
   CONSTRAINT FK_room_service_links2 FOREIGN KEY (service_id) REFERENCES service(service_id)
);*/