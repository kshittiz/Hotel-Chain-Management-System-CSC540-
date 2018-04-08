package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


import org.json.JSONObject;

public class Manager extends Staff {
    public  int addPerson(JSONObject obj1) {
        Connection c = Database.getConnection();
        try {
            
            PreparedStatement exe = c.prepareStatement("insert into manager(pid, privilege) values(?,?)");
            exe.setInt(1, obj1.getInt("pid"));
            exe.setString(2, obj1.getString("privilege"));
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
                    " Delete from manager where pid=?");
            exe.setInt(1, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void updatePerson(JSONObject obj2, int pid) {
        Connection c = Database.getConnection();
        try {

            PreparedStatement exe = c.prepareStatement(
                    "update manager set privilege=? where pid =?");

            exe.setString(1, obj2.getString("privilege"));
           
           
            

            exe.setInt(2, pid);

            exe.executeQuery();

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
/*
    public static void main(String[] args)
      {
          Manager m = new Manager();
          JSONObject o = new JSONObject();
          
          o.put("privilege","full_access");
          
         
          
          
          m.updatePerson(o,2);
      }*/
}
