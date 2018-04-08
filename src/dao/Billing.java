package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {
    int addBilling(int cid,int amount,int extra_discount,int tax,String billing_address,String billing_type) {
       int  invoice_id =0;
       Connection c = Database.getConnection();
       try {
           PreparedStatement exe = c.prepareStatement(
                   "insert into billing(cid,amount,extra_discount,tax,billing_address,billing_type) values(?,?,?,?,?,?)",
                   Statement.RETURN_GENERATED_KEYS);
           exe.setInt(1, cid);
           exe.setInt(2, amount);
           exe.setInt(3,extra_discount);
           exe.setInt(4,tax);
           exe.setString(5,billing_address);
           exe.setString(6,billing_type);
           

           exe.executeQuery();
           ResultSet result = exe.getGeneratedKeys();
           if(result.next())
               invoice_id=result.getInt(1);

           c.close();
       } catch (Exception e) {
           System.out.println(e);
       }

       return invoice_id;
    }
    public static void main(String[] args)
    {
        Billing b = new Billing();
        int x = b.addBilling(1, 100, 5, 5, "kshitiz ka ghar", "Credit card");
    }

}
/*CREATE TABLE billing(
        invoice_id int PRIMARY KEY AUTO_INCREMENT,
        cid int NOT NULL,
        amount bigint NOT NULL,
        extra_discount int DEFAULT 0,
        tax int DEFAULT 5 CHECK(tax>=1 and tax<=50),
        billing_address varchar(150) NOT NULL,
        billing_type varchar(30),
        CONSTRAINT FK_billing FOREIGN KEY (cid) REFERENCES checkin(cid),
        CONSTRAINT FK_billing_discount FOREIGN KEY (billing_type) REFERENCES discount(billing_type)
     );*/