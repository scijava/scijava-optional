package org.scijava.optional;

public interface Values
{
	< T > T getValue( String key, T defaultValue );
}
