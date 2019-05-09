import java.awt.Image;
import java.awt.Toolkit;


public class PacMan {

	private int x, y;
	private int width = 33, height = 33;
	private int orientation;
	private String str;
	private boolean open;
	
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

	public void setOpen(boolean open) {
		this.open = open;
	}

	public PacMan(int _x, int _y, String _str){
		x = _x;
		y = _y;
		str = _str;
	}
	
	public void setImage(boolean _open) {
		open = _open;
		if(open) img = Toolkit.getDefaultToolkit().getImage(str + "0.png");
		else img = Toolkit.getDefaultToolkit().getImage(str + "1.png");
	}

	public boolean getOpen(){
		return open;
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
