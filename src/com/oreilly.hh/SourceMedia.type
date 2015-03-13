package com.oreilly.hh;

public enum SourceMedia {

	/**music obtained from magnetic cassette tape. */
	CASSETTE("Audio Cassette Tape"),
	
	VINYL("Vinyl Record"),
	
	VHS("VHS Videocassette type"),
	
	BROADCAST("Analog Broadcast"),
	
	CD("Compact Disc"),
	
	DOWNLOAD("Internet Download"),
	
	STREAM("Digital Audio Stream");
	
	/**
	 * stores the human-readable description of this instance ,
	 * by which it is identified in the user interface.
	 */
	private final String description;
	
	/**
	 * enum constructors are always private since they can only be accessed
	 * through the enumeration mechanism
	 * 
	 * @para description human readable description of the source for the 
	 * audio,by which it is presented in the user interface
	 */
	private SourceMedia(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
}
