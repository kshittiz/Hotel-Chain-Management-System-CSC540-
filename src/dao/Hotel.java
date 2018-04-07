package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;




import java.sql.ResultSet;


public class Hotel {
    public  int addHotel(String name,String address) {
        int hotelId=0; 
                 Connection c = Database.getConnection();
                 try {
                     PreparedStatement exe = c.prepareStatement("insert into hotel(hotel_name,hotel_address) values(?, ?);", Statement.RETURN_GENERATED_KEYS);
                     exe.setString(1, name);
                     exe.setString(2, address);
                     
                     exe.executeQuery();
                     ResultSet result = exe.getGeneratedKeys();
                     if(result.next())
                         hotelId=result.getInt(1);
                     c.close();
                 } catch (Exception e) {
                     System.out.println(e);
                 }
                 return hotelId;
             }
    public static void main(String[] args) {

        Hotel h = new Hotel();
        

      
    
      
        int x= h.addHotel("Holiday Inn","Raleigh");
        System.out.print(x);

    }

}
