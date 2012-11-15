/* Source:  http://www.ibm.com/developerworks/java/library/j-dyn0603/ */


package ibm_tutorial;

import java.lang.reflect.Method;

public class MethodReflection {
	public static void main(String[] system_args) {

		try {

			try {
				
				MyClass obj = new MyClass();
				System.out.println(obj.Count);

				String name = "count" ;
				String prop = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				String mname = "get" + prop;
				Class[] types = new Class[] {};
				Method method = obj.getClass().getMethod(mname, types);
				Object result = method.invoke(obj, new Object[0]);
				int value = ((Integer) result).intValue() + 10;
				mname = "set" + prop;
				types = new Class[] { int.class };
				method = obj.getClass().getMethod(mname, types);
				method.invoke(obj, new Object[] { new Integer(value) });

				System.out.println(obj.Count);

			} catch (Exception ex) {
				System.out.println(ex.toString());
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}
}
