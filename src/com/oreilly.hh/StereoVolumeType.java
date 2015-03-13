package com.oreilly.hh;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class StereoVolumeType implements CompositeUserType{

	public String[] getPropertyNames()
	{
		return new String[]{"left","right"};
	}
	
	public Type[] getPropertyTypes()
	{
		return new Type[]{Hibernate.SHORT,Hibernate.SHORT};
	}
	
	public Object getPropertyValue(Object component,int property)
	{
		StereoVolume volume = (StereoVolume)component;
		
		short result;
		
		switch(property)
		{
		case 0:
			result = volume.getLeft();
			break;
			
		case 1:
			result = volume.getRight();
			break;
			
		default:
			throw new IllegalArgumentException("unknown property: "+ property);
		
		}
		
		return new Short(result);
	}
	
	public void setPropertyValue(Object component,int property,Object value)
	{
        StereoVolume volume = (StereoVolume)component;		
		short newLevel = ((Short)value).shortValue();
		
		switch(property)
		{
		case 0:
			volume.setLeft(newLevel);
			break;
			
		case 1:
			volume.setRight(newLevel);
			break;
			
		default:
			throw new IllegalArgumentException("unknown property: "+ property);
		
		}
	}
	
	public Class returnedClass()
	{
		return StereoVolume.class;
	}
	
	public boolean equals(Object x,Object y)
	{
		if(x==y)
		{
			return true;
			
		}
		if(x==null || y==null)
		{
			return false;
		}
		
		return ((StereoVolume)x).equals(y);
	}
	
	public Object deepCopy(Object value)
	{
		if(value==null)
		{
			return null;
		}
		StereoVolume volume = (StereoVolume)value;
		return new StereoVolume(volume.getLeft(),volume.getRight());
	}
	
	public boolean isMutable()
	{
		return true;
	}
	
	public Object nullSafeGet(ResultSet rs,String[] names,
			                 SessionImplementor session,Object owner)
	        throws SQLException
	{
		Short left = (Short)Hibernate.SHORT.nullSafeGet(rs, names[0]);
		Short right = (Short)Hibernate.SHORT.nullSafeGet(rs, names[1]);
		
		if(left == null || right == null)
		{
			return null;
		}
		return new StereoVolume(left.shortValue(),right.shortValue());
	}
	
	public void nullSafeSet(PreparedStatement st,Object value,
            int index,SessionImplementor session)
         throws SQLException
    {
        
        if(value == null)
        {
        	Hibernate.SHORT.nullSafeSet(st, null,index);
        	Hibernate.SHORT.nullSafeSet(st, null,index+1);
        }else
        {
        	StereoVolume vol = (StereoVolume)value;
        	Hibernate.SHORT.nullSafeSet(st, new Short(vol.getLeft()),index);
        	Hibernate.SHORT.nullSafeSet(st, new Short(vol.getRight()),index+1);
        }
        
    }
	
	public Object assemble(Serializable cached,SessionImplementor session,Object owner)
	{
		return deepCopy(cached);
	}
	
	public Serializable disassemble(Object value,SessionImplementor session)
	{
		return (Serializable)deepCopy(value);
	}
	
	public int hashCode(Object x)
	{
		return x.hashCode();
	}
	
	public Object replace(Object original,Object target,
			              SessionImplementor session,Object owner)
	{
		return deepCopy(original);
	}
}
