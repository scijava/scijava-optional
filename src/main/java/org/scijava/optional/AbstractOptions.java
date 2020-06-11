package org.scijava.optional;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbstractOptions< T extends AbstractOptions< T > > implements Options< T >
{
	final LinkedHashMap< String, Object > theOptions = new LinkedHashMap<>();

	protected AbstractOptions( T that )
	{
		theOptions.putAll( that.theOptions );
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
		additionalOptions.theOptions.forEach( ( k, v ) -> {
			// NB remove existing key for ordering for appended options
			concat.theOptions.remove( k );
			concat.theOptions.put( k, v );
		} );
		return concat;
	}

	@Override
	public T setValue( final String key, final Object value )
	{
		final T copy = copyOrThis();
		// NB remove existing key for ordering for appended options
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

	protected abstract class AbstractValues implements Values
	{
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
		public < T > T getValueOrDefault( final String key, final T defaultValue )
		{
			@SuppressWarnings( "unchecked" )
			final T value = ( T ) theOptions.get( key );
			return value == null ? defaultValue : value;
		}
	}

	protected class ValuesToString implements BiConsumer< String, Object >
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
