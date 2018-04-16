package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckInAttributes {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }
    public boolean addCheckInAttributes(int cid,int hotel_id,int room_num) {
        
        try {
            PreparedStatement exe = c.prepareStatement("insert into checkin_attributes(cid,hotel_id,room_num) values(?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            exe.setInt(1, cid);
            exe.setInt(2, hotel_id);
            exe.setInt(3,room_num);
         
            exe.executeQuery();
           
           
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    public static int cidUsingHidRoom_Num(int temphid,int tempRoomNo){
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select * from checkin_attributes where hotel_id=? and room_num=? ");
            exe.setInt(1, temphid);
            exe.setInt(2, tempRoomNo);
            ResultSet result = exe.executeQuery();
            if (result.next()) {
                return result.getInt("cid");

            }
           

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
   
  

}
