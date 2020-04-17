package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class AbstractOptions< T extends AbstractOptions< T > > implements Options< T >
{
	final LinkedHashMap< String, Object > theOptions = new LinkedHashMap<>();

	protected AbstractOptions( T that )
	{
		that.theOptions.forEach( theOptions::put );
	}

	public AbstractOptions()
	{
	}

	protected T copyOrThis()
	{
		return ( T ) this;
	}

	protected T append( final T additionalOptions )
	{
		T concat = copyOrThis();
		additionalOptions.theOptions.forEach( concat.theOptions::put );
		return concat;
	}

	@Override
	public T setValue( final String key, final Object value )
	{
		final T copy = copyOrThis();
		copy.theOptions.remove( key );
		copy.theOptions.put( key, value );
		return copy;
	}

	@Override
	public String toString()
	{
		StringJoiner result = new StringJoiner(", ","{ ",  " }");
		theOptions.forEach((key, value) -> result.add(key + "=" + value));
		return result.toString();
	}
}
