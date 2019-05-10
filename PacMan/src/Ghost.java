import java.awt.Image;
import java.awt.Toolkit;


public class Ghost {

	private int x, y;
	private int width = 33, height = 33;
	private int orientation;
	private String color;
	private String imageKey = "(Right)";
	
	private Image img;
	//-1 == left, -2 == down, 1 == right, 2 == up
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}


	public Ghost(int _x, int _y, String _str){
		x = _x;
		y = _y;
		color = _str;
	}
	
	public void setImage() {
//		img = Toolkit.getDefaultToolkit().getImage(str + imageKey + "0.png");
		img = Toolkit.getDefaultToolkit().getImage("Ghost" + color + imageKey + ".png");
	}
	
	public void setKey(String key){
		imageKey = key;
	}

	public Image getImage() {
		return img;
	}

	public void moveLeft() {
		x -= 7;
	}
	
	public void moveRight() {
		x += 7;
	}
	
	public void moveDown() {
		y += 7;
	}
	
	public void moveUp() {
		y -= 7;
	}
	
	public int getCenterX(){
		return x + 16;
	}
	
	public int getCenterY(){
		return y + 16;
	}
}
