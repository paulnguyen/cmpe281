
public aspect ScrumTag {

	// Item Introductions
    public void Item.setNew() {
    	this.resetTags() ;
        this.addTag("New");
    }

    public void Item.setReady() {
    	this.resetTags() ;
        this.addTag("Ready");
    }

    public void Item.setInProgress() {
    	this.resetTags() ;
        this.addTag("In-Progress");
    }

    public void Item.setInQA() {
    	this.resetTags() ;
        this.addTag("In-QA");
    }

    public void Item.setAccepted() {
    	this.resetTags() ;
        this.addTag("Accepted");
    }


}
