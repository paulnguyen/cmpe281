/* Source:  http://www.ibm.com/developerworks/java/library/j-dyn0603/ */

package ibm_tutorial;

import java.lang.reflect.Constructor;

public class ClassReflection {

	public static void main(String[] system_args) {
		try {

			// Load a Class
			Class clas = null;
			clas = Class.forName("ibm_tutorial.MyClass");
			// use the loaded class
			System.out.println(clas.toString());

			// Reflecting to call Constructor
			Class[] types = new Class[] { String.class, String.class };
			Constructor<TwoString> cons = TwoString.class.getConstructor(types);
			Object[] args = new Object[] { "a", "b" };
			TwoString ts = (TwoString) cons.newInstance(args);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}

}
