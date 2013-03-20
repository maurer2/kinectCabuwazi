package siluette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import effect.Boom;

public class StickFigure extends PApplet {

	SimpleOpenNI context;
	Random random = new Random();
	int[] userColors = { color(255, 0, 0, 100), color(0, 255, 0, 100), color(0, 0, 255, 100),
			color(255, 255, 0, 100), color(0, 255, 255, 100) };

	PImage canvas;
	PImage background;
	PImage handl;
	PImage handr;
	PImage face;
	PImage face2;
	PImage player1win;
	PImage player2win;

	int res_x = 1280;
	int res_y = 720;
	int max_trans_x = 30;
	int max_trans_y = 17;
	int cur_trans_x;
	float lightBoxCounter = 1;

	ArrayList<Player> players;
	ArrayList<Item> items;
	ArrayList<Boom> booms;

	int max = 640 * 480;
	int maxhd = 1280 * 720;

	// Haende
	PVector rightHand = new PVector();
	PVector rightHand2 = new PVector();
	PVector leftHand = new PVector();
	PVector leftHand2 = new PVector();

	String word = "cabuwazi";
	String word_filtered = "cabuwzi";
	HashMap<Character, PImage> word_items;
	HashMap<Character, PImage> word_items2;
	GameState gs;

	// Audio
	AudioPlayer applause;
	AudioPlayer explosion;
	AudioPlayer ambient;
	AudioPlayer failure;
	Minim minim;

	public StickFigure() {
		items = new ArrayList<Item>();
		players = new ArrayList<Player>();
		booms = new ArrayList<Boom>();

		word_items = new HashMap<Character, PImage>();
		word_items2 = new HashMap<Character, PImage>();

		word_items.put('c', loadImage("sterne/c2.png"));
		word_items.put('a', loadImage("sterne/a2.png"));
		word_items.put('b', loadImage("sterne/b2.png"));
		word_items.put('u', loadImage("sterne/u2.png"));
		word_items.put('w', loadImage("sterne/w2.png"));
		word_items.put('z', loadImage("sterne/z2.png"));
		word_items.put('i', loadImage("sterne/i2.png"));

		word_items2.put('c', loadImage("sterne/c.png"));
		word_items2.put('a', loadImage("sterne/a.png"));
		word_items2.put('b', loadImage("sterne/b.png"));
		word_items2.put('u', loadImage("sterne/u.png"));
		word_items2.put('w', loadImage("sterne/w.png"));
		word_items2.put('z', loadImage("sterne/z.png"));
		word_items2.put('i', loadImage("sterne/i.png"));
	}

	public void drawBooms() {
		// draw booms
		for (int i = 0; i < booms.size(); i++) {
			booms.get(i).draw();
		}

		// clear booms
		for (int i = 0; i < booms.size(); i++) {
			if (!booms.get(i).isAlive())
				booms.remove(i);
		}

	}

	public void drawletters(int x, int y, Player pl) {

		for (int i = 0; i < word.length(); i++) {
			PImage temp;
			if (i < pl.letters)
				temp = word_items2.get(word.charAt(i));
			else
				temp = word_items.get(word.charAt(i));
			image(temp, x, y);
			x = x + temp.width;
		}
	}

	public void drawletters2(int x, int y, Player pl) {

	}

	public void setup() {
		// context = new SimpleOpenNI(this);
		context = new SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_MULTI_THREADED);

		context.enableDepth();
		// context.enableRGB();
		context.enableHands();
		// context.enableGesture();

		// enable skeleton generation for all joints
		context.enableUser(SimpleOpenNI.SKEL_PROFILE_HEAD_HANDS);
		context.setMirror(true);

		// add focus gestures
		// context.addGesture("Wave");
		// context.addGesture("Click");
		// context.addGesture("RaiseHand");

		// Enable Scene
		context.enableScene(640, 480, 30);
		// context.enableScene(700,720,60);

		stroke(255);
		strokeWeight(12);
		noFill();
		// smooth();
		// size(context.depthWidth(), context.depthHeight());
		frameRate(30);
		size(1280, 720);

		// Images
		background = loadImage("sterne/backgroundhd_paralaxe.png");
		face = loadImage("sterne/head1.png");
		face2 = loadImage("sterne/head2.png");
		handl = loadImage("sterne/handl.png");
		handr = loadImage("sterne/handr.png");
		player1win = loadImage("sterne/win1.png");
		player2win = loadImage("sterne/win2.png");
		gs = GameState.Game;

