package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;



public class CheckIn {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int checkIn(int pid, int guests, Date checkin, Date checkout) throws SQLException {
        int cid = 0;
        PreparedStatement exe = c.prepareStatement(
                "insert into checkin(pid,guests,checkin,checkout) values(?, ?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setInt(1, pid);
        exe.setInt(2, guests);

        exe.setTimestamp(3, (java.sql.Timestamp)(checkin));
        exe.setTimestamp(4, (java.sql.Timestamp)(checkout));
        exe.executeQuery();
        ResultSet result = exe.getGeneratedKeys();
        if (result.next())
            cid = result.getInt(1);

        return cid;
    }
    public static boolean updateCheckOutTime(int cid) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "update checkin set checkout =? where cid=?"
                    );
            java.util.Date today = new java.util.Date();
            
            exe.setTimestamp(1, new java.sql.Timestamp (today.getTime()));
            exe.setInt(2,cid);
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
        
    }
    public static int durationUsingCID (int tempCID) {
        try {
            PreparedStatement exe = c.prepareStatement("select * from checkin where cid=? ");
            exe.setInt(1, (tempCID));
            ResultSet result = exe.executeQuery();
            if (result.next()) {

                java.sql.Timestamp tempcheckin = result.getTimestamp("checkin");
                java.util.Date today = new java.util.Date();
                java.sql.Timestamp tempcheckout=new java.sql.Timestamp (today.getTime());

            

                return (int) ((tempcheckout.getTime() - tempcheckin.getTime()) / (1000 * 24 * 60 * 60));
            }
          
            

        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

}
