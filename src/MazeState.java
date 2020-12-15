import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeState extends JPanel {
	// state of the game
	private Bubbles bubbles;
	private Food food;
	private MojoJojo mojo;
	private BufferedImage background;
	private BufferedImage winScreen;
	private BufferedImage loseScreen;
	private String name;

	//stores the maze
	private static int [][] mazeArr;

	public boolean playing = false; 
	private JLabel status;

	// Game constants
	public static final int SKY = 700;
	//velocity for Bubbles
	public static final int BUBBLES_VELOCITY = SKY/20;
	//interval for the timer
	public static final int INTERVAL = 100;

	public MazeState(JLabel status, String name) {
		buildMaze();
		try {
			background = ImageIO.read(new File("files/sky.png"));
			winScreen = ImageIO.read(new File("files/winScreen.png"));
			loseScreen = ImageIO.read(new File("files/loseScreen.png"));
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					bubbles.setVx(-BUBBLES_VELOCITY);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					bubbles.setVx(BUBBLES_VELOCITY);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					bubbles.setVy(BUBBLES_VELOCITY);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					bubbles.setVy(-BUBBLES_VELOCITY);
				}
			}

			public void keyReleased(KeyEvent e) {
				bubbles.setVx(0);
				bubbles.setVy(0);
			}
		});
		
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start();

		setFocusable(true);

		this.name= name;
		this.status = status;
	}

	public void reset() {
		bubbles = new Bubbles(SKY);
		food = new Food (SKY);
		mojo = new MojoJojo(SKY);
		playing = true;
		status.setText("Go Bubbles Go!");
		requestFocusInWindow();
	}

	void tick() {
		if (playing) {
			boolean isDead = Character.isDead(bubbles, mojo);
			bubbles.move();
			mojo.move();
			if (food.getCount() == 190) {
				playing = false;
				writeHighScore();
			}
			if (isDead) {
				playing = false;
				writeHighScore();
			}
			food.eatFood(bubbles);
			repaint();
		}
	}

	public void writeHighScore () {
		try
		{
			BufferedReader in = new BufferedReader(new FileReader("files/scores.txt"));
			String line = in.readLine();
			String [] origNameScore = new String [3];
			// origNameScore is an array that has 3 strings of type "name, score" 
			// (ex : "Carly, 3")
			String [] newNameScore = new String [3];
			//newNameScore is the new array will of 3 strings that we will construct
			//later in inserting the new score in the appropriate spot. For now, we
			//we will copy the contents of the original into the new.
			int [] origScores = new int [3];
			//origScores extracts each score int out of the NameScore string
			int i = 0;
			while (line != null) {
				origNameScore[i] = line + '\n';
				newNameScore[i] = line + '\n';
				origScores[i] = Integer.parseInt(((origNameScore[i].split(","))[1]).trim());
				//extract the score and make it an int 
				line = in.readLine();
				//read the next line
				i++;
			}
			in.close();
			//we have now built the array of previous scores and can loop and find where
			//to add in the new score 
			int score = food.getCount(); 
			String nameScore = name + ", " + score + '\n';
			//nameScore is the string of the name style of the file that we will maybe 
			//write into the file
			for (int j = 0; j < 3; j++) {
				if (score > origScores[j]){
					newNameScore[j] = nameScore; 
					for (int k = j + 1; k < 3; k ++) {
						newNameScore[k] = origNameScore[k - 1];
						//copy the rest of the sorted array of scores after
						//the new score that was put in
					}
					break;
					//break the outer for loop
				}
			}
			BufferedWriter out = new BufferedWriter(new FileWriter("files/scores.txt"));
			for (int l = 0; l < 3; l++) {
				out.write(newNameScore[l]);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (playing) {
			//h will make the sky a square to scale in the drawImage command
			int h = background.getHeight(null);
			g.drawImage(background, 0, 0, SKY, SKY, 0, 0, h, h, null);
			drawMaze(g);
			bubbles.draw(g);
			food.draw(g);
			mojo.draw(g);
			status.setText("Score : " + food.getCount());
		}else if (food.getCount() == 190) {
			int h = winScreen.getHeight(null);
			g.drawImage(winScreen, 0, 0, SKY, SKY, 0, 0, h, h, null);
		} else {
			int h = winScreen.getHeight(null);
			g.drawImage(loseScreen, 0, 0, SKY, SKY, 0, 0, h, h, null);
		}
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SKY, SKY);
	}
	private void drawMaze(Graphics g) {
		g.setColor(Color.WHITE);
		for (int x = 1; x < 19; x++){
			for (int y = 1; y < 19; y++){
				if (mazeArr[x][y] == 1) {
					int scaledX = x * (SKY/20);
					int scaledY = y * (SKY/20);
					g.fillRect(scaledX, scaledY, SKY/20, SKY/20);
				}
			}
		}
	}

	private void buildMaze() {
		mazeArr = new int[20][20];
		for (int x = 1; x < 19; x++) {
			for (int y = 1; y < 19; y++) {
				mazeArr [x][y] = 1;
			}
		}
		mazeArr [2][17] = 0;
		mazeArr [4][1] = 0;
		mazeArr [4][17] = 0;
		mazeArr [7][11] = 0;
		mazeArr [7][15] = 0;
		mazeArr [12][11] = 0;
		mazeArr [12][15] = 0;
		mazeArr [15][1] = 0;
		mazeArr [15][17] = 0;
		mazeArr [17][17] = 0;
		//1
		for (int y = 8; y < 16; y++) {
			mazeArr [1][y] = 0;
			mazeArr [18][y] = 0;
		}
		//2
		for (int y = 2; y < 7; y++) {
			mazeArr [2][y] = 0;
			mazeArr [17][y] = 0;
		}
		//3
		for (int y = 8; y < 18; y++) {
			if (y != 11 && y != 14) {
				mazeArr [3][y] = 0;
				mazeArr [16][y] = 0;
			}
		}
		//4
		for (int y = 8; y < 18; y++) {
			if (y != 11 && y != 16) {
				mazeArr [5][y] = 0;
				mazeArr [14][y] = 0;
			}
		}
		//5
		for (int y = 3; y < 6; y++){
			mazeArr [4][y] = 0;
			mazeArr [15][y] = 0;
		}

		//a
		for (int x = 6; x < 14; x++) {
			mazeArr [x][2] = 0;
			mazeArr [x][4] = 0;
			mazeArr [x][13] = 0;
		}
		//b
		for (int x = 7; x < 13; x++) {
			mazeArr [x][8] = 0;
			mazeArr [x][10] = 0;
			mazeArr [x][17] = 0;
		}
		//c
		for (int x = 4; x < 16; x++) {
			if (x != 8 && x != 11) {
				mazeArr [x][6] = 0;
			}
		}
		//d
		for (int x = 9; x < 11; x++) {
			mazeArr [x][5] = 0;
			mazeArr [x][11] = 0;
			mazeArr [x][15] = 0;
			mazeArr [x][16] = 0;
		}
	}

	public static int[][] getMaze() {
		int [][] mazeCopy = new int [20][20];
		for (int x = 0; x < 20; x++){
			for (int y = 0; y < 20; y++){
				mazeCopy[x][y] = mazeArr[x][y];
			}
		}
		return mazeCopy;
	}
}