		// Sound

		minim = new Minim(this);
		explosion = minim.loadFile("sounds/explosion.mp3");
		failure = minim.loadFile("sounds/wrongletter.mp3");

		applause = minim.loadFile("sounds/applause.mp3");
		/*
		 * //ambient=minim.loadFile("sounds/ambient.mp3"); //ambient.loop();
		 */

	}

	public void draw() {
		// background(0);
		// update the cam
		context.update();
		// ambient.play();

		// Paralaxscrolling
		if (players.size() > 0) {

			PVector pos = players.get(0).getCoM();
			pos.mult(2);

			float x = (((float) width / 2) - pos.x);
			float x2 = map(x, 0, width / 2, 0, max_trans_x);

			cur_trans_x = (int) x2;

			// if (pos.x <= width/2) {
			image(background, -max_trans_x - cur_trans_x, 0);
			// } else {
			// image(background, -max_trans_x -cur_trans_x, 0);
			// }
		} else {
			image(background, -max_trans_x, -max_trans_y);
		}

		// Draw Figure
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (p.isTracking()) {
				p.refreshJoints();
				image(players.get(i).face, players.get(i).head.x - face.width / 2,
						players.get(i).head.y - face.height / 2);
				image(handl, players.get(i).leftHand.x - handl.width / 2, players.get(i).leftHand.y
						- handl.height / 2);
				image(handr, players.get(i).rightHand.x - handr.width / 2,
						players.get(i).rightHand.y - handr.height / 2);
			}
		}

		// Kolisionsabfrage
		for (int i = 0; i < items.size(); i++) {

			// Rotation
			float x = items.get(i).box.x + items.get(i).box.width / 2;
			float y = items.get(i).box.y + items.get(i).box.height / 2;
			// rect(items.get(i).box.x,items.get(i).box.y,items.get(i).box.width,items.get(i).box.height);

			pushMatrix();
			translate(x, y);
			float rotation = items.get(i).rotation;
			rotate(rotation);
			// image(items.get(i).img,items.get(i).box.x,items.get(i).box.y);
			int x2 = items.get(i).img.width / 2;
			int y2 = items.get(i).img.height / 2;
			image(items.get(i).img, -x2, -y2);
			popMatrix();

			// Next Frame
			items.get(i).nextFrame();

			if (gs == GameState.Game) {
				for (int i2 = 0; i2 < players.size(); i2++) {

					if (players.get(i2).isTracking()) {
						context.getJointPositionSkeleton(players.get(i2).id,
								SimpleOpenNI.SKEL_RIGHT_HAND, rightHand);
						context.getJointPositionSkeleton(players.get(i2).id,
								SimpleOpenNI.SKEL_LEFT_HAND, leftHand);
						context.convertRealWorldToProjective(leftHand, leftHand2);
						context.convertRealWorldToProjective(rightHand, rightHand2);

						leftHand2.mult(2);
						rightHand2.mult(2);

						if (items.get(i).box.checkPoint(leftHand2)
								|| items.get(i).box.checkPoint(rightHand2)) {
							char tempchar = word.charAt(players.get(i2).letters);
							if (items.get(i).letter == tempchar) {
								if (players.get(i2).letters + 1 == word.length()) {
									if (i2 == 0)
										setGameState(GameState.EndP1);
									if (i2 == 1)
										setGameState(GameState.EndP2);

								}
								booms.add(new Boom(this, items.get(i).box.x, items.get(i).box.y));
								explosion.rewind();
								explosion.play();
								items.remove(i--);
								players.get(i2).letters++;

							} else { // Falscher Buchstabe eingesammelt

								// Score herunterfallen
								int bszahl = players.get(i2).letters;
								int bspos_x = 50;
								int bspos_y = 30;

								if (i2 == 0) {
									bspos_x = 50;

								}

								if (i2 == 1) {

									bspos_x = width - 50
											- (word.length() * word_items.get('c').width);

								}

								for (int k = 0; k < bszahl; k++) {
									char bs_char = word.charAt(k);
									PImage bs = word_items.get(bs_char);
									Item it = new Item(bspos_x + bs.width / 2, bspos_y + bs.height
											/ 2, bs, bs_char, random(15, 25), 0f, false);
									items.add(it);
									bspos_x += bs.width;
								}

								players.get(i2).letters = 0;
								items.remove(i--);
								failure.rewind();
								failure.play();

							}

							break;
						}
					}

				}
			}
		}

		// Entferne Items die außerhalb des Canvas sind
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).box.y > height) {
				items.remove(i--);
			}
		}

		if (gs == GameState.Game) {
			// Füge neue Items hinzu

			for (int i = 0; i < 4 - items.size(); i++) {
				String word_f_special = word_filtered;

				for (int k = 0; k < players.size(); k++) {
					for (int z = 0; z < 6; z++) {
						word_f_special += word.charAt(players.get(k).letters);
					}
				}

				char letter = word_f_special.charAt(random.nextInt(word_f_special.length()));

				PImage img = word_items2.get(letter);
				// items.add(new Item(random.nextInt(width-img.width),
				// -img.height, img, letter,
				// Math.max(4f,(random.nextFloat()*random.nextInt(15) )),
				// random.nextInt(360)));
				items.add(new Item(random.nextInt(width - img.width), -img.height, img, letter,
						random(2, 8), random.nextInt(360), true));

			}
		}

		// Alphabetscore zeichnen
		if (gs != GameState.Intro) {
			if (players.size() > 0) {
				int margin = 50;
				int margin2 = 100;
				drawletters(margin, 30, players.get(0));

				if (players.size() > 1) {
					drawletters(width - margin2 - (word.length() * word_items.get('c').width), 30,
							players.get(1));
				}

			}

			drawBooms();

			// Gewinnbenachrichtigung
			if (gs == GameState.EndP1 || gs == GameState.EndP2) {

				fill(255, 255, 255, lightBoxCounter);
				// fill(0,0,0,lightBoxCounter);
				noStroke();
				rect(0, 0, width, height);
				fill(0);
				color(255);
				applause.play();

				if (gs == GameState.EndP1) {
					// textSize(30);
					image(player1win, (width - player1win.width) / 2,
							(height - player1win.height) / 2 - 50);
					// text("Spieler 1 gewinnt",300,400);
				}

				if (gs == GameState.EndP2) {
					// textSize(30);
					// text("Spieler 2 gewinnt", 300,400);
					image(player2win, (width - player2win.width) / 2,
							(height - player2win.height) / 2 - 50);
				}

				lightBoxCounter *= 1.15f;
				if (lightBoxCounter > 255) {
					lightBoxCounter = 255;
				}
			}

		}

	}

	public void setGameState(GameState gs2) {
		gs = gs2;
	}

	public void stop() {
		// ambient.close();
		// applause.close();

		explosion.close();
		failure.close();
		minim.stop();
		super.stop();
	}

	// SimpleOpenNI events

	public void onNewUser(int userId) {
		println("onNewUser - userId: " + userId);
		println("  start pose detection");

		if (players.size() >= 2)
			return;

		context.startPoseDetection("Psi", userId);

		PImage currentFace;
		if (players.size() == 0) {
			currentFace = face;
		} else {
			currentFace = face2;

		}

		Player player = new Player(context, userId, currentFace);

		player.color = userColors[random.nextInt(userColors.length)];

		// if (players.size() > 1)
		// player.color=userColors[random.nextInt(userColors.length)];
		players.add(player);

	}

	public void onLostUser(int userId) {
		println("onLostUser - userId: " + userId);

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).id == userId) {
				players.remove(userId);
				return;
			}
		}

	}

	public void onStartCalibration(int userId) {
		println("onStartCalibration - userId: " + userId);
	}

	public void onEndCalibration(int userId, boolean successfull) {
		println("onEndCalibration - userId: " + userId + ", successfull: " + successfull);

		if (successfull) {
			println("  User calibrated !!!");
			context.startTrackingSkeleton(userId);
		} else {
			println("  Failed to calibrate user !!!");
			println("  Start pose detection");
			context.startPoseDetection("Psi", userId);
		}
	}

	public void onStartPose(String pose, int userId) {
		println("onStartPose - userId: " + userId + ", pose: " + pose);
		println(" stop pose detection");

		context.stopPoseDetection(userId);
		context.requestCalibrationSkeleton(userId, true);

	}

	public void onEndPose(String pose, int userId) {
		println("onEndPose - userId: " + userId + ", pose: " + pose);
	}

}
