package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import service.FrontDeskService;

public class FrontDesk extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JTabbedPane tabbedPane;
    JPanel register, checkin, checkout, billing, regpanel,billingpanel, end,end1;
    JLabel ssnL;
    JTextField ssnT;
    JLabel roomL;
    JTextField roomT;
    JLabel extraDiscountLabel;
    JTextField extraDiscountText;
    JLabel taxLabel;
    JTextField taxText;
    JLabel billingAdressLabel ;
    JTextField billingAdressText ;
    JLabel billingTypeLabel;
    JTextField billingTypeText;
    
    
    JButton check, addPerson,checkOut;
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
        
        
        
        
        //UI for billing
        
        
        
        // UI for check-out
        billingpanel = new JPanel(new GridLayout(12, 2, 0, 3));
        roomL = new JLabel("Enter the room number to be checked out");
        roomT = new JTextField();

        billingpanel.add(roomL);
        billingpanel.add(roomT);
        
        extraDiscountLabel=new JLabel("Enter extra discount");
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
        billingTypeText = new JTextField();
        
        billingpanel.add(billingTypeLabel);
        billingpanel.add(billingTypeText);
     
        checkout.add(billingpanel);
        end1 = new JPanel(new GridLayout(2, 1));

        checkOut = new JButton("Check Out and Bil the Customer");
        end1.add(checkOut);
        checkOut.addActionListener(this);
        checkout.add(new JScrollPane(end1), BorderLayout.SOUTH);

        
        
        
        
        
        
        

        // UI for check-in

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
        if(action.getSource()==checkOut) {
           
            
           int amount=0;
        try {
            amount = FrontDeskService.calculateAmount(roomT.getText(),extraDiscountText.getText(),billingTypeText.getText(),taxText.getText(),billingAdressText.getText());
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
           new MyDialog("The customer has successfully checked out,The total amount is" + Integer.toString(amount) );
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
        setSize(2500, 1000);
        // setLocation(login.getLocationOnScreen());
        setVisible(true);
    }
}