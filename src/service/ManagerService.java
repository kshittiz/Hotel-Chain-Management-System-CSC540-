package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONObject;

import dao.Catering;
import dao.Cleaning;
import dao.ContactInfo;
import dao.ContactLinks;
import dao.Customer;
import dao.Database;
import dao.Discount;
import dao.FrontDesk;
import dao.Hotel;
import dao.HotelPeopleLinks;
import dao.Manager;
import dao.People;
import dao.Room;
import dao.RoomCategory;
import dao.Service;
import dao.ServiceType;
import dao.Staff;
import view.LoginHMS;

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

    /**
     * Adding staff member in people hierarchy using transaction
     * 
     * @param JSONOBject
     *            obj
     * @return boolean
     */
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

    /**
     * Adding contact details using transaction
     * 
     * @param id
     * @param phone_number
     * @param email
     * @param type
     * @return boolean
     */
    public boolean addPersonalContactInfo(int id, String phone_number, String email, String type) {
        Connection c = Database.getConnection();
        try {
            // staring a transaction to add values in contact table
            c.setAutoCommit(false);
            ContactInfo.setConnnection(c);
            ContactInfo ci = new ContactInfo();
            int contact_id = ci.addContactInfo(phone_number, email);
            ContactLinks.setConnnection(c);
            ContactLinks cl = new ContactLinks();
            if ("people".equals(type))
                cl.CreateContactLinks(id, contact_id, "people");
            else
                cl.CreateContactLinks(id, contact_id, "hotel");

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

    public static boolean addNewRoom(String roomN, int hid, String category, String occup,
            String avail) {
        boolean result = false;
        Connection conn = Database.getConnection();
        Room.setConnnection(conn);
        Room r = new Room();
        try {
            if (r.createRoom(Integer.parseInt(roomN), hid, category, Integer.parseInt(occup),
                    avail)) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        }

        Database.endConnnection(conn);
        return result;

    }

    public static boolean addNewCategory(int hid, String category, int occup, String rate) {
        boolean result = false;
        Connection conn = Database.getConnection();
        RoomCategory.setConnnection(conn);
        RoomCategory rc = new RoomCategory();
        try {
            if (rc.createRoomCategory(hid, category, occup, Integer.parseInt(rate))) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        }

        Database.endConnnection(conn);
        return result;

    }

    public static boolean addNewService(String serviceN, int hid, String type) {
        boolean result = false;
        Connection conn = Database.getConnection();
        Service.setConnnection(conn);
        Service s = new Service();
        try {
            if (s.addService(Integer.parseInt(serviceN), hid, type)) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        }

        Database.endConnnection(conn);
        return result;

    }

    public static boolean addNewServiceType(String type, String price) {
        boolean result = false;
        Connection conn = Database.getConnection();
        ServiceType.setConnnection(conn);
        ServiceType st = new ServiceType();
        try {
            if (st.addServiceType(type, Integer.parseInt(price))) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        }

        Database.endConnnection(conn);
        return result;

    }

    public static boolean addNewDiscount(String billing_type, String disc) {
        boolean result = false;
        Connection conn = Database.getConnection();
        Discount.setConnnection(conn);
        Discount d = new Discount();
        try {
            if (d.addDiscount(billing_type, Integer.parseInt(disc))) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            result = false;
        }

        Database.endConnnection(conn);
        return result;

    }

    public static Vector<Vector<Object>> getHotelDetails(int hid) {
        Connection c = Database.getConnection();
        Hotel.setConnnection(c);
        Hotel rc = new Hotel();
        Vector<Vector<Object>> data = rc.getHotelDetails(hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getStaffDetails(String type) {
        Connection c = Database.getConnection();
        Staff.setConnnection(c);
        Staff s = new Staff();
        Vector<Vector<Object>> data = s.getStaffDetails(type, LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getStaffDetailsGroupedByRole() {
        Connection c = Database.getConnection();
        Staff.setConnnection(c);
        Staff s = new Staff();
        Vector<Vector<Object>> data = s.getStaffDetailsGroupedByRole(LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getCustomerDetails() {
        Connection c = Database.getConnection();
        Customer.setConnnection(c);
        Customer s = new Customer();
        Vector<Vector<Object>> data = s.getCustomerDetails(LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getRoomDetails() {
        Connection c = Database.getConnection();
        Room.setConnnection(c);
        Room r = new Room();
        Vector<Vector<Object>> data = r.getRoomDetails(LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getRoomCategoryDetails() {
        Connection c = Database.getConnection();
        RoomCategory.setConnnection(c);
        RoomCategory rc = new RoomCategory();
        Vector<Vector<Object>> data = rc.getRoomCategoriesForTable(LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static Vector<Vector<Object>> getServiceDetails() {
        Connection c = Database.getConnection();
        Service.setConnnection(c);
        Service s = new Service();
        Vector<Vector<Object>> data = s.getServiceDetails(LoginHMS.hid);
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

    public static Vector<Vector<Object>> getOccupancyStats(String type, String city) {
        Connection c = Database.getConnection();
        Room.setConnnection(c);
        Room r = new Room();
        Vector<Vector<Object>> data = r.getOccupancyStats(type, city, LoginHMS.hid);
        Database.endConnnection(c);
        return data;
    }

    public static boolean deleteStaff(HashMap<String, String> values) {
        boolean result = false;
        Connection c = Database.getConnection();
        People.setConnnection(c);
        People p = new People();
        int pid = Integer.parseInt(values.get("ID(*)"));
        if (pid != LoginHMS.pid)
            result = p.deletePerson(pid);
        Database.endConnnection(c);
        return result;
    }

    public static boolean deleteRoom(HashMap<String, String> values) {
        boolean result = false;
        Connection c = Database.getConnection();
        Room.setConnnection(c);
        Room r = new Room();
        int rid = Integer.parseInt(values.get("Room Number (*)"));
        if (r.getRoomAvailability(rid, LoginHMS.hid))
            result = r.deleteRoom(rid, LoginHMS.hid);
        Database.endConnnection(c);
        return result;
    }

    public static boolean deleteRoomCategory(HashMap<String, String> values) {
        Connection c = Database.getConnection();
        RoomCategory.setConnnection(c);
        RoomCategory r = new RoomCategory();
        String category = values.get("Room Category (*)");
        int occup = Integer.parseInt(values.get("Occupancy (*)"));
        boolean result = r.deleteRoomCategory(category, LoginHMS.hid, occup);
        Database.endConnnection(c);
        return result;
    }

    public static boolean deleteServiceType(HashMap<String, String> values) {
        Connection c = Database.getConnection();
        ServiceType.setConnnection(c);
        ServiceType st = new ServiceType();
        String type = values.get("Service Type (*)");
        boolean result = st.deleteServiceType(type);
        Database.endConnnection(c);
        return result;
    }

    public static boolean deleteService(HashMap<String, String> values) {
        Connection c = Database.getConnection();
        Service.setConnnection(c);
        Service st = new Service();
        int service_num = Integer.parseInt(values.get("Service Number (*)"));
        boolean result = st.deleteService(service_num, LoginHMS.hid);
        Database.endConnnection(c);
        return result;
    }

    public static boolean deleteDiscount(HashMap<String, String> values) {
        Connection c = Database.getConnection();
        Discount.setConnnection(c);
        Discount dis = new Discount();
        String billing_type = values.get("Billing Type (*)");
        boolean result = dis.deleteDiscount(billing_type);
        Database.endConnnection(c);
        return result;
    }
}
