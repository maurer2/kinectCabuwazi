package effect;

import processing.core.PApplet;
import processing.core.PVector;

public class Vector2D {
	public float x;
	public float y;
	
	public Vector2D(float d, float e) {
		this.x = d;
		this.y = e;
	}
	
	public Vector2D() {
		x = 0;
		y = 0;
	}
	public Vector2D clone()
	{
		return new Vector2D(x,y);
	}
	
	public Vector2D zero()
	{
		x = 0;
		y = 0;
		return this;
	}
	
	public boolean isZero()
	{
		return x == 0 && y ==0;
	}
	
	public float getLength()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public void setLength(float newLength)
	{
		float a = getAngle();
		this.x = (float)Math.cos(a)*newLength;
		this.y = (float)Math.sin(a)*newLength;
	}
	
	public void setAngle( float newAngle )
	{
		float length = this.getLength();
		this.x = (float)Math.cos(newAngle)*length;
		this.y = (float)Math.sin(newAngle)*length;
	}
	
	public float getAngle()
	{
		return (float)Math.atan2((y), (x));
	}
	
	public Vector2D normalize()
	{
		if(getLength() == 0)
		{
			x = 1;
		}else{
			float length = getLength();
			x /= length;
			y /= length;
		}
		return this;
	}
	
	public boolean isNormalized()
	{
		return getLength() == 1;
	}
	
	public Vector2D truncate(float max)
	{
		setLength(Math.min(max, getLength()));
		return this;
	}
	
	public Vector2D reverse()
	{
		x = -x;
		y = -y;
		return this;
	}
	
	public float dotProd(Vector2D v2)
	{
		return (x*v2.x + y*v2.y);
	}
	
	public static float angleBetween(Vector2D v1, Vector2D v2)
	{
		if(!v1.isNormalized()) v1 = v1.clone().normalize();
		if(!v2.isNormalized()) v2 = v2.clone().normalize();
		return (float)Math.acos(v1.dotProd(v2));
	}
	
	public Vector2D perp()
	{
		return new Vector2D(-y,x);
	}
	
	public int sign(Vector2D v2)
	{
		return perp().dotProd(v2) < 0 ? -1: 1;
	}
	
	public float dist(Vector2D v2)
	{
		return PApplet.dist(x, y, v2.x, v2.y);
	}
	
	public Vector2D add(Vector2D v2)
	{
		return new Vector2D(x+v2.x, y+v2.y);
	}
	
	public Vector2D substract(Vector2D v2)
	{
		return new Vector2D(x-v2.x, y-v2.y);
	}
	
	public Vector2D multiply(float value)
	{
		return new Vector2D(x*value, y*value);
	}
	
	public Vector2D divide(float value)
	{
		return new Vector2D(x/value, y/value);
	}
	
	public String toString()
	{
		return "[Vector 2D (x: "+x+",y: "+y+")]";
	}
	

}
