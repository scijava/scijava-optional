package org.scijava.optional.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FactoryAOptionsTest {

	@Test
	public void test() {
		FactoryAOptions options = new FactoryAOptions().a(10);
		assertEquals(10, options.getA());
	}

	@Test
	public void testClone() {
		FactoryAOptions options = new FactoryAOptions().a(10);
		FactoryAOptions modified = options.copy().a(20);
		assertEquals(10, options.getA());
		assertEquals(20, modified.getA());
	}

	@Test
	public void testToString() {
		FactoryAOptions options = new FactoryAOptions().a(10);
		assertEquals("{ a=10 }", options.toString());
	}

	@Test
	public void testValuesToString() {
		FactoryAOptions options = new FactoryAOptions().a(10);
		assertEquals("{ a=10 }", options.toString());
	}

}
