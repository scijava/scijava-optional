package org.scijava.optional.examples.advanced;

public class Demo
{
	public static void main( String... args )
	{
		final OptionsAB options = new OptionsAB().b( "Hello" ).a( 42 );
		System.out.println( "options = " + options );
		System.out.println( "options.values = " + options.values );
		System.out.println();
		System.out.println( "Option a: " + options.values.a() );
		System.out.println( "Option b: " + options.values.b() );
	}
}
