package org.scijava.optional.example;

import org.scijava.optional.Options;

interface OptionA< T extends Options< T > > extends Options< T > {

	default T a(int a) {
		return setValue("a", a);
	}
}
