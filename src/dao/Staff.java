package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

import org.json.JSONObject;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

public class Staff extends People {
    private static Connection c = null;
    private static String[] staff = { "ID(*)", "Name", "SSN", "Address", "Job Title", "Age",
            "Department" };
    public static Vector<String> STAFF_COLUMNS = new Vector<String>(Arrays.asList(staff));

    private static String[] managerStaff = { "ID(*)", "Name", "SSN", "Address", "Job Title", "Age",
            "Department", "Privilege" };
    public static Vector<String> MANAGER_STAFF_COLUMNS = new Vector<String>(Arrays.asList(
            managerStaff));

    private static String[] cateringStaff = { "ID(*)", "Name", "SSN", "Address", "Job Title", "Age",
            "Department", "Skill" };
    public static Vector<String> CATERING_STAFF_COLUMNS = new Vector<String>(Arrays.asList(
            cateringStaff));

    private static String[] cleaningStaff = { "ID(*)", "Name", "SSN", "Address", "Job Title", "Age",
            "Department", "Speciality" };

    public static Vector<String> CLEANING_STAFF_COLUMNS = new Vector<String>(Arrays.asList(
            cleaningStaff));

    private static String[] frontDeskStaff = { "ID(*)", "Name", "SSN", "Address", "Job Title",
            "Age", "Department", "Gender" };
    public static Vector<String> FRONT_DESK_Staff_COLUMNS = new Vector<String>(Arrays.asList(
            frontDeskStaff));

    public static void setConnnection(Connection conn) {
        c = conn;
    }

    public int addPerson(JSONObject obj1) throws Exception {
        PreparedStatement exe = c.prepareStatement(
                "insert into staff(pid, job_title, hotel_serving, age, department) values(?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        exe.setInt(1, obj1.getInt("pid"));
        exe.setString(2, obj1.getString("job_title"));
        exe.setInt(3, obj1.getInt("hotel_serving"));
        Integer age = (obj1.optInt("age") == 0) ? null : obj1.optInt("age");
        if (age != null)
            exe.setInt(4, age);
        else
            exe.setNull(4, Type.INT);
        exe.setString(5, obj1.getString("department"));
        exe.executeQuery();

        return obj1.getInt("pid");
    }

    public boolean deletePerson(int pid) {

        try {
            PreparedStatement exe = c.prepareStatement(" Delete from staff where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean updatePerson(JSONObject obj2, int pid) {

        try {

            PreparedStatement exe = c.prepareStatement(
                    "update staff set job_title=?,hotel_serving=?,age=?,department=? where pid =?");

            exe.setString(1, obj2.getString("job_title"));
            exe.setInt(2, obj2.getInt("hotel_serving"));
            exe.setInt(3, obj2.getInt("age"));
            exe.setString(4, obj2.getString("department"));

            exe.setInt(5, pid);

            exe.executeQuery();

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public Vector<Vector<Object>> getStaffDetails(String staffType) {
        Vector<Vector<Object>> data = null;
        try {
            PreparedStatement exe = null;
            if ("manager".equals(staffType))
                exe = c.prepareStatement(
                        "Select pid,name,ssn, address,job_title,age,department,privilege from people natural join staff natural join manager");
            else if ("catering".equals(staffType))
                exe = c.prepareStatement(
                        "Select pid,name,ssn, address,job_title,age,department,skill from people natural join staff natural join catering_staff");
            else if ("cleaning".equals(staffType))
                exe = c.prepareStatement(
                        "Select pid,name,ssn, address,job_title,age,department,speciality from people natural join staff natural join cleaning_staff");
            else if ("front_desk".equals(staffType))
                exe = c.prepareStatement(
                        "Select pid,name,ssn, address,job_title,age,department,gender from people natural join staff natural join front_desk");
            else
                exe = c.prepareStatement(
                        "Select pid,name,ssn, address,job_title,age,department from people natural join staff");

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
