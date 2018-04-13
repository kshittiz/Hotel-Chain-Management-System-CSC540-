package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class DeleteOperations extends JDialog implements ActionListener {
    JTable table;
    JButton delete = new JButton("delete");
    DefaultTableModel tableModel = new DefaultTableModel();

    public DeleteOperations(Manager manager, String title, String msg) {
        super(manager, title, true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel(msg);
        table = new JTable(tableModel);
        switch (title) {
        case "Remove staff member":
            tableModel.setDataVector(ManagerService.getStaffDetails(null), Staff.STAFF_COLUMNS);
            setSize((dim.width) / 2, dim.height / 3);
            break;
        case "Remove room category":
            tableModel.setDataVector(ManagerService.getRoomCategoryDetails(), RoomCategory.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);
            break;
        case "Remove room":
            tableModel.setDataVector(ManagerService.getRoomDetails(), Room.COLUMNS);
            setSize(dim.width / 2, dim.height / 3);

            break;
        case "Remove service type":
            tableModel.setDataVector(ManagerService.getServiceTypeDetails(), ServiceType.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);

            break;
        case "Remove Services offered":
            tableModel.setDataVector(ManagerService.getServiceDetails(), Service.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);

            break;
        case "Remove discount":
            tableModel.setDataVector(ManagerService.getDiscountDetails(), Discount.COLUMNS);
            setSize(dim.width / 3, dim.height / 3);

            break;
        }
        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        ImageIcon saveIcon = new ImageIcon(new ImageIcon("images/remove.png").getImage()
                .getScaledInstance(25, 22, Image.SCALE_SMOOTH));
        delete.setIcon(saveIcon);
        delete.setBackground(Color.DARK_GRAY);
        delete.setForeground(Color.RED);
        delete.addActionListener(this);
        panel.add(delete, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(delete);

        add(panel);
        setLocation(manager.getLocation());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
