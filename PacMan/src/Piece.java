import java.awt.Image;
import java.awt.Toolkit;

public class Piece {

	private boolean topCol, botCol, leftCol, rightCol; // true if blocked
	private int x, y;
	private int width = 40, height = 40;
	private String type;
	private Image img;
	private Image foodImg;
	private boolean food;
	private String foodType;
	public Piece(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;
		setImageData();
	}

	public void setImageData() {
		setImage(type);
		if(type.equals("1") ||
				type.equals("7") ||
				type.equals("8") ||
				type.equals("b") ||
				type.equals("c") ||
				type.equals("d") ||
				type.equals("f")) {
			leftCol = true;
		}

		if(type.equals("1") ||
				type.equals("5") ||
				type.equals("9") ||
				type.equals("a") ||
				type.equals("d") ||
				type.equals("e") ||
				type.equals("f")) {
			rightCol = true;
		}
		if(type.equals("2") ||
				type.equals("4") ||
				type.equals("8") ||
				type.equals("9") ||
				type.equals("c") ||
				type.equals("d") ||
				type.equals("e")) {
			topCol = true;
		}
		if(type.equals("2") ||
				type.equals("6") ||
				type.equals("a") ||
				type.equals("b") ||
				type.equals("c") ||
				type.equals("e") ||
				type.equals("f")) {
			botCol = true;
		}

	}

	public boolean isTopCol() {
		return topCol;
	}

	public void setTopCol(boolean topCol) {
		this.topCol = topCol;
	}

	public boolean isBotCol() {
		return botCol;
	}

	public void setBotCol(boolean botCol) {
		this.botCol = botCol;
	}

	public boolean isLeftCol() {
		return leftCol;
	}

	public void setLeftCol(boolean leftCol) {
		this.leftCol = leftCol;
	}

	public boolean isRightCol() {
		return rightCol;
	}

	public void setRightCol(boolean rightCol) {
		this.rightCol = rightCol;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Image getImage() {
		return img;
	}

	public void setImage(String str) {
		img = Toolkit.getDefaultToolkit().getImage("PacManImage" + type + ".png");
	}
	public boolean hasFood() {
		return food;
	}
	public void setFood(boolean b) {
		food = b;
	}
	public Image getFoodImage() {
		return foodImg;
	}

	public void setFoodImage(String str) {
		foodImg = Toolkit.getDefaultToolkit().getImage("PacManImage" + foodType + ".png");
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
		setFoodImage(foodType);
	}
	
	public void collision(PacMan pac, Piece left, Piece right, Piece up, Piece down){
		if(left != null && (left.isRightCol() && pac.getX() < x)){
			pac.setX(x);
		}
		if(right != null && (right.isLeftCol() && (pac.getX() + pac.getWidth()) > x + width)){
			pac.setX(x + width - pac.getWidth());
		}
		if(up != null && (up.isBotCol() && pac.getY() < y)){
			pac.setY(y);
		}
		if(down != null && (down.isTopCol() && (pac.getY() + pac.getHeight()) > y + height)){
			pac.setY(y + height - pac.getHeight());
		}
	}

}
