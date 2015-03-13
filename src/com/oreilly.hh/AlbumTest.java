package com.oreilly.hh;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.oreilly.hh.data.*;

import java.sql.Time;
import java.util.*;

/**
 * 
 * create sample album data,letting  hibernate persist it for us.
 *
 */
public class AlbumTest {

	/**
	 * quick and dirty helper method to handle repetitive portion of creating
	 * album tracks.A real implementation would have much more flexibility.
	 */
	private static void addAlbumTrack(Album album,String title,String file,
			Time length,Artist artist,int disc,int positionOnDisc,Session session)
	{
		Track track = new Track(title,file,length,new HashSet<Artist>(),
				                new Date(),new StereoVolume(),SourceMedia.BROADCAST,new HashSet<String>());
		track.getArtists().add(artist);
		session.save(track);
		album.getTracks().add(new AlbumTrack(track,disc,positionOnDisc));
		
	}
	
	public static void main(String args[])throws Exception
	{
		//create a configuration based on the properties file we have put\
		//in the standard place.
		Configuration config = new Configuration();
		config.configure();
		
		//get the session factory we can use for persistence
		SessionFactory sessionFactory = config.buildSessionFactory();
		
	    //ask for a session using the jdbc information we have configured
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			//create some data and persist it
			tx = session.beginTransaction();
			
			Artist artist = CreateTest.getArtist("Martin L.Gore", true, 
					                             session);
			Album album = new Album("Counterfeit e.p.",1,
					                 new HashSet<Artist>(),new HashSet<String>(),
					                 new ArrayList<AlbumTrack>(5),new Date());
			album.getArtists().add(artist);
			session.save(album);
			
			addAlbumTrack(album,"compulsion","vol1/album83/track01.mp3",
					      Time.valueOf("00:05:29"),artist,1,1,session);
			addAlbumTrack(album,"in a manner of speaking","vol1/album83/track02.mp3",
				      Time.valueOf("00:04:21"),artist,1,2,session);
			addAlbumTrack(album,"smile in the crowd","vol1/album83/track03.mp3",
				      Time.valueOf("00:05:06"),artist,1,3,session);
			addAlbumTrack(album,"gone","vol1/album83/track04.mp3",
				      Time.valueOf("00:03:32"),artist,1,4,session);
			addAlbumTrack(album,"never turn your back on mother earth","vol1/album83/track05.mp3",
				      Time.valueOf("00:03:32"),artist,1,5,session);
			addAlbumTrack(album,"motherless child","vol1/album83/track06.mp3",
				      Time.valueOf("00:03:32"),artist,1,6,session);
			
			System.out.println(album);
			
			//We are done; make our changes permanent
			tx.commit();
			
			//this commented out section is for experimenting with deletions
			//tx = session.beginTransaction();
			//album.getTracks().remove(1);
			//session.update(album);
			//tx.commit();
			
			//tx = session.beginTransaction();
			//session.delete(album);
			//tx.commit();
			
		}catch(Exception e)
		{
			if(tx != null)
			{
				//something went wrong;discard all partial changes
				tx.rollback();
			}
			throw new Exception("transaction failed",e);
		}finally{
			//no matter what,close the session
			session.close();
		}
		
		//clean up after ourselves
		sessionFactory.close();
	}
}
