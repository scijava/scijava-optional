package org.scijava.optional;

import org.junit.Test;
import org.scijava.optional.AbstractOptions;

import static org.junit.Assert.assertEquals;

public class AbstractOptionsTest {

	@Test
	public void test() {
		MyOptions options = new MyOptions().a(10);
		assertEquals(10, options.getA());
	}

	@Test
	public void testClone() {
		MyOptions options = new MyOptions().a(10);
		MyOptions modified = options.copy().a(20);
		assertEquals(10, options.getA());
		assertEquals(20, modified.getA());
	}

	@Test
	public void testToString() {
		MyOptions options = new MyOptions().a(10);
		assertEquals("{ a=10 }", options.toString());
	}

	@Test
	public void testValuesToString() {
		MyOptions options = new MyOptions().a(10);
		assertEquals("{ a=10 }", options.toString());
	}

	private static class MyOptions extends AbstractOptions<MyOptions > {

		public MyOptions a(int value) {
			return setValue("a", value);
		}

		public int getA() {
			return getValueOrDefault("a", 0);
		}

		public MyOptions copy() {
			return new MyOptions().setOptions(this);
		}
	}
}
