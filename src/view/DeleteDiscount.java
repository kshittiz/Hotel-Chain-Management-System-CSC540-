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
import service.ManagerService;

@SuppressWarnings("serial")
public class DeleteDiscount extends JDialog implements ActionListener {
    JTable table;
    JButton delete = new JButton("delete");
    DefaultTableModel tableModel = new DefaultTableModel();

    public DeleteDiscount(Manager manager) {
        super(manager, "Delete Discount", true);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel(
                "All types of discounts offered in hotel chain, Select type and click delete!");
        table = new JTable(tableModel);
        tableModel.setDataVector(ManagerService.getDiscountDetails(), Discount.COLUMNS);
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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width / 3, dim.height / 3);
        setLocation(manager.getLocation());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
