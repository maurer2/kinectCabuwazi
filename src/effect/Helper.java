package effect;

import processing.core.PApplet;

public class Helper {
	public static float getY(float x, PApplet p){
		float y = getYUnmapped(x, p);
		
		float percent = p.height / 100 * 40;
		
		y = p.map(y, -1, 1, percent, p.height-percent);
		
		return y;
	}
	
	public static float getYUnmapped(float x, PApplet p){
		return (float) Math.sin(Math.toRadians(x));
	}
}
