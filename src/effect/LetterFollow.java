package effect;
import java.util.ArrayList;
import java.util.LinkedList;


import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


public class LetterFollow extends PApplet{
	public int bounce=0;
	public int breite=0;
	public int max=650;
	protected SteeredLetter particle;
	protected SteeredLetter particle2;
	protected LinkedList<Vector2D> path = new LinkedList<Vector2D>();
	protected LinkedList<Vector2D> path2 = new LinkedList<Vector2D>();
	
	private ArrayList<SteeredLetter> letters;

	private ArrayList<Boom> booms;
	
	//private MovieMaker mm;

	PFont font;
	
	private boolean boom = true;
	
	public void setup(){
		size(1280,720,P2D);
		frameRate(60);
		smooth();
		noStroke();

		letters = new ArrayList<SteeredLetter>();
		booms = new ArrayList<Boom>();		
		//for (int i=0; i<210;i+=30) { 
			letters.add( new SteeredLetter(this, "sterne/a.png", 0, "C"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 50, "A"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 100, "B"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 150, "U"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 200, "W"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 250, "A"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 300, "Z"));
			letters.add( new SteeredLetter(this, "sterne/a.png", 350, "I"));
		//}
			
			//mm = new MovieMaker(this, width, height, "drawing.mov",
                    //60, MovieMaker.MOTION_JPEG_A, MovieMaker.HIGH);

	}
	
	public void draw(){	
		background(255);
		int rand=(int) random(0,letters.size());
		SteeredLetter randomLetter=letters.get(rand);
		SteeredLetter firstLetter = letters.get(0);
		float tempY = Helper.getYUnmapped(firstLetter.x_pos, this);
				
		
		if(tempY >= 1 || tempY <= -1){
			booms.add(new Boom(this, randomLetter.x_pos, Helper.getY(randomLetter.x_pos, this)));
		}
		
		//draw booms
		for(int i = 0; i< booms.size(); i++){
			booms.get(i).draw();
		}
		
		//clear booms
		for(int i = 0; i< booms.size(); i++){
			if(!booms.get(i).isAlive())
				booms.remove(i);
		}
		
		//draw letter
		for(int i = 0; i < letters.size(); i++){
			letters.get(i).update();
			letters.get(i).draw();
		}
		
		float oldY = 0;
		
		//draw letters
		for(int i= 1; i < width; i++){
			float y = Helper.getY(i, this);
			color(100);
			stroke(1);
			//line(i, y, i-1, oldY);			
			noStroke();			
			oldY = y;
		}		
		
		//mm.addFrame();

	}
	

}