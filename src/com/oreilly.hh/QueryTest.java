package com.oreilly.hh;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.oreilly.hh.data.*;

import java.sql.Time;
import java.util.*;

/*
 * retrieve data as objects
 */
public class QueryTest {

	/**
	 * retrieve any tracks that fit in the specified amount of time
	 * 
	 * @param length the maximum playing time for tracks to be returned
	 * @param session the hibernate session that can retrieve data
	 * @return a list of {@link Track}s meeting the length restriction.
	 */
	public static List trackNoLongerThan(Time length,Session session)
	{
		Query query = session.createQuery("from Track as track" + 
	                                      " where track.playTime <=?");
		query.setParameter(0, length);
		return query.list();
	}
	
	public static String listArtistNames(Set<Artist> artists)
	{
		StringBuilder result = new StringBuilder();
		for(Artist artist : artists)
		{
			result.append((result.length()==0)?"(":",");
			result.append(artist.getName());
		}
		if(result.length() >0)
		{
			result.append(")");
			
		}
		return result.toString();
	}
	
	/*
	 * look up and print some tracks when invoked from the command line.
	 */
	public static void main(String[] args) throws Exception{
		// create a configuration based on the properties file we 've put
		//in the standard place.
		Configuration config = new Configuration();
		config.configure();
		
		//get the session factory we can use for persistence
		SessionFactory sessionFactory = config.buildSessionFactory();
		
		//ask for a session using the jdbc information we 've configures
		Session session = sessionFactory.openSession();
		try{
			//print the tracks that will fit in five minutes
			List tracks = trackNoLongerThan(Time.valueOf("00:05:00"),session);
			
			for(ListIterator iter = tracks.listIterator();iter.hasNext();)
			{
				Track atrack = (Track)iter.next();
				String mediainfo ="";
				if(atrack.getSourceMedia() != null)
				{
					mediainfo = ",from " + atrack.getSourceMedia().getDescription();
				}
				
				System.out.println("track:\"" + atrack.getTitle() + 
						            "\"," + listArtistNames(atrack.getArtists())
						            + atrack.getPlayTime()+mediainfo);
			}
		}finally{
			//no matter what,close the session
			session.close();
		}
        //clean up after ourselves
		sessionFactory.close();
	}

}
