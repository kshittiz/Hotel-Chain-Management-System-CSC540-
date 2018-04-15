package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import dao.Billing;
import dao.CheckIn;
import dao.CheckInAttributes;
import dao.ContactInfo;
import dao.ContactLinks;
import dao.Customer;
import dao.Database;
import dao.HotelPeopleLinks;
import dao.People;
import dao.Room;
import dao.RoomCategory;
import dao.RoomServiceLinks;
import dao.Service;
import dao.ServiceType;
import view.LoginHMS;

public class FrontDeskService {
    public static String getNameLinkedwithSSN(String ssn) {
        String name = null;
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    "SELECT name from people natural join front_desk where ssn = ?");
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

    public static int calculateAmount(String room_num, String Discount, String Billing_Type,
            String tax, String billingadress) throws NumberFormatException, SQLException {
        // Initializing the connection
        Connection c = Database.getConnection();

        try {
            // Initializing auto commit for transaction
            c.setAutoCommit(false);

            // Initializing variables from input data
            int amount = 0;
            int temphid = LoginHMS.hid;
            int tempRoomNo = Integer.parseInt(room_num);
            int finalDiscount = Integer.parseInt(Discount);

            // Setting the variable values obtained from different classes
            Billing.setConnnection(c);
            int intialDiscount = Billing.discountOnBillType(Billing_Type); // Get discount based on
                                                                           // payment method
            CheckInAttributes.setConnnection(c);
            int tempCID = CheckInAttributes.cidUsingHidRoom_Num(temphid, tempRoomNo); // Get
                                                                                      // checkInAttributes
            CheckIn.setConnnection(c);
            int duration = CheckIn.durationUsingCID(tempCID);// Get total duration spend by customer
                                                             // in particular room
            Room.setConnnection(c);
            String temproom_category = Room.roomCat(tempRoomNo, temphid);// Get room_category
            int tempoccupancy = Room.roomOccupancy(tempRoomNo, temphid);// Get room occupancy
            RoomCategory.setConnnection(c);
            int tempNightlyRate = RoomCategory.nightlyRate(temphid, temproom_category,
                    tempoccupancy);// Get nightly rate for the room at the given hotel
            RoomServiceLinks.setConnnection(c);
            int tempServiceNum = RoomServiceLinks.getServiceNumber(temphid, tempRoomNo);
            Service.setConnnection(c);
            String tempServiceType = Service.getServiceType(temphid, tempServiceNum);
            ServiceType.setConnnection(c);
            int serviceAmount = ServiceType.getServiceAmount(tempServiceType);

            // Calculating the total amount
            amount = amount + tempNightlyRate * duration;
            amount = amount + serviceAmount;
            amount = amount - amount * ((finalDiscount + intialDiscount) / 100);
            amount = amount + amount * Integer.parseInt(tax) / 100;

            // Makes changes in databases affected by room checkout
            Billing.setConnnection(c);
            Billing.addBilling(tempCID, Integer.parseInt(Discount), amount, Integer.parseInt(tax),
                    billingadress, Billing_Type);
            Room.setConnnection(c);
            Room.updateRoomAvailbility("available", tempRoomNo);
            CheckIn.setConnnection(c);
            CheckIn.updateCheckOutTime(tempCID);

            // Committing transaction
            c.commit();

            return amount;
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return 0;
        } finally {
            Database.endConnnection(c);
        }

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

}
