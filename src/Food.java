import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Food {
    public static final String IMG_FILE = "files/heart.png";
    private static BufferedImage img;
	private int[][] mazeStateFood;
	private int foodCount;
	private int coordsToArray;

	public Food (int sky) {
		this.coordsToArray = sky/20;
		foodCount = 0;
		mazeStateFood = MazeState.getMaze();
		for (int x = 1; x < 19; x++){
			for (int y = 1; y < 19; y++){
				//is this path or sky ?
				if (mazeStateFood[x][y] == 1) {
					//if it is path, place food
					mazeStateFood[x][y] = 2;
					//set it equal to 2 : there is a piece of food
				}
			}
		}
		try {
	    	 img = ImageIO.read(new File(IMG_FILE));
	     } catch (IOException e) {
           System.out.println("Internal Error:" + e.getMessage());
       }
	}

	public void eatFood(Character c) {
		int x = (int)((c.getPx())/coordsToArray);
		int y = (int)((c.getPy())/coordsToArray);
		if (mazeStateFood[x][y] == 2) {
			mazeStateFood[x][y] = 1;
			foodCount ++;
		}
	}

	public int getCount(){
		return foodCount;
	}

	public void draw(Graphics g) {
		for (int x = 1; x < 19; x++){
			for (int y = 1; y < 19; y++){
				if (mazeStateFood[x][y] == 2) {
					int pX = (x * coordsToArray);
					int pY = (y * coordsToArray);
					g.drawImage(img, pX, pY, coordsToArray, coordsToArray, null);
				}
			}
		}
	}
}

