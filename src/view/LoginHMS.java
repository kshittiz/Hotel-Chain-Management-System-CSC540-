package view;

import java.awt.*;
import javax.swing.*;

public class LoginHMS {

	
	public LoginHMS()
	{
		//create frame
		JFrame frame=new JFrame("Wolf-Inn");
		frame.setSize(350, 220);
		
		//create panel
		JPanel panel=new JPanel();
		panel.setLayout(null);
		
		//ssn label
		JLabel ssnLabel=new JLabel("SSN");
		ssnLabel.setBounds(10,20,80,25);
		panel.add(ssnLabel);
		
		//ssn text field
		JTextField ssnText=new JTextField();
		ssnText.setBounds(100,20,200,25);
		panel.add(ssnText);
		
		//view label
		JLabel viewLabel = new JLabel("View");
	    viewLabel.setBounds(10,50,80,25);
	    panel.add(viewLabel);
	    
	    //view combo box
	    String[] privileges= {"Select View","Manager","Front Desk Representative"};
		JComboBox viewList = new JComboBox(privileges);
		viewList.setSelectedIndex(0);
	    viewList.setBounds(100,50,200,25);
	    panel.add(viewList);
	    
	    //add login button to panel
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(130, 100, 80, 25);
		panel.add(loginButton);
		
		//set location of frame such that it always appears in middle of screen 
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		    
	    //add panel to frame
		frame.add(panel);
		frame.setVisible(true);


	}
	public static void main(String[] args)
	{
	    LoginHMS login=new LoginHMS();
	}
	
}