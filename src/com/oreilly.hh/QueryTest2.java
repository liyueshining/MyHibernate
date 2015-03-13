package com.oreilly.hh;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.oreilly.hh.data.*;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 *provider a user interface to enter artist names and see their tracks. 
 */
public class QueryTest2 extends JPanel{

	JList list; //will contain tracks associated with current artist
	DefaultListModel model; //lets us manipulate the list contents
	
	/*
	 * build the panel containing ui elements
	 */
	public QueryTest2()
	{
		setLayout(new BorderLayout());
		model = new DefaultListModel();
		list = new JList(model);
		
		add(new JScrollPane(list),BorderLayout.SOUTH);
		
		final JTextField artistField = new JTextField(28);
		artistField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						updateTracks(artistField.getText());
					}
				});
			}
		});
		add(artistField,BorderLayout.EAST);
		add(new JLabel("Artist:"),BorderLayout.WEST);
	}
	
	/*
	 * update the list to cintain the tracks associated with an artist
	 */
	private void updateTracks(String name)
	{
		model.removeAllElements();//clear out previous tracks
		if(name.length() < 1) return;//nothing to do
		try{
			//ask for a session using the jdbc information we have configured
			Session session = sessionFactory.openSession();
			try{
				Artist artist= CreateTest.getArtist(name, false, session);
				if(artist == null)
				{
					model.addElement("artist not found");
					return;
				}
				//list the tracks associated with the artist
				for(Track atrack : artist.getTracks())
				{
					model.addElement("Track:\""+atrack.getTitle()+
							"\"," + atrack.getPlayTime());
				}
			}finally
			{
				//no matter what,close the session
				session.close();
			}
		}catch(Exception e)
		{
			System.err.println("problem updating tracks:"+ e);
			e.printStackTrace();
		}		
	}
	
	private static SessionFactory sessionFactory;//used to talk to hibernate
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load configuration properties,read mapping for persistent classs
		Configuration config = new Configuration();
		config.configure();
		
		//get the session factory we can use for persistence
		sessionFactory = config.buildSessionFactory();
		
		//set up the ui
		JFrame frame = new JFrame("Artist track lookup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new QueryTest2());
		frame.setSize(400, 180);
		frame.setVisible(true);
        
	}

}
