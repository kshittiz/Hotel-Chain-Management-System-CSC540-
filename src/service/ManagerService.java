package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.json.JSONObject;

import dao.Catering;
import dao.Cleaning;
import dao.ContactInfo;
import dao.ContactLinks;
import dao.Database;
import dao.Discount;
import dao.FrontDesk;
import dao.HotelPeopleLinks;
import dao.Manager;
import dao.People;
import dao.Room;
import dao.RoomCategory;
import dao.Service;
import dao.ServiceType;
import dao.Staff;

public class ManagerService {
    public static String getNameLinkedwithSSN(String ssn) {
        String name = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "SELECT name from people natural join manager where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            if (result.next())

                name = result.getString(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    public static boolean addNewStaff(JSONObject obj) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in people hierarchy
            c.setAutoCommit(false);
            People.setConnnection(c);
            People p = new People();
            int pid = p.addPerson(obj);
            obj.put("pid", pid);

            Staff.setConnnection(c);
            p = new Staff();
            p.addPerson(obj);
            if ("manager".equals(obj.getString("department"))) {
                Manager.setConnnection(c);
                p = new Manager();
                p.addPerson(obj);
            } else if ("front_desk".equals(obj.getString("department"))) {
                FrontDesk.setConnnection(c);
                p = new FrontDesk();
                p.addPerson(obj);
            } else if ("catering".equals(obj.getString("department"))) {
                Catering.setConnnection(c);
                p = new Catering();
                p.addPerson(obj);
            } else {
                Cleaning.setConnnection(c);
                p = new Cleaning();
                p.addPerson(obj);
            }
            // setting hotel_people_links
            HotelPeopleLinks.setConnnection(c);
            HotelPeopleLinks hpl = new HotelPeopleLinks();
            hpl.addHotelPeopleLinks(obj.getInt("hotel_serving"), pid);

            // setting contact_info
            ContactInfo.setConnnection(c);
            ContactInfo info = new ContactInfo();
            String phone = obj.optString("phone").isEmpty() ? null : obj.optString("phone");
            String email = obj.optString("email").isEmpty() ? null : obj.optString("email");
            int contact_id = info.addContactInfo(phone, email);

            // setting contact links
            ContactLinks.setConnnection(c);
            ContactLinks link = new ContactLinks();
            link.CreateContactLinks(pid, contact_id, "people");

            // Committing transaction
            c.commit();
            // transaction ends
            return true;

        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            Database.endConnnection(c);
        }
    }

    public static Vector<Vector<Object>> getStaffDetails() {
        Connection c = Database.getConnection();
        Staff.setConnnection(c);
        Staff s = new Staff();
        Vector<Vector<Object>> data = s.getStaffDetails();
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getRoomDetails() {
        Connection c = Database.getConnection();
        Room.setConnnection(c);
        Room r = new Room();
        Vector<Vector<Object>> data = r.getRoomDetails();
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getRoomCategoryDetails() {
        Connection c = Database.getConnection();
        RoomCategory.setConnnection(c);
        RoomCategory rc = new RoomCategory();
        Vector<Vector<Object>> data = rc.getRoomDetails();
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getServiceDetails() {
        Connection c = Database.getConnection();
        Service.setConnnection(c);
        Service s = new Service();
        Vector<Vector<Object>> data = s.getServiceDetails();
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getServiceTypeDetails() {
        Connection c = Database.getConnection();
        ServiceType.setConnnection(c);
        ServiceType s = new ServiceType();
        Vector<Vector<Object>> data = s.getServiceTypeDetails();
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getDiscountDetails() {
        Connection c = Database.getConnection();
        Discount.setConnnection(c);
        Discount d = new Discount();
        Vector<Vector<Object>> data = d.getDiscountDetails();
        Database.endConnnection(c);
        return data;
    }
}
