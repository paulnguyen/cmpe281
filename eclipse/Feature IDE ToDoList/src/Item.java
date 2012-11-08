
import java.util.HashMap;

public class Item {

	private String name ;
	private HashMap<String,String> tags ;
	
	public Item(String s) { name = s ; tags = new HashMap<String,String>() ; }
	public void setName( String newName ) { this.name = newName ; }
	public String getName() { return this.name ; }
	public Object[] getTags() { return tags.keySet().toArray() ; }
	public void addTag( String tag ) { tags.put(tag, "") ;}
	public void resetTags() { tags.clear() ; }
	
}
