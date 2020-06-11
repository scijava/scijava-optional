package org.scijava.optional.examples.advanced;

import java.util.function.BiConsumer;
import org.scijava.optional.AbstractOptions;

/**
 * {@code OptionsAB} inherits options defined in interfaces {@code OptionsA} and {@code OptionsB}
 */
public class OptionsAB
		extends AbstractOptions< OptionsAB >
		implements OptionsA< OptionsAB >, OptionsB< OptionsAB >
{
	public Values values = new Values();

	/**
	 * {@code OptionsAB.Values} inherits default values defined in interfaces {@code OptionsA.Val} and {@code OptionsB.Val}
	 */
	public class Values extends AbstractValues implements OptionsA.Val, OptionsB.Val
	{
		@Override
		public void forEach( BiConsumer< String, Object > action )
		{
			OptionsA.Val.super.forEach( action );
			OptionsB.Val.super.forEach( action );
		}
	}
}

