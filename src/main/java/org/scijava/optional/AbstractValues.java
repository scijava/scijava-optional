package org.scijava.optional;

import java.util.LinkedHashMap;

public abstract class AbstractValues implements Values
{
	private final LinkedHashMap< String, Object > theOptions;

	public AbstractValues( AbstractOptions< ? > options )
	{
		this.theOptions = options.theOptions;
	}

	public void buildToString( final ValuesToString sb )
	{
		throw new UnsupportedOperationException( "internal, not supposed to be called" );
	}

	@Override
	public < T > T getValue( final String key, final T defaultValue )
	{
		@SuppressWarnings( "unchecked" )
		final T value = ( T ) theOptions.get( key );
		return value == null ? defaultValue : value;
	}

	public class ValuesToString
	{
		private final StringBuilder sb;

		private boolean first;

		public ValuesToString()
		{
			sb = new StringBuilder().append( "{" );
			first = true;
		}

		public < T > void append( final String key, final T value )
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

		@Override
		public String toString()
		{
			sb.append( "}" );
			return sb.toString();
		}
	}
}
