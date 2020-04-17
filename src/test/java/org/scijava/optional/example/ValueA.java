package org.scijava.optional.example;

import org.scijava.optional.AbstractValues;
import org.scijava.optional.Values;

interface ValueA extends Values {

	default void buildToString(AbstractValues.ValuesToString sb) {
		sb.append("a", a());
	}

	default int a() {
		return getValue("a", 0);
	}
}
