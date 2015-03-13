package com.oreilly.hh;

import java.io.Serializable;

public class StereoVolume implements Serializable{

	public static final short MINIMUM = 0;
	
	public static final short MAXIMUM = 100;
	
	private short left ;
	
	private short right ;
	
	public StereoVolume()
	{
		this(MAXIMUM,MAXIMUM);
	}
	
	public StereoVolume(short left, short right)
	{
	    setLeft(left);
	    setRight(right);
	}
	
	private void checkVolume(short volume)
	{
		if(volume < MINIMUM)
		{
			throw new IllegalArgumentException("volume cannot be less than"+
		                         MINIMUM);
		}
		if(volume > MAXIMUM)
		{
			throw new IllegalArgumentException("volume cannot be more than"+
					             MAXIMUM);
		}	
	}
	
	public void setLeft(short volume)
	{
		checkVolume(volume);
		left = volume;
	}
	
	public void setRight(short volume)
	{
		checkVolume(volume);
		right = volume;
	}
	
	public short getLeft()
	{
		return left;
	}
	
	public short getRight()
	{
		return right;
	}
	
	public String toString()
	{
		return "Volume[left="+left+",right="+right+']';
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof StereoVolume)
		{
			StereoVolume other = (StereoVolume)obj;
			return other.getLeft() == getLeft() && other.getRight() == getRight();
		}
		return false;
	}
	
	public int hashCode()
	{
		return (int)getLeft()*MAXIMUM*10 + getRight();
	}
	
	
}
