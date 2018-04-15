package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.Database;
import dao.People;
import dao.Room;
import dao.Service;

public class RequestService {

	
	JPanel panel,end;
	JLabel room_num,type;
	static JComboBox room_numC,typeC;
	JButton add;
	ArrayList<String> roomnums=new ArrayList<String>();
	ArrayList<String> roomtypes=new ArrayList<String>();

	
	RequestService(FrontDesk f)
	{
		panel=new JPanel(new GridLayout(12,2,0,3));
		room_num=new JLabel("Room Number");
		type=new JLabel("Service Type");
		
	    Connection conn = Database.getConnection();
	    
	    Room.setConnnection(conn);
	    Service.setConnnection(conn);
	    
	    
	    Room room=new Room();
	    Service service=new Service();
	    
	    Vector<Vector<Object>> data =room.getRoomDetails(LoginHMS.hid);
	    //System.out.println(data);
	    int i;
	    for(i=0;i<data.size();i++)
	    {
	    	
	    	if(data.get(i).get(3).toString().equals("unavailable"))
	    	{
	    		
	    		roomnums.add(data.get(i).get(0).toString());
	    	}
	    }
	    room_numC=new JComboBox(roomnums.toArray());
	    
	    Vector<Vector<Object>> data_service=service.getServiceDetails(LoginHMS.hid);
	    int k;
	    for(k=0;k<data_service.size();k++)
	    {
	    	//System.out.println(data_service);	
	    	roomtypes.add(data_service.get(k).get(1).toString());
	    	
	    }

	    typeC=new JComboBox(roomtypes.toArray());
	    
	    
	    
	    
	    
	    
	    
	    panel.add(room_num);
	    panel.add(room_numC);
	    
	    panel.add(type);
	    panel.add(typeC);

        Database.endConnnection(conn);


	}
   
	public JPanel getview()
	{
		return panel;
	}
}
