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
