package effect;

import processing.core.PApplet;
//import hypermedia.video.OpenCV;

@SuppressWarnings("serial")
public class Tester extends PApplet {
	//OpenCV opencv;
	
	public void setup(){
		size(300,300);
		//opencv = new OpenCV(this);    //  Initialises the OpenCV object
		//opencv.capture( 320, 240 );     //  Opens a video capture stream


		
	}

	public void draw(){
		//opencv.read();

		
	}
	
	
	public static void main( String args[] ){
		PApplet.main(new String[] { "--present", "openCV.Tester" });
	}

	
	
	
}
