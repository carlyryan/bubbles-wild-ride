import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;


public class Bubbles extends Character{
    public static final String IMG_FILE = "files/bubbles.png";
    private static BufferedImage img;
    
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
     public Bubbles(int sky) {
    	 //I divided the width and height by 20 in order for Bubbles to be the 
    	 //appropriate size for the maze despite changes in the window scale
         super(INIT_VEL_X, INIT_VEL_Y, (int)(sky/20), (int)(sky/20), 
        		 (int)(sky/20),(int)(sky/20), sky);
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
}