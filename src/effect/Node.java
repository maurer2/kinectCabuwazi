package effect;


import processing.core.PVector;

class Node extends Vector2D {

	  float diameter = 0;

	  float minX = -Float.MAX_VALUE;
	  float maxX = Float.MAX_VALUE;
	  float minY = -Float.MAX_VALUE;
	  float maxY = Float.MAX_VALUE;
	  float minZ = -Float.MAX_VALUE;
	  float maxZ = Float.MAX_VALUE;

	  PVector velocity = new PVector();
	  PVector pVelocity = new PVector();
	  float maxVelocity = 10;

	  float damping = 0.5f;
	  float radius = 100;
	  float strength = -1;
	  float ramp = 1.0f;

	  int color = 1;
	  int opacity = 255;

	  // ------ constructors ------
	  Node() {
	  }

	  Node(float theX, float theY) {
	    x = theX;
	    y = theY;
	  }

	  void attract(Node[] theNodes) {
	    // attraction or repulsion part
	    for (int i = 0; i < theNodes.length; i++) {
	      Node otherNode = theNodes[i];
	      // stop when empty
	      if (otherNode == null) break;
	      // not with itself
	      if (otherNode == this) continue;

	      this.attract(otherNode);
	    }
	  }

	  void attract(Node theNode) {
	    float d = this.dist(theNode);

	    if (d > 0 && d < radius) {
	      float s = (float) Math.pow(d / radius, 1 / ramp);
	      float f = s * 9 * strength * (1 / (s + 1) + ((s - 3) / 4)) / d;
	      Vector2D df = this.substract(theNode);
	      df =  df.multiply(f);

	      theNode.velocity.x += df.x;
	      theNode.velocity.y += df.y;
	    }
	  }

	  // ------ update positions ------
	  void update() {
	    velocity.limit(maxVelocity);

	    pVelocity = velocity.get();

	     x += velocity.x;
	     y += velocity.y;

	    if (x < minX) {
	      x = minX - (x - minX);
	      velocity.x = -velocity.x;
	    }
	    if (x > maxX) {
	      x = maxX - (x - maxX);
	      velocity.x = -velocity.x;
	    }

	    if (y < minY) {
	      y = minY - (y - minY);
	      velocity.y = -velocity.y;
	    }
	    if (y > maxY) {
	      y = maxY - (y - maxY);
	      velocity.y = -velocity.y;
	    }

	    velocity.mult(1 - damping);
	    
	    opacity -=4;
	    
	    if(opacity < 0)
	    	opacity = 0;
	    
	    //color+=1;
	  }

	  void setBoundary(float theMinX, float theMinY, float theMaxX,
	  float theMaxY) {
	    this.minX = theMinX;
	    this.maxX = theMaxX;
	    this.minY = theMinY;
	    this.maxY = theMaxY;
	  }

	}
