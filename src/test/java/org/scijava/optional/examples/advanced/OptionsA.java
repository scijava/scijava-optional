package org.scijava.optional.examples.advanced;

import org.scijava.optional.Options;

interface OptionsA< T > extends Options< T > {

	default T a(int a) {
		return setValue("a", a);
	}

	default int getA() {
		return getValueOrDefault("a", 0);
	}
}
