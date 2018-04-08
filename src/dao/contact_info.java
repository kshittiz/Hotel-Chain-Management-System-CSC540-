package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class contact_info {
    public int addConactInfo(int phone_number,String email) {
        int contact_id=0; 
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("insert into contact_info(phone_number,email) values(?, ?)", Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, phone_number);
            exe.setString(2, email);
            exe.executeQuery();
            ResultSet result = exe.getGeneratedKeys();
            if(result.next())
                contact_id=result.getInt(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
/*        System.out.println(contact_id);
*/        return contact_id;
     
    }
    public static void main(String[] args)
    {
        contact_info ci = new contact_info();
       int x= ci.addConactInfo(999999888, "chor@choru.com");
       
    }
 
}
/* CREATE TABLE contact_info(
contact_id int PRIMARY KEY AUTO_INCREMENT,
phone_number int NOT NULL UNIQUE,
email varchar(40) DEFAULT NULL UNIQUE
);*/