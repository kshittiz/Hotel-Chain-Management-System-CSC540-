package dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {

    private static String connector;
    private static String userName;
    private static String password;
    private static String driver;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            parseDBdetails();
            Class.forName(driver);
            connection = DriverManager.getConnection(connector, userName, password);
        } catch (Exception e) {
            System.out.println("Connection with database failed!");
        }
        return connection;
    }

    public static void parseDBdetails() {
        Properties props = new Properties();
        try {
            FileReader reader = new FileReader("database.properties");
            // Try loading properties from the file (if found)
            props.load(reader);
            driver = props.getProperty("driver");
            connector = props.getProperty("connector");
            userName = props.getProperty("userName");
            password = props.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
  //This main function can be used to test connectivity with database, will be removed in final version
  /* public static void main(String... s) {
        Connection c = getConnection();
        try {
            Statement exe = c.createStatement();
            ResultSet result = exe.executeQuery("SELECT name from people");
            while (result.next())
                System.out.println(result.getString(1));
            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
  */
}
