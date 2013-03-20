package siluette;

import processing.core.PImage;
import processing.core.PVector;

public class BoundingBox {
	public float x;
	public float y;
	public int width;
	public int height;

	
	public BoundingBox(float x, float y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;		
	}
	

	
	public boolean checkPoint(PVector pv){
		if (pv.x > x &&
				pv.y > y &&
				pv.x < x + width &&
					pv.y < y + height){
							
				return true; 
		}
		
		return false;
		
	}
	
}
