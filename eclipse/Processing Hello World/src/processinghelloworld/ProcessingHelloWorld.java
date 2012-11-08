package processinghelloworld;

import processing.core.PApplet;


public class ProcessingHelloWorld extends PApplet {

	public void setup() {
		
		 size(500, 500);
		  smooth() ;
		  strokeWeight(3);
		  strokeCap(ROUND);
		  //background(102);
		  
		  // Only draw once
		  //noLoop();
		
	}

	public void draw() {
		
		  fill(0);
		  text("Hello World!", 100, 60) ;
		  println( "Hello World!" ) ;
	}
}
