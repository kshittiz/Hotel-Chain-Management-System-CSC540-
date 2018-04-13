package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;


import dao.ContactInfo;
import dao.ContactLinks;
import dao.Customer;
import dao.Database;
import dao.HotelPeopleLinks;
import dao.People;

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
    public static boolean checkIfPersonPresent(String ssn)
    {
    	int count=0;
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
    	if(count==0)
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
