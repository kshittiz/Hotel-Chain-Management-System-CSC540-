package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONObject;

public class Customer extends People {
    private static Connection c = null;

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) {
        try {
            PreparedStatement exe = c.prepareStatement(
                    "insert into customer(pid,date_of_birth) values(?, ?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("date_of_birth"));
            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
        }

        return obj1.getInt("pid");
    }
    public void deletePerson(int pid) {
        Connection c = Database.getConnection();
        try {
            PreparedStatement exe = c.prepareStatement(
                    " Delete from customer where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void updatePerson(String date_of_birth, int pid) {
        Connection c = Database.getConnection();
        try {

            PreparedStatement exe = c.prepareStatement(
                    "update customer set date_of_birth=? where pid =?");

            exe.setString(1, date_of_birth);
            
            

            exe.setInt(2, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    /*public static void main(String[] args)
    {
        Customer c= new Customer();
        c.updatePerson("1/1/1",15);
    }
*/
}
