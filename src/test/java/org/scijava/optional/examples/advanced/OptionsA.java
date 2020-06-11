package org.scijava.optional.examples.advanced;

import java.util.function.BiConsumer;
import org.scijava.optional.Options;
import org.scijava.optional.Values;

interface OptionsA< T > extends Options< T >
{
	default T a( int a )
	{
		return setValue( "a", a );
	}

	interface Val extends Values
	{
		default int a()
		{
			return getValueOrDefault( "a", 0 );
		}

		default void forEach( BiConsumer< String, Object > action )
		{
			action.accept( "a", a() );
		}
	}
}
