package view;

import java.awt.*;
import javax.swing.*;

public class FrontDesk {

	public FrontDesk()
	{
		JFrame frame=new JFrame("Front Desk View");
		
		JTabbedPane tabbedPane=new JTabbedPane();
		JPanel checkin=new JPanel();
		tabbedPane.addTab("Check-In Customer", checkin);
		
		JPanel checkout=new JPanel();
		tabbedPane.addTab("Check-Out Customer", checkout);
		
		JPanel billing=new JPanel();

		tabbedPane.addTab("Generate Bills", billing);
		
		frame.add(tabbedPane);

		
		//set this view to full screen size
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(dim.width, dim.height);
		
		frame.setVisible(true);
		
		
	}
	
	public static void main(String args[])
	{
		FrontDesk frontdesk=new FrontDesk();
	}
}
