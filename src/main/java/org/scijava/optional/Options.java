package org.scijava.optional;

public interface Options< T >
{
	T setValue( String key, Object value );

	< V > V getValue( String key, V defaultValue );
}
