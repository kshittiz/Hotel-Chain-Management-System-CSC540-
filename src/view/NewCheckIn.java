package view;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewCheckIn {

    JLabel ssnL, guestsL, checkinL, categoryL;
    public static JTextField ssnT, checkinT;
    JComboBox guestT, categoryT;
    JButton check;
    JPanel end;
    String[] num = { "1", "2", "3", "4" };
    String[] category = { "Deluxe", "Economy", "Executive suite", "Presidential Suite" };
    JPanel panel = new JPanel(new GridLayout(12, 2));
    Date date1;

    NewCheckIn(FrontDesk f) {
        ssnL = new JLabel("SSN");
        guestsL = new JLabel("Number of Guests");
        checkinL = new JLabel("Check In Date");
        categoryL = new JLabel("Category");

        ssnT = new JTextField();
        guestT = new JComboBox<>(num);
        categoryT = new JComboBox<>(category);
        panel.add(ssnL);
        panel.add(ssnT);

        panel.add(guestsL);
        panel.add(guestT);

        panel.add(categoryL);
        panel.add(categoryT);

    }

    public JPanel createcheckin() {
        return panel;
    }

}
