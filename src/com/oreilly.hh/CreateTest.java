package com.oreilly.hh;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.oreilly.hh.data.*;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

/*
* create sample data,letting hibernate persist it for us
*/
public class CreateTest
{
	
	public static Artist getArtist(String name,boolean create,Session session)
	{
		Query query = session.getNamedQuery("com.oreilly.hh.artistByName");
		query.setString("name", name);
		Artist found = (Artist)query.uniqueResult();
		if(found == null && create)
		{
			found = new Artist(name,new HashSet<Track>(),null);
			session.save(found);
		}
		if(found != null && found.getActualArtist() != null)
		{
			return found.getActualArtist();
		}
		return found;
	}
	
	private static void addTrackArtist(Track track,Artist artist)
	{
		track.getArtists().add(artist);
	}
	
	public static void main(String args[])throws Exception
	{
		//create a configuration based on the xml file we have put 
		//in the standard place
		Configuration config = new Configuration();
		config.configure();
		
		//get the session factory we can use for persistence
		SessionFactory sessionFactory = config.buildSessionFactory();
		
		//ask for a session using the jdbc information we 've configured
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try
		{
			//create some data and persist it
			tx = session.beginTransaction();
			StereoVolume fullVolume = new StereoVolume();
			
			Track track = new Track("Russian Trace",
					                "vol2/album610/track02.mp3",
					                Time.valueOf("00:03:30"),new HashSet<Artist>(),new Date(),
					                fullVolume,SourceMedia.BROADCAST,new HashSet<String>());
			addTrackArtist(track,getArtist("PPK",true,session));
			session.save(track);
			
			track = new Track("video killed the radio star",
					          "vol2/album175/track03.mp3",
					          Time.valueOf("00:06:06"),new HashSet<Artist>(),new Date(),
					          fullVolume,SourceMedia.CD,new HashSet<String>());
			addTrackArtist(track,getArtist("the buggles",true,session));
			session.save(track);
			
			track = new Track("gravity's angel",
					          "vol2/album175/track03.mp3",
					          Time.valueOf("00:06:06"),new HashSet<Artist>(),new Date(),
					          fullVolume,SourceMedia.VHS,new HashSet<String>());
			addTrackArtist(track,getArtist("laurie anderson",true,session));
			session.save(track);
			
			
			
			track = new Track("adagio for Strings(ferry corsten remox)",
			          "vol2/album972/track01.mp3",
			          Time.valueOf("00:06:35"),new HashSet<Artist>(),new Date(),
			          fullVolume,SourceMedia.VHS,new HashSet<String>());
	        addTrackArtist(track,getArtist("william oprbit",true,session));
	        addTrackArtist(track,getArtist("ferry corsten",true,session));
	        addTrackArtist(track,getArtist("samuel barber",true,session));	        
	        session.save(track);
	        
	        track = new Track("adagio for Strings(atb remix)",
			          "vol2/album972/track02.mp3",
			          Time.valueOf("00:07:39"),new HashSet<Artist>(),new Date(),
			          fullVolume,SourceMedia.VHS,new HashSet<String>());
	        addTrackArtist(track,getArtist("william oprbit",true,session));
	        addTrackArtist(track,getArtist("ferry corsten",true,session));
	        addTrackArtist(track,getArtist("samuel barber",true,session));	        
	        session.save(track);
	        
	        track = new Track("the world's 99",
			          "vol2/singles/pvw99.mp3",
			          Time.valueOf("00:07:05"),new HashSet<Artist>(),new Date(),
			          fullVolume,SourceMedia.VHS,new HashSet<String>());
	        addTrackArtist(track,getArtist("pulp victim",true,session));
	        addTrackArtist(track,getArtist("ferry corsten",true,session));	        
	        session.save(track);
	        
	        track = new Track("test tone 1",
			          "vol2/singles/test01.mp3",
			          Time.valueOf("00:00:10"),new HashSet<Artist>(),new Date(),
			          new StereoVolume((short)50,(short)70),SourceMedia.VHS,new HashSet<String>());	        
	        session.save(track);
	
			tx.commit();
		}catch(Exception e)
		{
			if(tx != null)
			{
				//something went wrong;discard all partial changes
				tx.rollback();
			}
			throw new Exception("Transaction failed",e);
		}finally
		{
			//no matter what,close the session
			session.close();
		}
		
		//clean up after ourselves
		sessionFactory.close();
	}
}
