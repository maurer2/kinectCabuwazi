package siluette;

import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class Player {
	public int id;
	public int letters;
	SimpleOpenNI context;
	public int color;
	public PImage face;
	public PVector com_x;
	public PVector leftHand;
	public PVector rightHand;
	public PVector head;
	
	
	public Player(SimpleOpenNI context, int userId, PImage face){
		this.id=userId;
		this.context=context;
		leftHand=new PVector();
		rightHand=new PVector();
		head=new PVector();
		this.face=face;
	}
	
	public void refreshJoints(){
		context.getJointPositionSkeleton(id, SimpleOpenNI.SKEL_RIGHT_HAND,rightHand);
		context.getJointPositionSkeleton(id, SimpleOpenNI.SKEL_LEFT_HAND,leftHand);
		context.getJointPositionSkeleton(id, SimpleOpenNI.SKEL_HEAD,head);	
		
		PVector temp=new PVector();
		context.convertRealWorldToProjective(head, temp);
		temp.mult(2);
		head=temp;
		
		temp= new PVector();
		
		context.convertRealWorldToProjective(leftHand, temp);
		temp.mult(2);
		leftHand=temp;
		
		temp= new PVector();
		
		context.convertRealWorldToProjective(rightHand, temp);
		temp.mult(2);
		rightHand=temp;
		
		
		
	}
	
	public PVector getCoM(){
		PVector result = new PVector();
		context.getCoM(id, result);
		
		return result;
	}
	
	public boolean isTracking(){
		return context.isTrackingSkeleton(id);
		
	}
	
	public PVector getJoint(int jointid){
		PVector pv= new PVector();
		context.getJointPositionSkeleton(id, jointid,pv);
		
		return pv;
	}
}
