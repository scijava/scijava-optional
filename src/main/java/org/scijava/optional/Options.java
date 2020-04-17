package org.scijava.optional;

public interface Options< T extends Options< T > >
{
	T setValue( String key, Object value );
}
