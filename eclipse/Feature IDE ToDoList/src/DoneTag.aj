

public aspect DoneTag {
	
	// Item Introductions
    public void Item.setDone() {
        this.addTag("Done");
    }
    
}
