package org.scijava.optional.examples.simple;

import org.scijava.optional.AbstractOptions;

public class Demo {

	public static void main(String... args) {

		print("Title A", new MyOptions());
		print("Title B", new MyOptions().number(42));
		print("Title C", new MyOptions().text("Hello"));
		print("Title D", new MyOptions().number(12).text("World"));
	}

	private static void print(String title, MyOptions options) {
		System.out.println(title +
				"       number: " + options.getNumber()
				+ "     text: " + options.getText());
	}

	private static class MyOptions extends AbstractOptions< MyOptions > {

		public MyOptions number(int number) {
			return setValue("number", number);
		}

		public int getNumber() {
			return getValueOrDefault("number", -1);
		}

		public MyOptions text(String text) {
			return setValue("text", text);
		}

		public String getText(){
			return getValueOrDefault("text", "");
		}
	}
}
