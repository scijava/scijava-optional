package org.scijava.optional;

public interface Values
{
	< T > T value( String key, T defaultValue );
}
