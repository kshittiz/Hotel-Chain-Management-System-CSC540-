package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import service.FrontDeskService;
import service.ManagerService;

public class LoginHMS extends JFrame implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    JTextField ssnText;
    JComboBox<String> viewList;
    String user;

    public LoginHMS() {
        // create frame
        super("Wolf-Inn");
        setSize(350, 220);

        // create panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        // ssn label
        JLabel ssnLabel = new JLabel("Enter SSN");
        panel.add(ssnLabel);

        // ssn text field
        ssnText = new JTextField();
        panel.add(ssnText);

        // view label
        JLabel viewLabel = new JLabel("Select duty");
        panel.add(viewLabel);

        // view combo box
        String[] privileges = { "Front Desk Representative", "Manager" };
        viewList = new JComboBox<String>(privileges);
        viewList.setSelectedIndex(0);
        panel.add(viewList);

        // add login button to panel
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(this);
        // set location of frame such that it always appears in middle of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        // add panel to frame
        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(loginButton);
        setSize(400, 150);
        setVisible(true);

    }

    public static void main(String[] args) {
        new LoginHMS();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ssn = ssnText.getText();
        String duty = (String) viewList.getSelectedItem();
        if (duty.equals("Front Desk Representative")) {
            user = FrontDeskService.getNameLinkedwithSSN(ssn);
            new FrontDesk(user);
        } else {
            user = ManagerService.getNameLinkedwithSSN(ssn);
        }

        if (user == null)
            new Error(this);
        else
            System.out.println(user);

    }

}

@SuppressWarnings("serial")
class Error extends JDialog {
    JLabel error = new JLabel("Sorry! SSN not registered for this duty");

    Error(LoginHMS login) {
        super(login, "Error", true);
        error.setForeground(Color.RED);
        add(error, BorderLayout.CENTER);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(250, 100);
        setLocation(login.getLocationOnScreen());
        setVisible(true);
    }
}