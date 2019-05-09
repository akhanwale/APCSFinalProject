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
	int screen_height;
	int screen_width;

	static Piece[][] mapArr = null; // stores map data

	boolean shiftDown = true; // determines direction of particle movement

	int shiftDistance = -1; // determines amount of particle shift

	int pacManX = 84;
	int pacManXCenter = pacManX + 16;

	int pacManY = 140-16;
	int pacManYCenter = pacManY + 16;
	int pacManImg = 0; 
	double pacManDir = 0; 
	int score = 0; 

	public PacManDriver(){
		scan(); // calls scan method to parse input file and initialize map

		// JFrame data
		f.setVisible(true);
		f.setTitle("Pac-Man");
		screen_height = mapArr[0].length*40 + 100;
		screen_width = mapArr.length* 40;
		f.setSize(screen_width, screen_height ); // dependent on map size
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Timer for update
		t = new Timer(175, this);
		t.start();
	}
	public void paint(Graphics g) {

		for(int i = 0; i < mapArr.length; i++) {
			for(int j = 0; j < mapArr[0].length; j++) {
				mapArr[i][j].setX(40*j);
				mapArr[i][j].setY(40*i + 80);
				g.drawImage(mapArr[i][j].getImage(), mapArr[i][j].getX() , mapArr[i][j].getY(), this);
				if(mapArr[i][j].hasFood()) {
					g.drawImage(mapArr[i][j].getFoodImage(), mapArr[i][j].getX() + (40- mapArr[i][j].getFoodImage().getWidth(this))/2, mapArr[i][j].getY() + (40- mapArr[i][j].getFoodImage().getHeight(this))/2 + shiftDistance, this);

				}
			}
		}

		// Rectangle at top for score, name etc.

			g.fillRect(0, 0, 40*mapArr.length, 80);

			// User Information Display

			Font f1 = new Font("Courier", 0, 30);
			g.setColor(Color.WHITE);
			g.setFont(f1);
			g.drawString("Name: " + "Pickles", 50, 50);
			g.drawString("Score: " + Integer.toString(score), 400, 50);
		

		// Map is a 2D array of images. Draws the correct image based on input file



		//Lives
		for(int i = 0; i < 3; i++) {
			Image heart = Toolkit.getDefaultToolkit().getImage("PacManImageh.png");
			g.drawImage(heart, 640 + 50*i , 15 , this);
		}
		
		Image pacMan =  Toolkit.getDefaultToolkit().getImage("Character" + Integer.toString(pacManImg) + ".png");

		g.drawImage(pacMan, pacManX, pacManY, this);
		pacManXCenter = pacManX + 16;
		pacManYCenter = pacManY + 16;
		
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
		if(pacManImg == 0) {
			pacManImg = 1;
		}else {
			pacManImg = 0;
		}
	}
	public void pacUpdate() {
		if(wheresPacMan().hasFood()) {
			wheresPacMan().setFood(false);
			score+=10;
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
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 38) {
			if(wheresPacMan().isTopCol()) {
			}else {
				pacManY-= 7;
			}
			
			pacUpdate();
		}else if(e.getKeyCode() == 39) {
			if(wheresPacMan().isRightCol()) {
				
			}else if(pacManX + 33 >= screen_width) {
				pacManX = 5;
			}else {
				pacManX+=7;
			}
			pacUpdate();
		}else if(e.getKeyCode() == 40) {
			if(wheresPacMan().isBotCol()) {
				
			}else {
				pacManY+= 7;
			}
			
			pacUpdate();

		}else if(e.getKeyCode() == 37) {
			if(wheresPacMan().isLeftCol()) {
				
			}else
			if(pacManX <= 0) {
				pacManX = screen_width-39;
			}else {
				pacManX-=7;
			}
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


	}
	public Piece wheresPacMan() {
		int row = ((pacManYCenter-80)/40);
		int col = (pacManXCenter/40);
		Piece p = mapArr[row][col];
		System.out.println(Integer.toString(row) +  " " + Integer.toString(col));
		return p; 
	}
}