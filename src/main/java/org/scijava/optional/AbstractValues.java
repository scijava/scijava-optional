package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.StringJoiner;

public abstract class AbstractValues implements Values
{
	private final LinkedHashMap< String, Object > theOptions;

	public AbstractValues( AbstractOptions< ? > options )
	{
		this.theOptions = options.theOptions;
	}

	@Override
	public < T > T getValue( final String key, final T defaultValue )
	{
		@SuppressWarnings( "unchecked" )
		final T value = ( T ) theOptions.get( key );
		return value == null ? defaultValue : value;
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(", ","{ ",  " }");
		theOptions.forEach((key, value) -> result.add(key + "=" + value));
		return result.toString();
	}
}
