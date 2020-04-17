package org.scijava.optional.example;

import org.scijava.optional.AbstractOptions;
import org.scijava.optional.AbstractValues;

public class FactoryAOptions extends AbstractOptions< FactoryAOptions >
		implements OptionA< FactoryAOptions >
{

	static class FactoryAValues extends AbstractValues implements ValueA

	{
        public FactoryAValues( final FactoryAOptions options ){
			super(options);
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

