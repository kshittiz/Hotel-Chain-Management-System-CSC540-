package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Service {
    public void addService(int service_id,int hotel_id,String type) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into service(service_id, hotel_id,type) values(?, ?,?)");
            exe.setInt(1,service_id);
            exe.setInt(2, hotel_id);
            exe.setString(3,type);
            
           exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
    Service s = new Service();
    s.addService(14,1,"massage");
    }
}
/*CREATE TABLE service(
        service_id int PRIMARY KEY,
        hotel_id int NOT NULL,
        type varchar(30) NOT NULL,
        CONSTRAINT FK_service FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id),
        CONSTRAINT FK_service_type FOREIGN KEY (type) REFERENCES service_type(type)
     );*/
