package com.oreilly.hh;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * 
 * Manages persistence for {@link SourceMedia} typesafe enumeration
 *
 */
public class SourceMediaType implements UserType{

	/**
	 * Indicates whether objects managed by this type are mutable
	 * 
	 * @return <code>false</code>,since enumeration instances are immutable
	 * singletons
	 * @param args
	 */
	public boolean isMutable()
	{
		return false;
	}
	
	/**
	 * return a deep copy of the persistent state,stopping at 
	 * entities and collections.
	 * @param value the object whose state is to be copied.
	 * @return the same object,since enumeration instances are singletons.
	 */
	public Object deepCopy(Object value)
	{
		return value;
	}
	
	/**
	 * compare two instances of the class mapped by this type for persistence
	 * "equality"
	 * 
	 * @param x first object to be compared.
	 * @param y second object to be compared.
	 * @return <code>true</code> iff both represent
	 * @param args
	 */
	public boolean equals(Object x,Object y)
	{
		//we can compare instances,since SourceMedia are immutable singletons
		return (x == y);
	}
	
	/**
	 * determine the class that is returned by {@link #nullSafeGet}
	 * @param args
	 * 
	 */
	public Class returnedClass()
	{
		return SourceMedia.class;
	}
	
	/**
	 * determine the sql types of the column used by this type mapping.
	 * @return a single VARCHAR column
	 * 
	 * @param args
	 */
	public int[] sqlTypes()
	{
		//allocate a new array each time to protect against callers changing
		//its contents.
		int[] typeList = new int[1];
		typeList[0] = Types.VARCHAR;
		return typeList;
	}
	
	/**
	 * retrieve an instance of the mapped class from a jdbc
	 * 
	 * @param rs the results from which the instance should be retrieved.
	 * @param names the columns from which the instance should be retrieved.
	 * @para owner the entity containing the value being retrieved
	 * @return the retrieved value, or null
	 * @throws SQLException if there is a problem accessing the database.
	 */
	public Object nullSafeGet(ResultSet rs,String[] names,Object owner) throws SQLException
	{
		//start by looking up the value name
		String name = (String)Hibernate.STRING.nullSafeGet(rs, names[0]);
		if(name == null)
		{
			return null;
		}
		//then find the corresponding enumeration value
		try{
			return SourceMedia.valueOf(name);
		}catch(IllegalArgumentException e)
		{
			throw new HibernateException("bad sourceMedia value:" +name,e);
		}
	}
	
	/**
	 * write an instance of the mapped class to a {@link preparedstatement}
	 * handling null values
	 * 
	 * @param st a jdbc prepared statement
	 * @param value the sourceMedia value to write
	 * @param index the parameter index within the prepared statement at which
	 *         this value is to be written
	 * @throws SQLException if there is a problem accessing the database
	 * @param args
	 */
	public void nullSafeSet(PreparedStatement st,Object value,int index)
	      throws SQLException
	{
		String name = null;
		
		if(value != null)
			name = ((SourceMedia)value).toString();
        Hibernate.STRING.nullSafeSet(st, name, index);
		
	}
	
	/**
	 * reconstruct an object from the cacheable representation.at the very least this
	 * method should perform a deep copy if the type is mutable.
	 * 
	 * @param cached the object to be cached
	 * @param owner the owner of the cached object
	 * @return a reconstructed object from the cachable representation
	 */
	public Object assemble(Serializable cached, Object owner)
	{
		return cached;
	}
	
	public Serializable disassemble(Object value)
	{
		return (Serializable)value;
	}
	
	public int hashCode(Object x)
	{
		return x.hashCode();
	}
	
	public Object replace(Object original,Object owner)
	     throws HibernateException
	{
		return original;
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
