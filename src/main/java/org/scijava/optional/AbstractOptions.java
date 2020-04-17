package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.Map;

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
		if ( additionalOptions == null )
			return ( T ) this;

		T concat = copyOrThis();
		additionalOptions.theOptions.forEach( concat.theOptions::put );
		return concat;
	}

	@Override
	public T add( final String key, final Object value )
	{
		final T copy = copyOrThis();
		copy.theOptions.remove( key );
		copy.theOptions.put( key, value );
		return copy;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append( "{" );
		int numLeft = theOptions.size();
		for ( Map.Entry< String, Object > option : theOptions.entrySet() )
		{
			sb.append( option.getKey() );
			sb.append( " = " );
			sb.append( option.getValue() );
			if ( --numLeft > 0 )
				sb.append( ", " );
		}
		sb.append( "}" );
		return sb.toString();
	}
}
