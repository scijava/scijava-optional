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
package org.scijava.optional.examples.simple;

import java.util.function.BiConsumer;
import org.scijava.optional.AbstractOptions;

public class Demo
{
	public static void main( String... args )
	{
		print( "new MyOptions()", new MyOptions() );
		print( "new MyOptions().number( 42 )", new MyOptions().number( 42 ) );
		print( "new MyOptions().text( \"Hello\" )", new MyOptions().text( "Hello" ) );
		print( "new MyOptions().text( \"World\" ).number( 12 )", new MyOptions().text( "World" ).number( 12 ) );
	}

	private static void print( String title, MyOptions options )
	{
		System.out.println( title );
		System.out.println( "  options = " + options );
		System.out.println( "  options.values = " + options.values );
		System.out.println( "  options.values.number() = " + options.values.number() );
		System.out.println( "  options.values.text() = " + options.values.text() );
		System.out.println();
	}

	public static class MyOptions extends AbstractOptions< MyOptions >
	{
		public MyOptions number( int number )
		{
			return setValue( "number", number );
		}

		public MyOptions text( String text )
		{
			return setValue( "text", text );
		}

		public Values values = new Values();

		public class Values extends AbstractValues
		{
			public int number()
			{
				return getValueOrDefault( "number", -1 );
			}

			public String text()
			{
				return getValueOrDefault( "text", "" );
			}

			/*
			 * Overriding forEach() makes default values show up in Values.toString().
			 * (Try commenting it out.)
			 */
			@Override
			public void forEach( final BiConsumer< String, Object > action )
			{
				action.accept( "number", number() );
				action.accept( "text", text() );
			}
		}
	}
}
