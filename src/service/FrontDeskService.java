package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import dao.Billing;
import dao.ContactInfo;
import dao.ContactLinks;
import dao.Customer;
import dao.Database;
import dao.HotelPeopleLinks;
import dao.People;
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
    public static int calculateAmount(String SSN,String Discount,String Billing_Type,String tax,String billingadress)
    {
        int amount =0;
        int temphid=0;
        int tempPID=0;
        int tempCID=0;
        int tempRoomNo =0;
        String temproom_category = "";
        int tempoccupancy=0;
        int tempNightlyRate=0;
        Date tempcheckin;
        Date tempcheckout;
        long duration=0;
        int tempServiceNum=0;
        String tempServiceType="";
        int finalDiscount=Integer.parseInt(Discount);
     
        Connection c = Database.getConnection();
        //getting people id
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select pid from people where ssn=? ");
            exe.setInt(1,Integer.parseInt(SSN));
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempPID=result.getInt("pid");
           
            }
           
            
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select discount from discount where billing_type=? ");
            exe.setString(1,Billing_Type);
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            finalDiscount=result.getInt("discount");
           
            }
           
            
        } catch (Exception e) {
            System.out.println(e);
        }
        // getting checkin id
        
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select * from checkin where pid=? ");
            exe.setInt(1,(tempPID));
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempCID=result.getInt("cid");
            tempcheckin=result.getDate("checkin");
            tempcheckout=result.getDate("checkout");
             duration  = (int)(tempcheckout.getTime() - tempcheckin.getTime())/(1000 * 60 * 60 * 24);
             
             


            }
            System.out.println(tempCID);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //getting room number
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select * from checkin_attributes where cid=?"
                    );
            exe.setInt(1,(tempCID));
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempRoomNo=result.getInt("room_num");
            temphid =result.getInt("hotel_id");
            }
            System.out.println(tempRoomNo);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //getting room category and room occupancy 
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select * from room where room_num=? and hotel_id=?"
                    );
            exe.setInt(1,(tempRoomNo));
            exe.setInt(2,(temphid));
            //System.out.println(temphid);
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            temproom_category=result.getString("room_category");
            tempoccupancy=result.getInt("occupancy");
            }
            System.out.println(temproom_category);
            System.out.println(tempoccupancy);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //Getting nightly rate
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select nightly_rate from room_category where hotel_id =? and room_category=? and occupancy =?"
                   );
            exe.setInt(1,(temphid));
            exe.setString(2,(temproom_category));
            exe.setInt(3,tempoccupancy);
            //System.out.println(temphid);
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempNightlyRate=result.getInt("nightly_rate");
        
            }
            amount =(int) (amount+tempNightlyRate*duration);
            
            System.out.println(tempNightlyRate);
            System.out.println(amount);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //Getting service_num
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select service_num from room_service_links where hotel_id_room=? and room_num=?"
                   );
            exe.setInt(1,(temphid));
            exe.setInt(2,(tempRoomNo));
           
            //System.out.println(temphid);
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempServiceNum=result.getInt("service_num");
        
            }
          
            
            System.out.println(tempServiceNum);
          
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //Getting service_type
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select type from service where hotel_id=? and service_num=?"
                   );
            exe.setInt(1,(temphid));
            exe.setInt(2,(tempServiceNum));
           
            //System.out.println(temphid);
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempServiceType=result.getString("type");
        
            }
          
            
            System.out.println(tempServiceType);
          
            
        } catch (Exception e) {
            System.out.println(e);
        }
        //getting service price
        try {
            PreparedStatement exe = c.prepareStatement(
                    "select price from service_type where type=?"
                   );
            exe.setString(1,(tempServiceType));
            
           List<Integer> tempList = new ArrayList<Integer>();
         
            ResultSet result = exe.executeQuery();
            if(result.next()) {
            tempList.add(result.getInt("price"));
            
        
            }
            for(int i=0;i<tempList.size();i++)
            {
          amount=amount+tempList.get(i);
            }
            amount =amount -amount*(finalDiscount/100);
            amount=amount+amount*Integer.parseInt(tax)/100;
            System.out.println(amount);
            c.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
              
        return amount;
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
