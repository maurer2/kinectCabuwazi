package siluette;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.*;

public class SkelletonTest extends PApplet {
SimpleOpenNI  context;

PVector realWorldPoint2= new PVector();
int[] userColors = { color(255,0,0), color(0,255,0), color(0,0,255), color(255,255,0), color(255,0,255), color(0,255,255) };

PImage depth;
PImage imer;
PImage partikel;

int max=640*480;
int[] userPixels = new int[max];

//Haende
PVector  rightHand = new PVector();
PVector  rightHand2 = new PVector();
PVector  leftHand = new PVector();
PVector  leftHand2 = new PVector();


public void setup(){
  context = new SimpleOpenNI(this);
  //context = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);   
  
  // enable depthMap generation 
  context.enableDepth();  
  context.enableRGB();
  context.enableHands();
  context.enableGesture();
  
  // enable skeleton generation for all joints
  context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
  context.setMirror(false); 
  
  //HAnd
  
  // add focus gestures  / here i do have some problems on the mac, i only recognize raiseHand ? Maybe cpu performance ?
  context.addGesture("Wave");
  context.addGesture("Click");
  context.addGesture("RaiseHand");  
  
  context.enableScene(640,480,60);  
  
  stroke(255,0,0);
  strokeWeight(5);
  
  //smooth();  
  size(context.depthWidth(), context.depthHeight());
  imer = loadImage("imer.png");  

}

public void draw()
{
  background(0);
  // update the cam
  context.update(); 
  
  // USer Map
  context.getUserPixels(1, userPixels);  
  //context.setDepthImageColor(255, 0, 0);  
 
  depth=context.sceneImage(); 
  depth.loadPixels();  
  
  if (context.getNumberOfUsers()>0) {  
	  // Pixel fuer Pixel durchlaufen
	  for (int zaehler = 0; zaehler < max; zaehler++ ) {	    		      
    	  if (userPixels[zaehler] == 1){    		  
    		  //stroke(userColors[1]);
    		  //println(color(depth.pixels[zaehler]));    		 
    	  } else {
    		  //Hintergrund ausblenden
    		  //depth.pixels[zaehler]=color(0);    		   
    		  //point(50,50);
    	  }
     }
  
  depth.updatePixels();  
  
  }
  // Depthmap anzeigen
  image(depth,0,0);
  
  // Imer anzeigen  
  if (context.getNumberOfUsers()>0) {  
	  //imer();
  }  
  //Partikel zeigen
  //Partikel();
  
  // Haende zeigen
  if(context.isTrackingSkeleton(1)) {	 
	  drawHaende(1);
  }
  
  //Kollision
  if(context.isTrackingSkeleton(1)) {	 
	  //Kollision(1);
  }  

  // Skelet anzeigen
  if(context.isTrackingSkeleton(1)) {	  
	   drawSkeleton(1);
	   
  }
  
  
    //rect(posProjected.x-50,posProjected.y-50,100,100);
    
	//  for (int x = 0; x < depth.width; x++ ) {
	//	    for (int y = 0; y < depth.height; y++ ) {
	//	      int loc = x + y*depth.width;
	//	      
	//	      if (context.getNumberOfUsers()>0) {	    		      
	//	    	  if (userPixels[loc] == 1){	    		  
	//	    		  //depth.pixels[loc]=color(255,0,0);    	  
	//	    	  } else {
	//	    		  depth.pixels[loc]=color(0);
	//	    	  }
	//	      }  
	//	    	   
	//	      
	//	    }
	//  }   
  
}

public void imer(){
	  PVector posRealWorld = new PVector();
	  PVector posProjected = new PVector();
	  context.getCoM(1, posRealWorld);
	  context.convertRealWorldToProjective(posRealWorld,posProjected);  
	  
	  //rect(posProjected.x-50,posProjected.y-50,100,100);  
	  //rect(posProjected.x-50,height/2,mappedRadius,mappedRadius);
	  //println(posProjected);
	  //int res=(int) (map(posProjected.z,0,4000,140,150));
	  //imer.resize(res, res);
	  int xpos=(int) posProjected.x - imer.width/2;
	  int ypos=height-imer.height;
	  image(imer,xpos,ypos);	  
		
	}


public void drawHaende(int userId){
	//Handposition auslesen 
	context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_WRIST,rightHand);
	context.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_LEFT_HAND,leftHand);
	
	// Pos Konvertieren
	context.convertRealWorldToProjective(rightHand, rightHand2); 
	context.convertRealWorldToProjective(leftHand, leftHand2);	  

	color(255,0,255);
	fill(0,255);	
	
	// SKalierungsfaktor
	int sfaktorr=(int) (map(rightHand2.z,0,4000,150,10));
	int sfaktorl=(int) (map(leftHand2.z,0,4000,150,10));	

	// Handboxen zeichnen x y z
	rect(rightHand2.x-sfaktorr/2,rightHand2.y-sfaktorr/2,sfaktorr,sfaktorr);
	rect(leftHand2.x-sfaktorl/2,leftHand2.y-sfaktorl/2,sfaktorl,sfaktorl);	
	
	//Kollsion test
