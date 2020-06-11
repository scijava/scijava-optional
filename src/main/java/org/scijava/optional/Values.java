package org.scijava.optional;

public interface Values
{
	< T > T getValueOrDefault( String key, T defaultValue );
}
