package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;


public class CheckIn {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }
    public  int checkIn(int pid,int guests,Date checkin,Date checkout) {
        int cid=0; 
                
                 try {
                     PreparedStatement exe = c.prepareStatement("insert into checkin(pid,guests,checkin,checkout) values(?, ?,?,?)", Statement.RETURN_GENERATED_KEYS);
                     exe.setInt(1, pid);
                     exe.setInt(2, guests);
                    
                     exe.setDate(3,   new java.sql.Date(checkin.getTime()));
                     exe.setDate(4, new java.sql.Date(checkout.getTime()));
                     exe.executeQuery();
                     ResultSet result = exe.getGeneratedKeys();
                     if(result.next())
                         cid=result.getInt(1);
                     
                 } catch (Exception e) {
                     System.out.println(e);
                 }
                 return cid;
             }
   

}


