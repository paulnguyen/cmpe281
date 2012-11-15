/* Source:  http://www.ibm.com/developerworks/java/library/j-dyn0603/ */


package ibm_tutorial;

import java.lang.reflect.Field;

public class FieldReflection {

	public static void main(String[] system_args) {
		
		try {
			MyClass obj = new MyClass() ;			
		    System.out.println( obj.Count ) ;		

			String fieldName = "Count" ;
		    Field field = obj.getClass().getDeclaredField(fieldName);
		    int value = field.getInt(obj) + 1;
		    field.setInt(obj, value);		
		    
		    System.out.println( obj.Count ) ;

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}	
	
}
