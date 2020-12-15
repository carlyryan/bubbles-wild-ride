import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MojoJojo extends Character{
    public static final String IMG_FILE = "files/mojo1.jpg";
	private static BufferedImage img;
	
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
	private int direction;
	// 0 is east
	// 1 is west
	// 2 is north
	// 3 is south
	public static final int VEL = 35;

	public MojoJojo (int sky) {
		super(INIT_VEL_X, INIT_VEL_Y, (int)(9*sky/20), (int)(9*sky/20), 
				(int)(sky/20), (int)(sky/20), sky);
		direction = 1;
		try {
			img = ImageIO.read(new File(IMG_FILE));
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void move() {
		int [] ewns = this.nextOptions();
		int origDir = direction;
		boolean valid = false;
		while (!valid) {
			int rand = (int) (Math.random() * 4.0);
			if (ewns[rand] == 1 && origDir != dirOpposite(rand)) {
				this.changeDir(rand);
			} else if (ewns[(rand + 1) % 4] == 1 && origDir != dirOpposite((rand + 1) % 4)) {
				this.changeDir((rand + 1) % 4);
			} else if (ewns[(rand + 2) % 4] == 1 && origDir != dirOpposite((rand + 2) % 4)) {
				this.changeDir((rand + 2) % 4);
			} else if (ewns[(rand + 3) % 4] == 1 && origDir != dirOpposite((rand + 3) % 4)) {
				this.changeDir((rand + 3) % 4);
			}
			valid = this.validMovement();
		}
		this.setPx (this.getPx() + this.getVx());
		this.setPy (this.getPy() + this.getVy());
	}

	// 0 is east
	// 1 is west
	// 2 is north
	// 3 is south
	private void changeDir(int d){
		direction = d;
		if (d == 0) { //east
			this.setVx(VEL);
			this.setVy(0);
		} else if (d == 1) { //west
			this.setVx(-VEL);
			this.setVy(0);
		} else if (d == 2) { //north
			this.setVx(0);
			this.setVy(-VEL);
		} else { //south
			this.setVx(0);
			this.setVy(VEL);
			//there can only be an input between 0 and 3 (inclusive) guaranteed by 
			//the Math.random() command paired with the (int) casing 
		}
	}

	//made public for testing
	public static int dirOpposite(int dir) {
		if (dir == 0 || dir == 1) {
			return (dir + 1) % 2;
		} else {
			return (dir - 1) % 2 + 2;
		}
	}
}
