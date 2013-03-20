package effect;

import processing.core.PApplet;

public class Particle {
	protected PApplet p;
	protected String edgeBehavior;
	protected float mass;
	protected float maxSpeed;
	protected float angle;
	protected int color;
	
	protected Vector2D pos;
	protected Vector2D velocity;
	
	
	public static String WRAP = "Wrap"; 
	public static String BOUNCE = "Bounce"; 
	
	
	public Particle(PApplet parent) {
		p = parent;
		pos = new Vector2D();
		velocity = new Vector2D();
		maxSpeed = 10;
		mass = 1;
		edgeBehavior = "";
		color = generateColor();
	}
	
	public void update()
	{
		velocity.truncate(maxSpeed);
		pos = pos.add(velocity);
		if(edgeBehavior == WRAP)
		{
			wrap();
		}
		if(edgeBehavior == BOUNCE)
		{
			bounce();
		}
		angle = velocity.getAngle();
	}
	
	public void draw()
	{
		p.pushMatrix();
		
		
		p.translate(pos.x, pos.y);
		p.rotate(angle);		
		p.noStroke();
		
		p.fill(color, 100);
		p.beginShape();
		p.vertex(10,0);
		p.vertex(-10,5);
		p.vertex(-10,-5);
		p.vertex(10,0);
		p.endShape();
		//p.rect(0, 0, 30, 5);
		p.popMatrix();
	}
	
	
	public void bounce()
	{
		if( pos.x<0 )
		{
			pos.x = 0;
			velocity.x *=-1;
		}
		else if( pos.x > p.width )
		{
			pos.x = p.width;
			velocity.x *=-1;
		}
		if( pos.y < 0)
		{
			pos.y = 0;
			velocity.y *= -1;
		}else if(pos.y > p.height)
		{
			pos.y = p.height;
			velocity.y *= -1; 
		}
	}
	
	public void wrap()
	{
		if( pos.x<0 )
		{
			pos.x = p.width;
		}
		else if( pos.x > p.width )
		{
			pos.x = 0;
		}
		if( pos.y < 0)
		{
			pos.y = p.height;
		}else if(pos.y > p.height)
		{
			pos.y = 0;
		}
	}
	
	
	protected int generateColor()
	{
		int rand = (int)(Math.random()*3);
		int col = p.color(255);
		switch(rand)
		{
			case 0:
				col = p.color(255,0,0);
			break;
			case 1:
				col = p.color(0,255,0);
			break;
			case 2:
				col = p.color(0,0,255);
			break;
		}
		return col;
	}
	
	public String getEdgeBehavior() {
		return edgeBehavior;
	}

	public void setEdgeBehavior(String edgeBehavior) {
		this.edgeBehavior = edgeBehavior;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Vector2D getPos() {
		return pos;
	}

	public void setPos(Vector2D pos) {
		this.pos = pos;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
