package effect;

import processing.core.PApplet;

public class Boom {
	
	private PApplet p;
	private float x;
	private float y;
	
	private Node[] nodes = new Node[100];
	
	public boolean isAlive(){
		return nodes[0].opacity != 0;
	}
	
	public Boom(PApplet p, float x, float y){
		this.p = p;
		this.x = x;
		this.y = y;
		
		 for (int i = 0 ; i < nodes.length; i++) {
			    nodes[i] = new Node(p.random(-1, 1), p.random(-1, 1));
			    nodes[i].color = p.color(p.random(150, 250),p.random(150, 220),p.random(50, 20));
			    //nodes[i].setBoundary(5, 5, width-5, height-5);
			  } 
		
	}
	
	public void draw(){
		p.pushMatrix();
		p.noStroke();
		p.translate(x, y);
		
		
		  for (int i = 0 ; i < nodes.length; i++) {
		    nodes[i].attract(nodes);
		  } 

		  for (int i = 0 ; i < nodes.length; i++) {
		    nodes[i].update();
		  } 

		  for (int i = 0 ; i < nodes.length; i++) {
			p.fill(nodes[i].color, nodes[i].opacity);
			//p.fill(255,0,0);
		    p.ellipse(nodes[i].x, nodes[i].y, 10, 10);
		  }
		  
		 p.popMatrix();
	}
}
