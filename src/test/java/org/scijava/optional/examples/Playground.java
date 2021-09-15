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
package org.scijava.optional.examples;

import java.util.function.BiConsumer;
import org.scijava.optional.AbstractOptions;
import org.scijava.optional.Options;
import org.scijava.optional.Values;

public class Playground
{
	interface OptionA< T > extends Options< T >
	{
		default T a( final int a )
		{
			return setValue( "a", a );
		}

		interface ValueA extends Values
		{
			default void forEach( BiConsumer< String, Object > action )
			{
				action.accept( "a", a() );
			}

			default int a()
			{
				return getValueOrDefault( "a", 0 );
			}
		}
	}

	// ====================================================================================== //

	interface OptionB< T > extends Options< T >
	{
		default T b( final int b )
		{
			return setValue( "b", b );
		}

		interface ValueB extends Values
		{
			default void forEach( BiConsumer< String, Object > action )
			{
				action.accept( "b", b() );
			}

			default int b()
			{
				return getValueOrDefault( "b", 0 );
			}
		}
	}

	// ====================================================================================== //

	static class FactoryAOptions extends AbstractOptions< FactoryAOptions > implements OptionA< FactoryAOptions >, OptionB< FactoryAOptions >
	{
		class FactoryAValues extends AbstractValues implements ValueA, ValueB
		{
			@Override
			public void forEach( BiConsumer< String, Object > action )
			{
				ValueA.super.forEach( action );
				ValueB.super.forEach( action );
			}
		}

		public final FactoryAValues values = new FactoryAValues();

		/*
		 * The following methods result in immutable FactoryAOptions instances,
		 * where options.a(3) results in a new FactoryAOptions instance with the
		 * same parameters as options, except a=3.
		 *
		 * If in-place modification of the options builder is desired,
		 * the following methods should be left out. Then, options.a(3) modifies
		 * the options instance, setting a=3
		 */

		public FactoryAOptions()
		{}

		private FactoryAOptions( FactoryAOptions that )
		{
			super( that );
		}

		@Override
		protected FactoryAOptions copyOrThis()
		{
			return new FactoryAOptions( this );
		}
	}

	static < O extends OptionA< O > & OptionB< O > > void factory( final O options )
	{
		System.out.println( "(factory) options = " + options );
	}

	public static void main( final String[] args )
	{
		final FactoryAOptions options = new FactoryAOptions().a( 3 ).b( 2 );
		System.out.println( "options = " + options );
		factory( options );
		System.out.println( "options.values = " + options.values );
	}
}
