package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FrontDesk extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FrontDesk(String name) {
        super("Front Desk View - " + name);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel checkin = new JPanel();
        tabbedPane.addTab("Check-In Customer", checkin);

        JPanel checkout = new JPanel();
        tabbedPane.addTab("Check-Out Customer", checkout);

        JPanel billing = new JPanel();

        tabbedPane.addTab("Generate Bills", billing);

        add(tabbedPane);

        // set this view to full screen size
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width / 2, dim.height / 2);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }
}
