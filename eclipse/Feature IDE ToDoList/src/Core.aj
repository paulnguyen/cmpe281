
import java.util.ArrayList;

public aspect Core {
	
	// Core Introductions
    private ArrayList<Item> Main.coreList = new ArrayList<Item>() ;
    
    public void Main.insert(Item item) {
        coreList.add(item);
    }
    
    public void Main.delete(int index)
    {
    	coreList.remove(index) ;
    }
    
    public ArrayList<Item> Main.getList()
    {
    	return coreList ;
    }
    
    // Core Overrides
    void around(Main obj) : call( * Main.setup() ) && target(obj) {
    	obj.insert( new Item("Test #1") ) ;
    	obj.insert( new Item("Test #2") ) ;
	}    
    
    void around(Main obj) : call( * Main.print() ) && target(obj) {
    	System.out.println( "Welcome to the To Do List -- Version 1.0." ) ;
        for ( Item item  : obj.getList() )
        {
            System.out.println( item.getName() ) ;
        }    	
	}    
    
}
