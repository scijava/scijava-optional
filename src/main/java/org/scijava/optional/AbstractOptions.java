/*-
 * #%L
 * SciJava Optional library for emulating named and default arguments.
 * %%
 * Copyright (C) 2020 - 2021 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.scijava.optional;

import java.util.Arrays;
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
		final ValuesToString sb = new ValuesToString();
		theOptions.forEach( sb );
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
			return theOptions.containsKey( key ) ? ( T ) theOptions.get( key ) : defaultValue;
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
			sb.append( valueToString( value ) );
			if ( !theOptions.containsKey( key ) )
				sb.append( " [default]" );
		}
	}

	private static String valueToString( final Object value )
	{
		if ( value != null && value.getClass().isArray() )
		{
			if ( value instanceof long[] )
				return Arrays.toString( ( long[] ) value );
			else if ( value instanceof int[] )
				return Arrays.toString( ( int[] ) value );
			else if ( value instanceof short[] )
				return Arrays.toString( ( short[] ) value );
			else if ( value instanceof char[] )
				return Arrays.toString( ( char[] ) value );
			else if ( value instanceof byte[] )
				return Arrays.toString( ( byte[] ) value );
			else if ( value instanceof boolean[] )
				return Arrays.toString( ( boolean[] ) value );
			else if ( value instanceof double[] )
				return Arrays.toString( ( double[] ) value );
			else if ( value instanceof float[] )
				return Arrays.toString( ( float[] ) value );
			else
				return Arrays.deepToString( ( Object[] ) value );
		}
		else
			return String.valueOf( value );
	}
}
