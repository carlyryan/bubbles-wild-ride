import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

public abstract class Character {
	/* The characters are the brave super hero, Bubbles, and the evil 
	 * villain Mojo Jojo who has cloned himself. 
	 *  
	 * The characters store their coordinates in the state of the game.
	 * Coords begin at the upper-left corner of the frame and also
	 * at upper-left hand corner of the photo.
	 */
	private int px; 
	private int py;

	private int width;
	private int height;

	private int vx;
	private int vy;
	/* this is the coefficient to convert coordinates of the character to the 
	 * array of the maze to check if it is a valid movement
	 */
	private int coordsToArray;


	public Character (int vx, int vy, int px, int py, int width, int height, 
			int sky) {
		this.vx = vx;
		this.vy = vy;
		this.px = px;
		this.py = py;
		this.width  = width;
		this.height = height;
		this.coordsToArray = sky/20;
	}

	/**** GETTERS ****/
	public int getPx() {
		return this.px;
	}

	public int getPy() {
		return this.py;
	}

	public int getVx() {
		return this.vx;
	}

	public int getVy() {
		return this.vy;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	/**** SETTERS ****/
	public void setPx(int px) {
		this.px = px;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

	/***MOVEMENT METHODS***/
	public boolean validMovement() {
		int[][] arr = MazeState.getMaze();
		int x = (int)((this.px + this.vx)/this.coordsToArray);
		int y = (int)((this.py + this.vy)/this.coordsToArray);
		return arr[x][y] == 1;
	}

	public void move() {
		if (validMovement()) {
			this.px += this.vx;
			this.py += this.vy;
		}
	}

	//because this method is only used for mojo, we do not want him moving
	//into the two dead end boxes in the lower central half. 
	public int[] nextOptions() {
		int[][] arr = MazeState.getMaze();
		int x = (int)((this.px)/this.coordsToArray);
		int y = (int)((this.py)/this.coordsToArray);
		//array, 0 if no, 1 if yes, east north west south
		int [] ewns = {0, 0, 0, 0};
		//box1 and 2 are the tiles right below the dead ends that mojo cannot enter
		boolean box1 = x == 8 && y == 12;
		boolean box2 = x == 11 && y == 12;
		if (x > 0 && y > 0 && x < 19 && y < 19 && !box1 && !box2) {
			if (arr[x + 1][y] == 1) {
				ewns[0] = 1;
			}
			if (arr[x - 1][y] == 1) {
				ewns[1] = 1;
			}
			if (arr[x][y - 1] == 1) {
				ewns[2] = 1;
			}
			if (arr[x][y + 1] == 1) {
				ewns[3] = 1;
			}
		//in these tiles, mojo can move east or west but CANNOT go north although it
		//is a pair of indices of the maze array that is technically valid
		} if (box1 || box2) {
			ewns[0] = 1;
			ewns[1] = 1;
		}
		return ewns;
	}

	public static boolean isDead (Character c1, Character c2){
		int c1NextX = c1.px + c1.vx;
		int c1NextY = c1.py + c1.vy;
		int c2NextX = c2.px + c2.vx;
		int c2NextY = c2.py + c2.vy;

		boolean placesEqual = (c1NextX == c2NextX && c1NextY == c2NextY);
		boolean swapPlaces = (c1NextX == c2.px && c1NextY == c2.py) 
				&& (c1.px == c2NextX && c1.py == c2NextY);
		return placesEqual || swapPlaces;
	}

	/***DRAW METHOD DEFINED PER CHARACTER***/
	public abstract void draw(Graphics g);

}
