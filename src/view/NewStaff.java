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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NewStaff extends JDialog implements ActionListener {

    JLabel name, ssn, jobTitle, age, department, gender, skill, privilege, type, phone, email;
    JTextField nameT, ssnT, titleT, ageT, skillT, typeT, phoneT, emailT;
    JComboBox<String> departmentB = new JComboBox<String>(new String[] { "Manager", "Catering",
            "Cleaning", "Front Desk" });
    JComboBox<String> privilegeB = new JComboBox<String>(new String[] { "Cleaning", "Catering",
            "Front Desk" });
    JComboBox<String> genderB = new JComboBox<String>(new String[] { "Female", "Male", "Other" });
    JButton save = new JButton("Save");

    public NewStaff(Manager manager) {
        super(manager, "New staff details", true);
        JPanel panel = new JPanel(new GridLayout(11, 2, 0, 3));
        name = new JLabel(" Name (*)");
        ssn = new JLabel(" SSN (*)");
        jobTitle = new JLabel(" Job Title (*)");
        age = new JLabel(" Age");
        department = new JLabel(" Department");
        gender = new JLabel(" Gender");
        skill = new JLabel(" Skill");
        privilege = new JLabel(" Privilege");
        type = new JLabel(" speciality");
        phone = new JLabel(" Phone");
        email = new JLabel(" Email");

        nameT = new JTextField();
        ssnT = new JTextField();
        titleT = new JTextField();
        ageT = new JTextField();
        skillT = new JTextField();
        typeT = new JTextField();
        phoneT = new JTextField();
        emailT = new JTextField();

        panel.add(name);
        panel.add(nameT);

        panel.add(ssn);
        panel.add(ssnT);

        panel.add(jobTitle);
        panel.add(titleT);

        panel.add(age);
        panel.add(ageT);

        panel.add(department);
        panel.add(departmentB);
        departmentB.addActionListener(this);

        panel.add(privilege);
        panel.add(privilegeB);

        panel.add(gender);
        panel.add(genderB);
        genderB.setEnabled(false);
        gender.setEnabled(false);

        panel.add(skill);
        panel.add(skillT);
        skillT.setEnabled(false);
        skill.setEnabled(false);

        panel.add(type);
        panel.add(typeT);
        typeT.setEnabled(false);
        type.setEnabled(false);

        panel.add(phone);
        panel.add(phoneT);

        panel.add(email);
        panel.add(emailT);

        add(panel, BorderLayout.CENTER);

        save.setBackground(Color.GREEN);
        save.addActionListener(this);
        add(save, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(save);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width / 4, dim.height / 3);
        setLocation(100, 100);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == departmentB) {
            if ("Manager".equals(departmentB.getSelectedItem())) {
                privilegeB.setEnabled(true);
                genderB.setEnabled(false);
                skillT.setEnabled(false);
                typeT.setEnabled(false);
                privilege.setEnabled(true);
                gender.setEnabled(false);
                skill.setEnabled(false);
                type.setEnabled(false);

            } else if ("Catering".equals(departmentB.getSelectedItem())) {
                privilegeB.setEnabled(false);
                genderB.setEnabled(false);
                skillT.setEnabled(true);
                typeT.setEnabled(false);
                privilege.setEnabled(false);
                gender.setEnabled(false);
                skill.setEnabled(true);
                type.setEnabled(false);

            } else if ("Cleaning".equals(departmentB.getSelectedItem())) {
                privilegeB.setEnabled(false);
                genderB.setEnabled(false);
                skillT.setEnabled(false);
                typeT.setEnabled(true);
                privilege.setEnabled(false);
                gender.setEnabled(false);
                skill.setEnabled(false);
                type.setEnabled(true);
            } else {
                privilegeB.setEnabled(false);
                genderB.setEnabled(true);
                skillT.setEnabled(false);
                typeT.setEnabled(false);
                privilege.setEnabled(false);
                gender.setEnabled(true);
                skill.setEnabled(false);
                type.setEnabled(false);
            }
        }
    }

}
