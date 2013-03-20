package camTest;

import processing.core.PApplet;
import SimpleOpenNI.*;

public class test extends PApplet{
	
	SimpleOpenNI  context;

	
	public void setup(){
		size(600,600);
		frameRate(60);
		context = new SimpleOpenNI(this);
		   
		// mirror is by default enabled
		context.setMirror(true);
		  
		 // enable depthMap generation 
		context.enableDepth();
		  
		// enable ir generation
		context.enableRGB();
		//context.enableRGB(640,480,30);
		//context.enableRGB(1280,1024,15);
		 
		size(context.depthWidth() + context.rgbWidth() + 10, context.rgbHeight());		
		
		
	}
	
	public void draw(){
		// update the cam
		context.update();
		  
		background(200,0,0);
		  
		// draw depthImageMap
		image(context.depthImage(),0,0);
		  
		 // draw irImageMap
		image(context.rgbImage(),context.depthWidth() + 10,0);	
		
		
	}
	
	
}
