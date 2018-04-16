package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONObject;

import dao.CheckIn;
import dao.CheckInAttributes;
import dao.ContactInfo;
import dao.ContactLinks;
import dao.Customer;
import dao.Database;
import dao.FrontDeskCheckInLinks;
import dao.HotelCheckInLinks;
import dao.HotelPeopleLinks;
import dao.People;
import dao.RoomServiceLinks;
import dao.Service;
import view.LoginHMS;
import view.UpdateCustomer;

public class FrontDeskService {
    public static String getNameLinkedwithSSN(String ssn) {
        String name = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("SELECT name from people natural join front_desk where ssn = ?");
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

    public static HashMap<Integer, String> getAllPeople(int hotel_id) {

        HashMap<Integer, String> map = new HashMap<>();

        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c
                    .prepareStatement("SELECT pid,name from people natural join hotel_people_links where hotel_id = ?");
            exe.setInt(1, hotel_id);
            ResultSet result = exe.executeQuery();
            while (result.next()) {
                map.put(result.getInt(1), result.getString(2));
            }
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return map;
    }

    public static boolean checkIfPersonPresent(String ssn) {
        int count = 0;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement("SELECT count(*) from people where ssn = ?");
            exe.setString(1, ssn);
            ResultSet result = exe.executeQuery();
            if (result.next())
                count = result.getInt(1);
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (count == 0)
            return false;
        else
            return true;
    }

    public static boolean addNewCustomer(JSONObject obj) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in people hierarchy
            c.setAutoCommit(false);
            People.setConnnection(c);
            People p = new People();
            int pid = p.addPerson(obj);
            obj.put("pid", pid);

            Customer.setConnnection(c);
            p = new Customer();
            p.addPerson(obj);

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

    public static HashMap<Integer, String> checkRoomAvailable(int hid, int numguest, String category) {
        String availability = "unavailable";
        HashMap<Integer, String> map = new HashMap<Integer, String>();

        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "SELECT room_num,availability from room where hotel_id=? and occupancy=? and room_category=? and availability='available'");
            exe.setInt(1, hid);
            exe.setInt(2, numguest);
            exe.setString(3, category);
            ResultSet result = exe.executeQuery();
            if (result.next()) {
                availability = result.getString(2);
                map.put(result.getInt(1), result.getString(2));
            }
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (availability.equals("available"))
            return map;
        else
            return null;

    }

    public static boolean addNewCheckIn(JSONObject obj) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in people hierarchy
            c.setAutoCommit(false);
            CheckIn.setConnnection(c);
            CheckIn checkin = new CheckIn();
            int cid = checkin.checkIn(obj.getInt("pid"), obj.getInt("guests"), (Timestamp) obj.get("checkin"),
                    (Timestamp) obj.get("checkout"));
            checkin.updateRoomAfterCheckIn(LoginHMS.hid, obj.getInt("room_num"));

            HotelCheckInLinks.setConnnection(c);
            HotelCheckInLinks h = new HotelCheckInLinks();
            h.addHotelCheckInLinks(LoginHMS.hid, cid);

            FrontDeskCheckInLinks.setConnnection(c);
            FrontDeskCheckInLinks f = new FrontDeskCheckInLinks();
            f.addFrontDeskCheckInLinks(LoginHMS.pid, cid);

            CheckInAttributes.setConnnection(c);
            CheckInAttributes checkin_attr = new CheckInAttributes();
            checkin_attr.addCheckInAttributes(cid, LoginHMS.hid, obj.getInt("room_num"));

            Service.setConnnection(c);
            RoomServiceLinks.setConnnection(c);
            Service s = new Service();
            RoomServiceLinks r = new RoomServiceLinks();

            if (obj.getString("category").contains("Presidential")) {
                int room_service = s.getservicenum("room service", LoginHMS.hid);
                int catering = s.getservicenum("catering", LoginHMS.hid);

                int staff_id = s.getStaffServing(LoginHMS.hid, "catering");
                r.addRoomServiceLinks(obj.getInt("room_num"), room_service, LoginHMS.hid, LoginHMS.pid);
                r.addRoomServiceLinks(obj.getInt("room_num"), catering, LoginHMS.hid, staff_id);

            }

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

    public static boolean requestNewService(JSONObject obj) {
        Connection c = Database.getConnection();

        try {
            // staring a transaction to add values in people hierarchy
            c.setAutoCommit(false);

            RoomServiceLinks.setConnnection(c);
            RoomServiceLinks roomservice = new RoomServiceLinks();
            roomservice.addRoomServiceLinks(obj.getInt("room_num"), obj.getInt("service_num"), obj.getInt("hotel_id"),
                    obj.getInt("staff_id"));

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

    public static boolean updateCustomer(JSONObject obj) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in people hierarchy
            c.setAutoCommit(false);
            People.setConnnection(c);
            People p = new People();
            int pid = People.getPIDbySSN(obj.getString("original_ssn"));

            if (!obj.getString("name").isEmpty())
                p.fdupdatePerson(obj.getString("name"), pid);

            Customer.setConnnection(c);
            Customer cust = new Customer();

            if (!obj.getString("date_of_birth").isEmpty()) {
                int count = cust.checkIfCustomerExists(pid);
                if (count == 0) {
                    JSONObject obj1 = new JSONObject();
                    obj1.put("pid", pid);
                    obj1.put("date_of_birth", obj.getString("date_of_birth"));
                    cust.addPerson(obj1);
                } else {
                    cust.updatePerson(obj.getString("date_of_birth"), pid);
                }
            }

            ContactInfo.setConnnection(c);
            ContactInfo info = new ContactInfo();
            int contactid = Integer.parseInt(UpdateCustomer.contactId.getText());

            
            String phone = obj.optString("phone");
            String email = obj.optString("email");

            if (contactid == 0) {
                contactid = info.addContactInfo(phone, email);
                ContactLinks.setConnnection(c);
                ContactLinks contact = new ContactLinks();
                contact.CreateContactLinks(pid, contactid, "people");

            } else {
                HashMap<String, String> map = new HashMap<String, String>();

                map.put("phone_number", phone);
                map.put("email", email);
                info.updateContactDetails(map, contactid);
            }

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

    public static Vector<Vector<Object>> getContactDetails(String ssn) {
        Vector<Vector<Object>> contacts;
        Connection c = Database.getConnection();
        People.setConnnection(c);
        int pid = People.getPIDbySSN(ssn);
        ContactInfo.setConnnection(c);
        ContactInfo ci = new ContactInfo();
        try {
            contacts = ci.getContactDetails(pid, "people");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contacts;

    }

    public static Vector<Vector<Object>> getRoomServicesOfferedByStaff(String room_num) {
        Connection c = Database.getConnection();
        RoomServiceLinks.setConnnection(c);
        RoomServiceLinks r = new RoomServiceLinks();
        Vector<Vector<Object>> data = r.getRoomServicesOfferedByStaff(Integer.parseInt(room_num), LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

}
