/** Original Code Generated 
 package processingcontrolp5;
 import processing.core.PApplet;
 public class ProcessingControlP5 extends PApplet {
 public void setup() {
 }
 public void draw() {
 }
 public static void main(String _args[]) {
 PApplet.main(new String[] { processingcontrolp5.ProcessingControlP5.class.getName() });
 }
 }
 **/


import processing.core.PApplet;
import controlP5.*;

public class ProcessingControlP5Feature extends PApplet {

	ControlP5 cp5;
	public int myColorRect = 200;
	public int myColorBackground = 100;

	public void setup() {

		size(800, 600);
		noStroke();

		cp5 = new ControlP5(this);

		// create a slider
		// parameters:
		// name, minValue, maxValue, defaultValue, x, y, width, height
		cp5.addSlider("sliderA", 100, 200, 100, 100, 260, 100, 14);

		// create 3 numberboxes and assign an id for each
		cp5.addNumberbox("numberboxA", myColorRect, 100, 140, 100, 14).setId(1);
		cp5.addNumberbox("numberboxB", myColorBackground, 100, 180, 100, 14)
				.setId(2);
		cp5.addNumberbox("numberboxC", 0, 100, 220, 100, 14).setId(3);

		// create a texfield
		cp5.addTextfield("textA", 100, 290, 100, 20);

		// change individual settings for a controller
		cp5.getController("numberboxA").setMax(255);
		cp5.getController("numberboxA").setMin(0);
		

	}

	public void draw() {
		background(myColorBackground);
		fill(myColorRect);
		rect(0, 0, width, 100);
	}

	// events from controller numberboxC are received here
	public void numberboxC(int theValue) {
		println("### got an event from numberboxC : " + theValue);
	}

	// an event from slider sliderA will change the value of textfield textA
	// here
	public void sliderA(int theValue) {
		Textfield txt = ((Textfield) cp5.getController("textA"));
		txt.setValue("" + theValue);
	}

	// for every change (a textfield event confirmed with a return) in textfield
	// textA,
	// function textA will be invoked
	public void textA(String theValue) {
		println("### got an event from textA : " + theValue);
	}

	// function controlEvent will be invoked with every value change
	// in any registered controller
	public void controlEvent(ControlEvent theEvent) {
		println("got a control event from controller with id "
				+ theEvent.getId());
		switch (theEvent.getId()) {
		case (1): // numberboxA is registered with id 1
			myColorRect = (int) (theEvent.getController().getValue());
			break;
		case (2): // numberboxB is registered with id 2
			myColorBackground = (int) (theEvent.getController().getValue());
			break;
		}
	}



}