//	int[] testarray= new int[50*50];
//	for (int i=0;i<testarray.length;i++) {
//		testarray[i]=1;
//	}
//	
//	for (int x = 0; x < 50; x++ ) {
//		 for (int y = 0; y < 50; y++ ) {
//		     int loc = x + y*depth.width;
//		     
//		     if ((leftHand2.x == x) & (leftHand2.y == y)) {
//		    	 println("Treffer");
//		     }
//		    	 
//		     
//		 }
//	}	
	
	
}

public void Partikel(){
	partikel = createImage(50, 50, RGB);
	loadPixels();
	for (int i = 0; i < (partikel.width*partikel.height); i++) {
	  partikel.pixels[i] = color(0,255,255);
	}
	updatePixels();
	image(partikel, 0, 0);	
	
}

public void Kollision(int userId){
	//println(partikel.width);	
}

public void drawSkeleton(int userId){
  context.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);
  context.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_LEFT_SHOULDER);
  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_LEFT_ELBOW);
context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW, SimpleOpenNI.SKEL_LEFT_HAND); 
//
  context.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
 context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_RIGHT_ELBOW);
context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW, SimpleOpenNI.SKEL_RIGHT_HAND);  
//
  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
////
  context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_LEFT_HIP);
 context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP, SimpleOpenNI.SKEL_LEFT_KNEE);
  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE, SimpleOpenNI.SKEL_LEFT_FOOT);
////
  context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_RIGHT_HIP);
  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP, SimpleOpenNI.SKEL_RIGHT_KNEE);
  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE, SimpleOpenNI.SKEL_RIGHT_FOOT);
////  
  context.drawLimb(1,SimpleOpenNI.SKEL_LEFT_HAND, SimpleOpenNI.SKEL_LEFT_HAND);
  context.drawLimb(1,SimpleOpenNI.SKEL_RIGHT_HAND, SimpleOpenNI.SKEL_RIGHT_HAND);
}

// -----------------------------------------------------------------
// SimpleOpenNI events

public void onNewUser(int userId)
{
  println("onNewUser - userId: " + userId);
  println("  start pose detection");
  
  context.startPoseDetection("Psi",userId);
}

public void onLostUser(int userId)
{
  println("onLostUser - userId: " + userId);
}

public void onStartCalibration(int userId)
{
  println("onStartCalibration - userId: " + userId);
}

public void onEndCalibration(int userId, boolean successfull)
{
  println("onEndCalibration - userId: " + userId + ", successfull: " + successfull);
  
  if (successfull) 
  { 
    println("  User calibrated !!!");
    context.startTrackingSkeleton(userId); 
  } 
  else 
  { 
    println("  Failed to calibrate user !!!");
    println("  Start pose detection");
    context.startPoseDetection("Psi",userId);
  }
}

public void onStartPose(String pose,int userId)
{
  println("onStartPose - userId: " + userId + ", pose: " + pose);
  println(" stop pose detection");
  
  context.stopPoseDetection(userId); 
  context.requestCalibrationSkeleton(userId, true);
 
}

public void onEndPose(String pose,int userId)
{
  println("onEndPose - userId: " + userId + ", pose: " + pose);
}


}
