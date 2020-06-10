package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

public abstract class AbstractValues implements Values
{
	private final LinkedHashMap< String, Object > theOptions;

	public AbstractValues( AbstractOptions< ? > options )
	{
		this.theOptions = options.theOptions;
	}

	public void forEach( BiConsumer< String, Object > action )
	{
		theOptions.forEach( action );
	}

	@Override
	public String toString()
	{
		final ValuesToString sb = new ValuesToString();
		forEach( sb );
		return sb.toString();
	}

	@Override
	public < T > T value( final String key, final T defaultValue )
	{
		@SuppressWarnings( "unchecked" )
		final T value = ( T ) theOptions.get( key );
		return value == null ? defaultValue : value;
	}

	public class ValuesToString implements BiConsumer< String, Object >
	{
		private final StringBuilder sb;

		private boolean first;

		public ValuesToString()
		{
			sb = new StringBuilder().append( "{" );
			first = true;
		}

		@Override
		public String toString()
		{
			sb.append( "}" );
			return sb.toString();
		}

		@Override
		public void accept( final String key, final Object value )
		{
			if ( first )
				first = false;
			else
				sb.append( ", " );
			sb.append( key );
			sb.append( " = " );
			sb.append( value );
			if ( !theOptions.containsKey( key ) )
				sb.append( " [default]" );
		}
	}
}
