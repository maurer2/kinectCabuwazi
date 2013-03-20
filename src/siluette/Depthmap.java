package siluette;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.*;


public class Depthmap extends PApplet {

	SimpleOpenNI  context;
	PImage depth;
	PImage depth2;
	PImage backgroundImage;
	
	public float zoomfactor;
	public float threshold = 180;

public void setup(){

  context = new SimpleOpenNI(this);  
   
  // enable depthMap generation 
  context.enableDepth();
  //context.setMirror(false);
  
  // enable camera image generation
  //context.enableRGB();
  background(0,0,0);
  frameRate(60);
  stroke(0,0,255);

  //context.depthMapRealWorld();
  
  size(1280,720);
  
  // Fullscreen Resizen
  fullscreen();
  
  // HIntergrundbild
  backgroundImage = loadImage("zirkuszelt.png"); 
  
  context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
  
  // enable the scene, to get the floor
  context.enableScene();
  
  
  
}

public void fullscreen() {
	int width_z=context.depthWidth(); //640
	int height_z=context.depthHeight(); //480	
	int width_s=width; //1280
	int height_s=height; //720
	
	float zoomx=(float)width_s/width_z;
	float zoomy=(float)height_s/height_z;	

	if (zoomx < zoomy) {
		zoomfactor= zoomx;
	}
	else zoomfactor= zoomy; 	
}

public void drawSkeleton(int userId){
	// TODO Auto-generated method stub
	
	
	  // to get the 3d joint data
	  /*
	  PVector jointPos = new PVector();
	  context.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_NECK,jointPos);
	  println(jointPos);
	  */
	  
	  context.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);

	  context.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_LEFT_SHOULDER);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_LEFT_ELBOW);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW, SimpleOpenNI.SKEL_LEFT_HAND);

	  context.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_RIGHT_ELBOW);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW, SimpleOpenNI.SKEL_RIGHT_HAND);

	  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_TORSO);

	  context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_LEFT_HIP);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP, SimpleOpenNI.SKEL_LEFT_KNEE);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE, SimpleOpenNI.SKEL_LEFT_FOOT);

	  context.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_RIGHT_HIP);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP, SimpleOpenNI.SKEL_RIGHT_KNEE);
	  context.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE, SimpleOpenNI.SKEL_RIGHT_FOOT);  
	}





public void draw(){
	
  background(0);

  // update the cam
  context.update(); 
   
  // Vergroessern  
  //translate(0,0);
  //scale(zoomfactor);
  
  //depth=context.depthImage();
  //depth2=context.depthImage();
  
  // Depthmap zeigen
  //image(depth2,0,0);  
  
  image(context.depthImage(),0,0);
  
  if(context.isTrackingSkeleton(1)){
	  	 drawSkeleton(1);
	}
  
  

//
//	int[] userdepth=new int[width+height];
//  if (context.getNumberOfUsers() > 0) {
//	  context.getUserPixels(0,userdepth);
//  }
//  
//  
//  int userCount = context.getNumberOfUsers();
//  int[] userMap = null;
//  PVector userpoint = new PVector();  
//  
//  context.getCoM(userCount, userpoint );  
//  
//  if(userCount > 0){
//    userMap = context.getUsersPixels(SimpleOpenNI.USERS_ALL);
//  }
//  
//  int index=0;
//  
//    
//
//  int[]   depthMap = context.depthMap();
//  PVector realWorldPoint;
//  
//  
//  
//  for(int y=0;y < context.depthHeight();y++)
//  {
//    for(int x=0;x < context.depthWidth();x++)
//    {
//      index = x + y * context.depthWidth();
//      if(depthMap[index] > 0)
//      { 
//        // get the realworld points
//        realWorldPoint = context.depthMapRealWorld()[index];
//        
//        // check if there is a user
//        if(userMap != null && userMap[index] != 0)
//        {  // calc the user color
//        	color(255,0,0);
//           stroke(255); 
//        }
//        else
//          // default color
//          stroke(100); 
//        //point(realWorldPoint.x,realWorldPoint.y,realWorldPoint.z);
//      }
//    } 
//  }
  
  
  
  
  
  
  
  
  
  
  
  //context.getUserPixels();
  
//  depth.loadPixels();
//  backgroundImage.loadPixels();
//  
//   
//  for (int x = 0; x < depth.width; x ++ ) {
//	    for (int y = 0; y < depth.height; y ++ ) {
//	      int loc = x + y*depth.width; 
//	      
//	      int fgColor = depth.pixels[loc];  
//	      
//	      float r1 = red(fgColor);
//	      float g1 = green(fgColor);
//	      float b1 = blue(fgColor);
//	      
//	      float r2 = 255;
//	      float g2 = 255;
//	      float b2 = 255;
//	      float diff = dist(r1,g1,b1,r2,g2,b2);	      
//	      
//	      // Mappe
//	      float pmapped= map(brightness(depth.pixels[loc]),105,255,0,255);
//	      
//	      pmapped=brightness(depth.pixels[loc]);
//	      
//	      if (pmapped > threshold) {
//	      //if (brightness(depth.pixels[loc]) > threshold) {          
//	          //depth.pixels[loc]  = color(255,0,0);  // Rot
//	        }  else {
//	        	//depth.pixels[loc]  = color(0);    // Transparent
//	        	//depth.pixels[loc] = backgroundImage.pixels[loc];
//	        }
//	      
//	      //depth.pixels[loc] = userdepth[loc];
//	      
//	      
////	      if (diff > threshold) {
////	          // If so, display the foreground color
////	          depth.pixels[loc] = color(0,0,0);
////	        } else {
////	          // If not, display green
////	        	//depth.pixels[loc] = fgColor; // We could choose to replace the background pixels with something other than the color green!
////	        	//depth.pixels[loc] = color(255,255,255); // We could choose to replace the background pixels with something other than the color green!
////	        }	      
//	      
//	      //System.out.println(depth.pixels[loc]);	      
//
//	      point(x,y);
//	    }
//	    //System.out.println(highestvalue);	
//  }
//  
//  depth.updatePixels();  
//
//  //image(backgroundImage,0,0);
//  image(depth,0,0); 


}

//-----------------------------------------------------------------
//SimpleOpenNI events

void onNewUser(int userId)
{
System.out.println("onNewUser - userId: " + userId);
System.out.println("  start pose detection");

context.startPoseDetection("Psi",userId);
}

void onLostUser(int userId)
{
println("onLostUser - userId: " + userId);
}

void onStartCalibration(int userId)
{
println("onStartCalibration - userId: " + userId);
}

void onEndCalibration(int userId, boolean successfull)
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

void onStartPose(String pose,int userId)
{
println("onStartPose - userId: " + userId + ", pose: " + pose);
println(" stop pose detection");

context.stopPoseDetection(userId); 
context.requestCalibrationSkeleton(userId, true);

}

void onEndPose(String pose,int userId)
{
println("onEndPose - userId: " + userId + ", pose: " + pose);
}



}


