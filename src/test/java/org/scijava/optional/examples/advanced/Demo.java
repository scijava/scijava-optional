package org.scijava.optional.examples.advanced;

public class Demo {

	public static void main(String... args) {
		OptionsAB options = new OptionsAB().a(10).b("Hello");
		System.out.println("Option a: " + options.getA());
		System.out.println("Option b: " + options.getB());
	}
}
