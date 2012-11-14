
public aspect Hello {
    // PointCut
    after(ProcessingControlP5Feature obj): target(obj) && call(void ProcessingControlP5Feature.setup(..)) {
        System.out.println( "Processing Setup Done..." );
    }
	
}