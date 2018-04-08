package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class ContactLinks {
    
    public int CreateContactLinks(int link_id,int contact_id, String type) {
       
            int id=0; 
                     Connection c = Database.getConnection();
                     try {
                         PreparedStatement exe = c.prepareStatement("insert into contact_links(link_id,contact_id, type) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                         exe.setInt(1, link_id);
                         exe.setInt(2, contact_id);
                         exe.setString(3,type);
                         exe.executeQuery();
                         ResultSet result = exe.getGeneratedKeys();
                         if(result.next())
                             id=result.getInt(1);
                         c.close();
                     } catch (Exception e) {
                         System.out.println(e);
                     }
/*                     System.out.println(id);
*/                     return id;
                 
    }
    /*public static void main(String[] args) {
        contact_links cl = new contact_links();
       int x= cl.CreateContactLinks(1,15,"hotel");
        
    }*/
    

}


/*CREATE TABLE contact_links(
        id int PRIMARY KEY AUTO_INCREMENT,
        link_id int NOT NULL,
        contact_id int NOT NULL UNIQUE,
        type ENUM("people", "hotel") NOT NULL, 
        CONSTRAINT FK_PeopleHotelcontacts FOREIGN KEY (contact_id) REFERENCES contact_info(contact_id)
     );*/