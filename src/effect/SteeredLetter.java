package effect;

import java.util.LinkedList;
import processing.core.*;

public class SteeredLetter extends Particle{
	
	protected Vector2D steeringForce;
	protected Vector2D targetPosition;
	protected float maxForce;
	protected float arrivalThreshold;
	
	protected float wanderDistance;
	protected float wanderRadius;
	protected float wanderRange;
	protected float wanderAngle;
	
	protected int pathIndex;
	protected float pathThreshold;
	
	int x_pos = 0;
	int x_velocity = 1;
	
	PImage letter;
	PFont font;
	String text;

	
	public SteeredLetter(PApplet parent, String path, int pos, String bs) {
		// TODO Auto-generated constructor stub
		super(parent);
		steeringForce = new Vector2D();
		maxForce = 1;
		arrivalThreshold = 100;
		wanderAngle = 0;
		wanderDistance = 10;
		wanderRadius = 5;
		wanderRange = 5;
		
		pathIndex = 0;
		pathThreshold = 20;		
		letter = p.loadImage(path, "jpg");	
		
		font= p.createFont("Arial", 50);
		//font =p.loadFont("DoloresBlack.otf");
		
		x_pos = pos;
		text=bs;
		
	}
	
	public Vector2D getTarget() {
		return targetPosition;
	}
	public void setTarget(Vector2D target) {
		this.targetPosition = target;
	}
	
	public float getMaxForce() {
		return maxForce;
	}
	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}
	
	public float getArrivalThreshold() {
		return arrivalThreshold;
	}
	public void setArrivalThreshold(float arrivalThreshold) {
		this.arrivalThreshold = arrivalThreshold;
	}
	
	public float getWanderDistance() {
		return wanderDistance;
	}

	public void setWanderDistance(float wanderDistance) {
		this.wanderDistance = wanderDistance;
	}

	public float getWanderRadius() {
		return wanderRadius;
	}

	public void setWanderRadius(float wanderRadius) {
		this.wanderRadius = wanderRadius;
	}

	public float getWanderRange() {
		return wanderRange;
	}

	public void setWanderRange(float wanderRange) {
		this.wanderRange = wanderRange;
	}
	
	public void seek(Vector2D target)
	{
		Vector2D desiredVelocity = target.substract(pos);
		//desiredVelocity.truncate(maxSpeed);
		desiredVelocity.normalize();
		desiredVelocity = desiredVelocity.multiply(maxSpeed);
		Vector2D force = desiredVelocity.substract(velocity);
		steeringForce = steeringForce.add(force);
	}
	
	public void flee(Vector2D target)
	{
		Vector2D desiredVelocity = target.substract(pos);
		if(desiredVelocity.getLength() > 50) return;
		desiredVelocity.normalize();
		desiredVelocity = desiredVelocity.multiply(maxSpeed);
		Vector2D force = desiredVelocity.substract(velocity);
		steeringForce = steeringForce.substract(force);
	}
	
	
	public void arrive(Vector2D target)
	{
		Vector2D desiredVelocity = target.substract(pos);
		desiredVelocity.normalize();
		
		float dist = p.dist(target.x, target.y, pos.x, pos.y);
		
		if(dist > arrivalThreshold)
		{
			desiredVelocity = desiredVelocity.multiply(maxSpeed);
		}else{
			desiredVelocity = desiredVelocity.multiply(maxSpeed * (dist/arrivalThreshold));
		}
		
		Vector2D force = desiredVelocity.substract(velocity);
		steeringForce = steeringForce.add(force);
	}
	
	
	public void pursue(Particle target)
	{
		float lookAheadTime = pos.dist(target.pos) / maxSpeed;
		Vector2D predictedTarget = target.pos.add(target.velocity.multiply(lookAheadTime));
		seek(predictedTarget);
	}
	
	public void evade(Particle target)
	{
		float lookAheadTime = pos.dist(target.pos) / maxSpeed;
		Vector2D predictedTarget = target.pos.add(target.velocity.multiply(lookAheadTime));
		flee(predictedTarget);
	}
	
	public void wander()
	{
		Vector2D center = velocity.clone().normalize().multiply(wanderDistance);
		Vector2D offset = new Vector2D();
		offset.setLength(wanderRadius);
		offset.setAngle(wanderAngle);
		wanderAngle += p.radians(p.random(wanderRange, -wanderRange));
		Vector2D force = center.add(offset);
		steeringForce = steeringForce.add(force);
	}
	
	public void followPath( LinkedList<Vector2D> path, Boolean loop)
	{
		Vector2D waypoint = path.get(pathIndex);
		if(pos.dist(waypoint) < pathThreshold)
		{
			if(pathIndex >= path.size() -1)
			{
				if(loop)
				{
					pathIndex = 0;
				}
			}else{
				pathIndex ++;
			}
		}
		//p.println("path index: " + pathIndex);
		if(pathIndex >= path.size() -1 && !loop)
		{
			arrive(waypoint);
		}else{
			//p.println("Seek: " + waypoint);
			seek(waypoint);
		}
	}
	
	public void update()
	{
		x_pos += x_velocity;
		
		steeringForce.truncate(maxForce);
		steeringForce = steeringForce.divide(mass);
		velocity = velocity.add(steeringForce);
		steeringForce = new Vector2D();
		super.update();
	}
	
	public void draw()	{	
		p.pushMatrix();
		p.translate(x_pos, Helper.getY(x_pos, p));
		p.rotate((float) Math.sin(Math.toRadians(x_pos+90)));
		//p.image(letter, -letter.width/2, -letter.height/2);
		//p.image(letter, 0, 0);
		
		// Text	
		float rot=(float) Math.abs(Math.sin(Math.toRadians(x_pos))*100);
		p.fill(0);		
		p.textFont(font);
		
		if (rot<40) {
			rot=40;
		}
		p.textSize(rot);
		//System.out.println(rot);
		
		//p.textSize(80);		
		//p.textAlign(1);
		
		p.text(text,0,letter.height);		
		p.popMatrix();
	}
}
