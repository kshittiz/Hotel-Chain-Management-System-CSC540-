package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HotelPeopleLinks {
      int addHotelPeopleLinks(int hotel_id,int pid) {
          int id=0;
          Connection c = Database.getConnection();
          try {
              PreparedStatement exe = c.prepareStatement("insert into hotel_people_links(hotel_id,pid) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
              exe.setInt(1, hotel_id);
              exe.setInt(2, pid);
           
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
      public static void main(String[] args) {
      HotelPeopleLinks hl = new HotelPeopleLinks();
     int x= hl.addHotelPeopleLinks(1, 1);
      
  }
      
      
      
}

