public class Foo {
	@Oneway
	public static void someMethod() {
		System.out.println("Some Method...");
	}

	public static void main(String[] args) {
		someMethod(); // executes in background
	}
}
