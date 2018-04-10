package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.Discount;
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

    public FetchOperations(Manager manager, String title, String msg) {
        super(manager, title, true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel(msg);
        table = new JTable(tableModel);
        switch (title) {
        case "See your staff details":
            extra = new JComboBox<String>(new String[] { "complete staff", "manager", "catering",
                    "cleaning", "front_desk" });
            panel.add(extra, BorderLayout.SOUTH);
            extra.addActionListener(this);
            tableModel.setDataVector(ManagerService.getStaffDetails(null), Staff.STAFF_COLUMNS);
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
            if ("complete staff".equals(null))
                tableModel.setDataVector(ManagerService.getStaffDetails((String) extra
                        .getSelectedItem()), Staff.MANAGER_STAFF_COLUMNS);
            if ("manager".equals(extra.getSelectedItem()))
                tableModel.setDataVector(ManagerService.getStaffDetails((String) extra
                        .getSelectedItem()), Staff.MANAGER_STAFF_COLUMNS);
            if ("cleaning".equals(extra.getSelectedItem()))
                tableModel.setDataVector(ManagerService.getStaffDetails((String) extra
                        .getSelectedItem()), Staff.CLEANING_STAFF_COLUMNS);
            if ("catering".equals(extra.getSelectedItem()))
                tableModel.setDataVector(ManagerService.getStaffDetails((String) extra
                        .getSelectedItem()), Staff.CLEANING_STAFF_COLUMNS);
            if ("front_desk".equals(extra.getSelectedItem()))
                tableModel.setDataVector(ManagerService.getStaffDetails((String) extra
                        .getSelectedItem()), Staff.FRONT_DESK_Staff_COLUMNS);
        }
    }

}
