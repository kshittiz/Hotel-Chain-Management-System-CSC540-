package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.Vector;

import org.json.JSONObject;

public class Customer extends People {
    private static Connection c = null;
    private static String[] customers = { "ID(*)", "Name", "SSN", "Address", "Date of Birth" };
    public static Vector<String> COLUMNS = new Vector<String>(Arrays.asList(customers));

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) throws Exception {
        PreparedStatement exe = c.prepareStatement(
                "insert into customer(pid,date_of_birth) values(?, ?)");
        exe.setInt(1, obj1.getInt("pid"));
        exe.setString(2, obj1.getString("date_of_birth"));
        exe.executeQuery();

        return obj1.getInt("pid");
    }

    public boolean deletePerson(int pid) {

        try {
            PreparedStatement exe = c.prepareStatement(" Delete from customer where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updatePerson(String date_of_birth, int pid) {

        try {

            PreparedStatement exe = c.prepareStatement(
                    "update customer set date_of_birth=? where pid =?");

            exe.setString(1, date_of_birth);

            exe.setInt(2, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public Vector<Vector<Object>> getCustomerDetails() {
        Vector<Vector<Object>> data = null;
        try {

            PreparedStatement exe = c.prepareStatement(
                    "Select pid,name,ssn,address,date_of_birth from people natural join customer");
            ResultSet result = exe.executeQuery();
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Data of the table
            data = new Vector<Vector<Object>>();
            while (result.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(result.getObject(i));
                }
                data.add(vector);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }
}