package siluette;

import processing.core.PImage;

public class Item {
	public PImage img;
	public BoundingBox box;
	public char letter;
	public float velocity;
	public float rotation;
	public boolean isRotationEnabled;
	
	public Item(int x, int y, PImage img, char letter, float velocity, float rotation, boolean isRotationEnabled) {
		this.box = new BoundingBox(x-img.width/2,y-img.height/2,img.width, img.height);		
		this.letter=letter;
		this.velocity=velocity;
		this.img=img;
		this.rotation=rotation;
		this.isRotationEnabled=isRotationEnabled;
		
	}
	
	public void nextFrame(){
		if (isRotationEnabled == true){
			rotation+=0.1f;
		}
		velocity=velocity*1.03f;
		box.y+= velocity;
		
		
	}

}
