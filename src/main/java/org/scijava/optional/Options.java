package org.scijava.optional;

public interface Options< T extends Options< T > >
{
	T add( String key, Object value );
}
