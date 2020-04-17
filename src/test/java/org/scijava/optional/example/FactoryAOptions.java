package org.scijava.optional.example;

import org.scijava.optional.AbstractOptions;
import org.scijava.optional.AbstractValues;
import org.scijava.optional.Options;

public class FactoryAOptions extends AbstractOptions< FactoryAOptions >
		implements OptionA< FactoryAOptions >
{

	static class FactoryAValues extends AbstractValues implements ValueA

	{
        public FactoryAValues( final FactoryAOptions options ){
			super(options);
		}

		@Override
		public String toString() {
			final ValuesToString sb = new ValuesToString();
			ValueA.super.buildToString(sb);
			return sb.toString();
		}

	}

	public final FactoryAValues values = new FactoryAValues(this);

	public FactoryAOptions() {
		super();
	}

	public FactoryAOptions(FactoryAOptions that) {
		super(that);
	}

	@Override
	public FactoryAOptions copyOrThis() {
		return new FactoryAOptions(this);
	}
}

