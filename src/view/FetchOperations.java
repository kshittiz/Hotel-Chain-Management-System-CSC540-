package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.Customer;
import dao.Discount;
import dao.Hotel;
import dao.Room;
import dao.RoomCategory;
import dao.Service;
import dao.ServiceType;
import dao.Staff;
import service.ManagerService;

@SuppressWarnings("serial")
public class FetchOperations extends JDialog implements ActionListener {
    JTable table;
    DefaultTableModel tableModel = new DefaultTableModel();
    JComboBox<String> extra;
    JComboBox<String> occup;
    JCheckBox role;
    String city = "";

    public FetchOperations(Manager manager, String title, String msg) {
        super(manager, title, true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel(msg);
        table = new JTable(tableModel);
        switch (title) {
        case "See your hotel details":
            tableModel.setDataVector(ManagerService.getHotelDetails(LoginHMS.hid), Hotel.COLUMNS);
            setSize(dim.width / 2, dim.height / 5);
            break;
        case "See all hotels in chain":
            tableModel.setDataVector(ManagerService.getHotelDetails(0), Hotel.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);
            break;
        case "See your staff details":
            extra = new JComboBox<String>(new String[] { "complete staff", "manager", "catering",
                    "cleaning", "front_desk" });
            panel.add(extra, BorderLayout.SOUTH);
            role = new JCheckBox("Group by Department/Role ");
            panel.add(role, BorderLayout.EAST);
            extra.addActionListener(this);
            tableModel.setDataVector(ManagerService.getStaffDetails(null), Staff.STAFF_COLUMNS);
            setSize((dim.width + 500) / 2, dim.height / 3);
            break;
        case "See all customers in your hotel":
            tableModel.setDataVector(ManagerService.getCustomerDetails(), Customer.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);
            break;

        case "See all room categories in your hotel":
            tableModel.setDataVector(ManagerService.getRoomCategoryDetails(), RoomCategory.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);
            break;
        case "See all rooms in your hotel":
            tableModel.setDataVector(ManagerService.getRoomDetails(), Room.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);
            break;
        case "See all types of services in hotel chain":
            tableModel.setDataVector(ManagerService.getServiceTypeDetails(), ServiceType.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);
            break;
        case "See all services offered by your hotel":
            tableModel.setDataVector(ManagerService.getServiceDetails(), Service.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);
            break;
        case "See discounts offered":
            tableModel.setDataVector(ManagerService.getDiscountDetails(), Discount.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);
            break;
        case "See occupancy statistics":
            city = "";
            occup = new JComboBox<String>(new String[] { "Occupancy group by all hotels",
                    "Occupancy by room type", "Occupancy by city", "Occupancy by dates",
                    "Total Occupancy", "% of rooms occupied" });
            panel.add(occup, BorderLayout.SOUTH);
            occup.addActionListener(this);
            tableModel.setDataVector(ManagerService.getOccupancyStats(
                    "Occupancy group by all hotels", null), Room.GRP_HOTELS_COLUMNS);
            setSize(dim.width / 3, dim.height / 3);
            break;
        }

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(panel);
        setLocation(manager.getLocation());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == extra) {
            String action = (String) extra.getSelectedItem();

            if (role.isSelected())
                tableModel.setDataVector(ManagerService.getStaffDetailsGroupedByRole(),
                        Staff.COLUMNS);
            else {
                if ("complete staff".equals(action))
                    tableModel.setDataVector(ManagerService.getStaffDetails(null),
                            Staff.STAFF_COLUMNS);
                if ("manager".equals(action))
                    tableModel.setDataVector(ManagerService.getStaffDetails(action),
                            Staff.MANAGER_STAFF_COLUMNS);
                if ("cleaning".equals(action))
                    tableModel.setDataVector(ManagerService.getStaffDetails(action),
                            Staff.CLEANING_STAFF_COLUMNS);
                if ("catering".equals(action))
                    tableModel.setDataVector(ManagerService.getStaffDetails(action),
                            Staff.CLEANING_STAFF_COLUMNS);
                if ("front_desk".equals(action))
                    tableModel.setDataVector(ManagerService.getStaffDetails(action),
                            Staff.FRONT_DESK_Staff_COLUMNS);
            }
        }
        if (e.getSource() == occup) {
            String action = (String) occup.getSelectedItem();
            if ("Occupancy group by all hotels".equals(action))
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, null),
                        Room.GRP_HOTELS_COLUMNS);
            if ("Occupancy by room type".equals(action))
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, null),
                        Room.GRP_RTYPE_COLUMNS);
            if ("Occupancy by city".equals(action)) {
                new Query(this);
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, city),
                        Room.GRP_RCITY_COLUMNS);
            }
            if ("Occupancy by dates".equals(action))
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, null),
                        Room.GRP_RDATES_COLUMNS);
            if ("Total Occupancy".equals(action))
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, null),
                        Room.TOT_OCCUP_COLUMNS);
            if ("% of rooms occupied".equals(action))
                tableModel.setDataVector(ManagerService.getOccupancyStats(action, null),
                        Room.PER_OCCUP_COLUMNS);
        }
    }

}

@SuppressWarnings("serial")
class Query extends JDialog implements ActionListener {
    JTextField city = new JTextField();
    JButton submit = new JButton("submit");
    FetchOperations ops;

    Query(FetchOperations ops) {
        super(ops, "Enter city", true);
        this.ops = ops;
        add(city, BorderLayout.CENTER);
        add(submit, BorderLayout.SOUTH);
        submit.addActionListener(this);
        submit.setBackground(Color.ORANGE);
        setSize(300, 100);
        setLocation(ops.getLocation());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ops.city = city.getText();
        this.dispose();
    }
}
