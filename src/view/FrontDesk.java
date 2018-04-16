package view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import dao.Database;
import dao.Discount;
import dao.People;
import dao.Service;
import service.FrontDeskService;

public class FrontDesk extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JTabbedPane tabbedPane;
    JPanel register, checkin, checkinP, services, checkout, billing, regpanel, billingpanel, end,
            end1;
    JLabel ssnL;
    JTextField ssnT;
    JLabel roomL;
    JTextField roomT;
    JLabel extraDiscountLabel;
    JTextField extraDiscountText;
    JLabel taxLabel;
    JTextField taxText;
    JLabel billingAdressLabel;
    JTextField billingAdressText;
    JLabel billingTypeLabel;
    JTextField billingTypeText;
    JComboBox<String> payment;
    JButton check, addPerson, checkOut, checkB, add_service;
    NewCheckIn newcheckin;
    // static JLabel opLabel;

    public FrontDesk(String name) {
        super("Front Desk View - " + name);
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.DARK_GRAY);
        tabbedPane.setForeground(Color.WHITE);

        register = new JPanel(new BorderLayout());

        ImageIcon registerIcon = new ImageIcon(new ImageIcon("images/add.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        tabbedPane.addTab("Register Customer", registerIcon, register);

        checkin = new JPanel(new BorderLayout());

        ImageIcon checkinIcon = new ImageIcon(new ImageIcon("images/checkin.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        tabbedPane.addTab("Check-In Customer", checkinIcon, checkin);

        checkout = new JPanel(new BorderLayout());
        ImageIcon checkoutIcon = new ImageIcon(new ImageIcon("images/checkout.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        tabbedPane.addTab("Check-Out Customer", checkoutIcon, checkout);

        services = new JPanel(new BorderLayout());
        ImageIcon servicesIcon = new ImageIcon(new ImageIcon("images/billing.png").getImage()
                .getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        tabbedPane.addTab("Request Services", servicesIcon, services);

        // add(tabbedPane);
        add(tabbedPane, BorderLayout.CENTER);

        // UI for register
        regpanel = new JPanel(new GridLayout(12, 2, 0, 3));
        ssnL = new JLabel("SSN(*)");
        ssnT = new JTextField();

        regpanel.add(ssnL);
        regpanel.add(ssnT);

        register.add(regpanel);
        // register.add(new JScrollPane(regpanel), BorderLayout.CENTER);

        end = new JPanel(new GridLayout(2, 1));

        check = new JButton("Check if Person Detail's Present");
        end.add(check);
        check.addActionListener(this);

        addPerson = new JButton("Add this Person");
        end.add(addPerson);
        addPerson.addActionListener(this);

        // register.add(end, BorderLayout.SOUTH);
        register.add(new JScrollPane(end), BorderLayout.SOUTH);

        // UI for billing

        // UI for check-out
        billingpanel = new JPanel(new GridLayout(12, 2, 0, 3));
        roomL = new JLabel("Enter the room number to be checked out");
        roomT = new JTextField();

        billingpanel.add(roomL);
        billingpanel.add(roomT);

        extraDiscountLabel = new JLabel("Enter extra discount");
        extraDiscountText = new JTextField();

        billingpanel.add(extraDiscountLabel);
        billingpanel.add(extraDiscountText);

        taxLabel = new JLabel("Enter tax to be levied");
        taxText = new JTextField();

        billingpanel.add(taxLabel);
        billingpanel.add(taxText);

        billingAdressLabel = new JLabel("Enter the adress");
        billingAdressText = new JTextField();

        billingpanel.add(billingAdressLabel);
        billingpanel.add(billingAdressText);

        billingTypeLabel = new JLabel("Enter the billing type");
        
        List<String> paymentTypesList = FrontDeskService.getListOfPayment() ;//getting all billing types from database
        String []paymentTypes = new String[paymentTypesList.size()]; //converting the list obtained to array format
        paymentTypesList.toArray(paymentTypes);
        payment = new JComboBox<String>(paymentTypes); // creating a dropdown
                                                       // menu for billing types
        payment.setSelectedIndex(0);

        billingpanel.add(billingTypeLabel);
        billingpanel.add(payment);

        checkout.add(billingpanel);

        end1 = new JPanel(new GridLayout(2, 1));

        checkOut = new JButton("Check Out and Bil the Customer");
        end1.add(checkOut);
        checkOut.addActionListener(this);
        checkout.add(new JScrollPane(end1), BorderLayout.SOUTH);

        // UI for check-in
        newcheckin = new NewCheckIn(this);
        checkin.add(newcheckin.createcheckin());
        checkinP = new JPanel(new GridLayout(1, 1));
        checkB = new JButton("Check-In");
        checkinP.add(checkB);
        checkB.addActionListener(this);
        checkin.add(new JScrollPane(checkinP), BorderLayout.SOUTH);
        // checkin.setSize(50,100);

        // UI for services
        RequestService reqservice = new RequestService(this);
        services.add(reqservice.getview());

        // set this view to full screen size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width / 2, dim.height / 2);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent action) {
        // TODO Auto-generated method stub

        if (action.getSource() == check) {
            boolean check = FrontDeskService.checkIfPersonPresent(ssnT.getText());
            if (check == false) {
                new MyDialog("Sorry! No Data present for this Person");
            } else {
                new MyDialog("Data present for this Person!");
            }
        }

        if (action.getSource() == addPerson) {
            new NewCustomer(this, ssnT.getText());
        }

        if (action.getSource() == add_service) {
            // System.out.println("yo");
            // RequestService r=new RequestService(this);
            int my_room = Integer.parseInt(RequestService.room_numC.getSelectedItem().toString());
            String my_service = RequestService.typeC.getSelectedItem().toString();

            Connection c = Database.getConnection();
            // RoomServiceLinks.setConnnection(c);
            Service.setConnnection(c);
            Service s = new Service();
            int room_service = s.getservicenum(my_service, LoginHMS.hid);
            // System.out.println("num"+room_service);
            JSONObject input = new JSONObject();
            input.put("room_num", my_room);
            input.put("service_type", my_service);
            input.put("service_num", room_service);
            // input.put("staff_id", LoginHMS.pid);
            int mypid = 10;
            input.put("hotel_id", LoginHMS.hid);

            if (my_service.contains("special")) {
                mypid = s.getStaffServing(LoginHMS.hid, "manager");
            } else if (my_service.contains("room")) {
                mypid = s.getStaffServing(LoginHMS.hid, "cleaning");

            } else if (my_service.contains("dry")) {
                mypid = s.getStaffServing(LoginHMS.hid, "cleaning");

            } else if (my_service.contains("catering")) {
                mypid = s.getStaffServing(LoginHMS.hid, "catering");

            } else
                mypid = LoginHMS.pid;

            input.put("staff_id", mypid);

            if (!FrontDeskService.requestNewService(input)) {
                new MyDialog("Service Requested was not successful");
            } else {
                new MyDialog2("Service Request completed successfully!");

            }

            // System.out.println(""+my_room+""+my_service);

        }
        if (action.getSource() == checkB) {
            int numguests = Integer.parseInt(newcheckin.guestT.getSelectedItem().toString());
            String category = newcheckin.categoryT.getSelectedItem().toString();

            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Date date1 = null;

            String jDateChooser1 = myFormat.format(cal.getTime());
            try {
                date1 = myFormat.parse(jDateChooser1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*
             * SimpleDateFormat myFormat = new SimpleDateFormat( "yyyy-mm-dd HH:mm:ss");
             * Calendar cal = Calendar.getInstance();
             * 
             * Timestamp date1=null; try { date1 = (Timestamp)
             * myFormat.parse(cal.getTime().toString());
             * 
             * } catch (Exception e) { e.printStackTrace(); }
             */
            System.out.println(date1);

            int room_num = 0;
            Map<Integer, String> map = new LinkedHashMap<>();
            map = FrontDeskService.checkRoomAvailable(LoginHMS.hid, numguests, category);

            if (map != null) {
                JSONObject input = new JSONObject();
                // System.out.println((newcheckin.ssnT.getText()));
                Connection conn = Database.getConnection();

                People.setConnnection(conn);
                String ssn = newcheckin.ssnT.getText();

                int peopleid = People.getPIDbySSN(ssn);

                Database.endConnnection(conn);

                Map.Entry<Integer, String> entry = map.entrySet().iterator().next();
                room_num = entry.getKey();

                if (peopleid == 0)
                    new MyDialog("Invalid SSN");
                else {
                    input.put("pid", peopleid);
                    input.put("guests", numguests);
                    input.put("checkin", date1);
                    input.put("checkout", date1);
                    input.put("room_num", room_num);
                    input.put("category", category);
                    // input.put("front_desk_id",front_desk_id);

                    if (!FrontDeskService.addNewCheckIn(input)) {
                        new MyDialog("Check-In was not successful");
                    } else {
                        new MyDialog2("Check-In successful!");

                    }
                    this.dispose();
                }
            } else
                new MyDialog("Sorry! No Room Available");

        }

        // action to be perfomed when customer checks out
        if (action.getSource() == checkOut) {
            String finalString = "";
            String paymentTypeTemp = (String) payment.getSelectedItem();

            try {
                finalString = FrontDeskService.calculateAmount(roomT.getText(),
                        extraDiscountText.getText(), paymentTypeTemp, taxText.getText(),
                        billingAdressText.getText());
                if (finalString != "") {
                    new MyDialog3("The customer has successfully checked out " + "\n" + finalString);
                } else {
                    new MyDialog3("The room needs to be corrected");
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        }

    }
}

@SuppressWarnings("serial")
class MyDialog extends JDialog {
    JLabel error;

    MyDialog(String text) {
        error = new JLabel(text);
        // super(login, "Error", true);
        error.setForeground(Color.RED);
        add(error, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(250, 100);
        // setLocation(login.getLocationOnScreen());
        setVisible(true);
    }
}

@SuppressWarnings("serial")
class MyDialog2 extends JDialog {
    JLabel error;

    MyDialog2(String text) {
        error = new JLabel(text);
        // super(login, "Error", true);
        error.setForeground(Color.GREEN);
        add(error, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(250, 100);
        // setLocation(login.getLocationOnScreen());
        setVisible(true);
    }
}

@SuppressWarnings("serial")
class MyDialog3 extends JDialog {
    JTextArea result;

    MyDialog3(String text) {
        result = new JTextArea(text);
        // super(login, "Error", true);
        result.setEditable(false);
        result.setForeground(Color.GREEN);
        add(result, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(250, 100);
        // setLocation(login.getLocationOnScreen());
        setVisible(true);
    }
}
