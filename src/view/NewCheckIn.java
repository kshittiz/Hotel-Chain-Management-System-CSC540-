package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class NewCheckIn {
	
	JLabel ssnL,guestsL,checkinL,categoryL;
	JTextField ssnT,checkinT;
	JComboBox guestT,categoryT;
	JButton check;
	JPanel end;
	String[] num= {"1","2","3","4"};
	String[] category= {"Deluxe","Economy","Executive suite","Presidential Suite"};
	JPanel panel=new JPanel(new GridLayout(12,2));
	Date date1;
	NewCheckIn(FrontDesk f)
	{
		ssnL=new JLabel("SSN");
		guestsL=new JLabel("Number of Guests");
		checkinL=new JLabel("Check In Date");
		categoryL=new JLabel("Category");
		
		
		ssnT=new JTextField();
		guestT=new JComboBox<>(num);
		categoryT=new JComboBox<>(category);
		panel.add(ssnL);		
		panel.add(ssnT);
		
		panel.add(guestsL);
		panel.add(guestT);
		
		panel.add(categoryL);
		panel.add(categoryT);

		/*SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		String jDateChooser1=myFormat.format(cal.getTime());
		try {
			date1 = myFormat.parse(jDateChooser1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		checkinT=new JTextField(date1.toString());
		
		panel.add(checkinL);
		panel.add(checkinT);*/
		
        

		
		


	
	}
	public void actionPerformed(ActionEvent action) {
		//System.out.println("yo");
		
	}
	public JPanel createcheckin()
	{
		
		return panel;
	}

}
