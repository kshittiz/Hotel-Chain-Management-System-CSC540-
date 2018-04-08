package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ServiceType {
    public void addServiceType(String type,int price) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into service_type(type, price) values(?, ?)");
            exe.setString(1, type);
            exe.setInt(2, price);
            
           exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*public static void main(String[] args) {
    ServiceType st = new ServiceType();
    st.service_type("massage",500);
    }*/

}
/*CREATE TABLE service_type(
        type varchar(30) PRIMARY KEY,
        price int NOT NULL CHECK(price>=0)
     );*/