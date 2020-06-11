package org.scijava.optional.examples.advanced;

import java.util.function.BiConsumer;
import org.scijava.optional.Options;
import org.scijava.optional.Values;

public interface OptionsB< T > extends Options< T >
{
	default T b( String b )
	{
		return setValue( "b", b );
	}

	interface Val extends Values
	{
		default String b()
		{
			return getValueOrDefault( "b", "" );
		}

		default void forEach( BiConsumer< String, Object > action )
		{
			action.accept( "b", b() );
		}
	}
}
