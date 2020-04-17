package org.scijava.optional.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactoryAOptionsTest {

	@Test
	public void test() {
		FactoryAOptions options = new FactoryAOptions().a(10);
		assertEquals(10, options.values.a());
	}
}
