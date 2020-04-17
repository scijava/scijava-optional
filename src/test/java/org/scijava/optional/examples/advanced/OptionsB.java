package org.scijava.optional.examples.advanced;

import org.scijava.optional.Options;

public interface OptionsB< T > extends Options<T> {

	default T b(String b) {
		return setValue("b", b);
	}

	default String getB() {
		return getValueOrDefault("b", "");
	}

}
