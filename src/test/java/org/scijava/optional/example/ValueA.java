package org.scijava.optional.example;

import org.scijava.optional.AbstractValues;
import org.scijava.optional.Values;

interface ValueA extends Values {

	default int a() {
		return getValue("a", 0);
	}
}
