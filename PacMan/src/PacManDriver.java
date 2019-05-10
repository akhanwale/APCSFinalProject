import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PacManDriver extends JPanel implements ActionListener, KeyListener{
	JFrame f = new JFrame(); 

	Timer t = null;
	boolean setupPaint = true;

	static Piece[][] mapArr = null; // stores map data

	boolean shiftDown = true; // determines direction of particle movement

	int shiftDistance = -1; // determines amount of particle shift

	int pacManX = 84;
	int pacManXCenter = pacManX + 16;

	int pacManY = 124;
	int pacManYCenter = pacManY + 16;
	int pacManImg = 0;
	
	int blueX = 484;
	int blueY = 484;

	int screen_width;
	int screen_height;

	PacMan pac;
	Ghost blue;
	double pacManDir = 0;
	AffineTransform tx;
	AffineTransformOp op;

	boolean moveUp, moveDown, moveLeft, moveRight;
	boolean up, down, left, right;

	int cycles = 0;

	public PacManDriver(){
		scan(); // calls scan method to parse input file and initialize map

		pac = new PacMan(pacManX, pacManY, "Character");
		blue = new Ghost(blueX, blueY, "Character");
		// JFrame data
		f.setVisible(true);
		f.setTitle("Pac-Man");
		f.setSize(mapArr.length* 40, mapArr[0].length*40 + 100 ); // dependent on map size
		screen_width = mapArr.length* 40;
		screen_height = mapArr[0].length*40 + 100;
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Timer for update
		t = new Timer(40, this);
		t.start();
	}
	public void paint(Graphics g) {
		g.clearRect(0, 0, screen_width, screen_height);
		for(int i = 0; i < mapArr.length; i++) {
			for(int j = 0; j < mapArr[0].length; j++) {
				//mapArr[i][j].collision(pac);
				mapArr[i][j].setX(40*j);
				mapArr[i][j].setY(40*i + 80);
				g.drawImage(mapArr[i][j].getImage(), mapArr[i][j].getX() , mapArr[i][j].getY(), this);
				if(mapArr[i][j].hasFood()) {
					g.drawImage(mapArr[i][j].getFoodImage(), mapArr[i][j].getX() + (40- mapArr[i][j].getFoodImage().getWidth(this))/2, mapArr[i][j].getY() + (40- mapArr[i][j].getFoodImage().getHeight(this))/2 + shiftDistance, this);

				}
			}
		}

		// Rectangle at top for score, name etc.
		if(setupPaint) {
			g.fillRect(0, 0, 40*mapArr.length, 80);

			// User Information Display

			Font f1 = new Font("Courier", 0, 30);
			g.setColor(Color.WHITE);
			g.setFont(f1);
			g.drawString("Name: " + "Pickles", 50, 50);
			g.drawString("Score: 100", 400, 50);
		}

		// Map is a 2D array of images. Draws the correct image based on input file

		blue.setKey("(Right)");
		blue.setImage();
		g.drawImage(blue.getImage(), blue.getX(), blue.getY(), this);

		//Lives
		for(int i = 0; i < 3; i++) {
			Image heart = Toolkit.getDefaultToolkit().getImage("PacManImageh.png");
			g.drawImage(heart, 640 + 50*i , 15 , this);
		}

		if(left){
			pac.setKey("(Left)");
		}
		else if(right){
			pac.setKey("(Right)");
		}
		else if(down){
			pac.setKey("(Down)");
		}
		else if(up){
			pac.setKey("(Up)");
		}

		g.drawImage(pac.getImage(), pac.getX(), pac.getY(), this);

	}

	public static void scan() {
		File file = new File("Map1.txt");
		Scanner s;
		try {
			s = new Scanner(file);
			int rows = s.nextInt(); 
			int cols = s.nextInt();
			mapArr = new Piece[rows][cols];
			int iterator = 0;
			while(s.hasNextLine() && iterator != rows) {

				for(int i = 0; i < cols; i++) {
					if(s.hasNextInt()) {
						Piece p = new Piece(0, 0, Integer.toString(s.nextInt()));
						mapArr[iterator][i] = p;


					}else {
						Piece p = new Piece(0, 0, s.next());
						mapArr[iterator][i] = p;


					}
				}
				iterator++;

			}
			iterator = 0;
			while(s.hasNextLine() && iterator != rows) {


				for(int i = 0; i < cols; i++) {
					if(s.hasNextInt()) {
						mapArr[iterator][i].setFood(false);
						s.nextInt();

					}else {
						mapArr[iterator][i].setFood(true);
						mapArr[iterator][i].setFoodType(s.next());


					}
				}
				iterator++;
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update() {
		if(cycles % 4 == 0){
			if(shiftDown) {
				shiftDistance++;
			}else {
				shiftDistance--;
			}

			if(shiftDistance == -1) {
				shiftDown = true;
			}
			if(shiftDistance == 1) {
				shiftDown = false;
			}
			pac.setImage(!pac.getOpen());
		}
	}

	public void pacUpdate() {
		int[] pos = wheresPacMan();
		
		if(pac.getX() + 20 >= screen_width){
			pac.setX(5);	
		}

		if(pac.getX() <= -10){
			pac.setX(screen_width - 39);
		}

		if(left) pac.moveLeft();
		else if(right) pac.moveRight();
		else if(up) pac.moveUp();
		else if(down) pac.moveDown();

		if(pos[1] <= 0 || pos[1] >= mapArr.length - 1){
			mapArr[pos[0]][pos[1]].collision(pac, null, null, mapArr[pos[0]-1][pos[1]], mapArr[pos[0]+1][pos[1]]);
			mapArr[9][mapArr[0].length-1].setFood(false);
			mapArr[9][0].setFood(false);
		}
		else{
			if(mapArr[pos[0]][pos[1]].hasFood()){
				mapArr[pos[0]][pos[1]].setFood(false);
			}
			mapArr[pos[0]][pos[1]].collision(pac, mapArr[pos[0]][pos[1]-1], mapArr[pos[0]][pos[1]+1], mapArr[pos[0]-1][pos[1]], mapArr[pos[0]+1][pos[1]]);
		}
		
		repaint();
	}
	public static void main(String[] args) {
		PacManDriver h = new PacManDriver();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub



	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == 38) {
			//			pac.moveUp();
			up = true;
			down = false;
			left = false;
			right = false;
			pacUpdate();

		}else if(e.getKeyCode() == 39) {

			//			else{
			//				pac.moveRight();
			//			}
			up = false;
			down = false;
			left = false;
			right = true;
			pacUpdate();

		}else if(e.getKeyCode() == 40) {
			//			pac.moveDown();
			up = false;
			down = true;
			left = false;
			right = false;
			pacUpdate();

		}else if(e.getKeyCode() == 37) {

			//			else{
			//				pac.moveLeft();
			//			}		
			up = false;
			down = false;
			left = true;
			right = false;
			pacUpdate();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		repaint();
		update();
		pacUpdate();
		cycles ++;
		
		
	}
	public int[] wheresPacMan() {
		int row = ((pac.getCenterY()-80)/40);
		int col = (pac.getCenterX()/40);
		return new int[]{row, col}; 

	}
}