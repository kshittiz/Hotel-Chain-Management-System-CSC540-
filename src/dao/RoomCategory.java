package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomCategory {

    private int hid;
    private String room_category;
    private int occupancy;
    private int nightly_rate;
    private static Connection c = null;

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public String getRoom_category() {
        return room_category;
    }

    public void setRoom_category(String room_category) {
        this.room_category = room_category;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public int getNightly_rate() {
        return nightly_rate;
    }

    public void setNightly_rate(int nightly_rate) {
        this.nightly_rate = nightly_rate;
    }

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public boolean createRoomCategory(int hid, String room_category, int occupancy,
            int nightly_rate) {

        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into room_category(hotel_id, room_category, occupancy,nightly_rate) values(?,?,?,?)");
            exe.setInt(1, hid);
            exe.setString(2, room_category);
            exe.setInt(3, occupancy);
            exe.setInt(4, nightly_rate);
            exe.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static ArrayList<RoomCategory> getRoomCategories(int hid) {
        ArrayList<RoomCategory> list = new ArrayList<RoomCategory>();
        try {
            RoomCategory rc;
            PreparedStatement exe = c.prepareStatement(
                    "Select room_category, occupancy from room_category where hotel_id = ?");
            exe.setInt(1, hid);
            ResultSet result = exe.executeQuery();
            while (result.next()) {
                rc = new RoomCategory();
                rc.setRoom_category(result.getString(1));
                rc.setOccupancy(result.getInt(2));
                list.add(rc);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return list;
    }

}
