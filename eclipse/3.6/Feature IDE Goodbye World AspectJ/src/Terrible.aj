
public aspect Terrible {
	after(): call(void Main.print()) {
		System.out.print(" Terrible");
	}
}