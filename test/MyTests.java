import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

import org.junit.Test;

public class MyTests {
	private MazeState maze;
	private Bubbles bubbles;
	private MojoJojo mojo;
	public static final int BUBBLES_VELOCITY = 35;

	@Before
	public void setUp() {
		final JLabel status = new JLabel("Running...");
		maze = new MazeState(status, "Carly");
		bubbles = new Bubbles(700);
		mojo = new MojoJojo(700);
	}


	/*****CHARACTER*****/

	@Test
	public void testNotValidMovement(){
		bubbles.setPx(35); //index 1
		bubbles.setPy(245); //index 7
		bubbles.setVx(0);
		bubbles.setVy(BUBBLES_VELOCITY);
		assertFalse("Not Valid Movement", bubbles.validMovement());
	}

	@Test
	public void testValidMovement(){
		bubbles.setPx(35); //index 1
		bubbles.setPy(175); //index 5
		bubbles.setVx(0);
		bubbles.setVy(BUBBLES_VELOCITY);
		assertTrue("Valid Movement", bubbles.validMovement());
	}

	@Test
	public void testValidMovementMove(){
		bubbles.setPx(35); //index 1
		bubbles.setPy(175); //index 5
		bubbles.setVx(0);
		bubbles.setVy(BUBBLES_VELOCITY);
		bubbles.move();
		bubbles.move();
		assertFalse("Not Valid Movement after 2 moves", bubbles.validMovement());
	}

	@Test
	public void testNotValidMovementMove(){
		bubbles.setPx(35); //index 1
		bubbles.setPy(245); //index 7
		bubbles.setVx(0);
		bubbles.setVy(BUBBLES_VELOCITY);
		bubbles.move();
		assertEquals("Position Doesnt Move", 35, bubbles.getPx());
		assertEquals("Position Doesnt Move", 245, bubbles.getPy());
	}

	@Test
	public void testValidMovementDoesntMove(){
		bubbles.setPx(35); //index 1
		bubbles.setPy(175); //index 5
		bubbles.setVx(0);
		bubbles.setVy(BUBBLES_VELOCITY);
		bubbles.move();
		assertEquals("Position Moves", 35, bubbles.getPx());
		assertEquals("Position Moves", 210, bubbles.getPy());
	}

	@Test
	public void testNextOptions2ways() {
		bubbles.setPx(35); //index 1
		bubbles.setPy(35); //index 1
		int [] ewns = bubbles.nextOptions();
		assertEquals("Option East : 1", 1, ewns[0]);
		assertEquals("Option West : 0", 0, ewns[1]);
		assertEquals("Option North : 0", 0, ewns[2]);
		assertEquals("Option South : 1", 1, ewns[3]);
	}

	@Test
	public void testNextOptions3ways() {
		bubbles.setPx(455); //index 13
		bubbles.setPy(245); //index 7
		int [] ewns = bubbles.nextOptions();
		assertEquals("Option East : 1", 1, ewns[0]);
		assertEquals("Option West : 1", 1, ewns[1]);
		assertEquals("Option North : 0", 0, ewns[2]);
		assertEquals("Option South : 1", 1, ewns[3]);
	}

	@Test
	public void testNextOptions1way() {
		bubbles.setPx(280); //index 8
		bubbles.setPy(385); //index 11
		int [] ewns = bubbles.nextOptions();
		assertEquals("Option East : 0", 0, ewns[0]);
		assertEquals("Option West : 0", 0, ewns[1]);
		assertEquals("Option North : 0", 0, ewns[2]);
		assertEquals("Option South : 1", 1, ewns[3]);
	}
	
	@Test
	public void testIsDead(){
		bubbles.setPx(245); //index 7
		bubbles.setPy(245); //index 7
		bubbles.setVx(BUBBLES_VELOCITY);
		bubbles.setVy(0);
		mojo.setPx(315); //index 9
		mojo.setPy(245); //index 7
		mojo.setVx(-BUBBLES_VELOCITY);
		mojo.setVy(0);
		//the two will switch spots but should still die 
		assertTrue("Is Dead", Character.isDead(bubbles, mojo));
	}
	
	@Test
	public void testIsDeadSwitch(){
		bubbles.setPx(280); //index 8
		bubbles.setPy(245); //index 7
		bubbles.setVx(BUBBLES_VELOCITY);
		bubbles.setVy(0);
		mojo.setPx(315); //index 9
		mojo.setPy(245); //index 7
		mojo.setVx(-BUBBLES_VELOCITY);
		mojo.setVy(0);
		//the two will switch spots but should still die 
		assertTrue("Is Dead", Character.isDead(bubbles, mojo));
	}
	
	@Test
	public void testIsDeadVelocity00(){
		bubbles.setPx(280); //index 8
		bubbles.setPy(245); //index 7
		bubbles.setVx(0);//bubbles is still
		bubbles.setVy(0);
		mojo.setPx(315); //index 9
		mojo.setPy(245); //index 7
		mojo.setVx(-BUBBLES_VELOCITY);
		mojo.setVy(0);
		//the two will switch spots but should still die 
		assertTrue("Is Dead", Character.isDead(bubbles, mojo));
	}


	@Test
	public void testIsNotDead(){
		bubbles.setPx(280); //index 8
		bubbles.setPy(245); //index 7
		bubbles.setVx(0);
		bubbles.setVy(-BUBBLES_VELOCITY);
		//bubbles is moving north into a valid spot so should not die
		mojo.setPx(315); //index 9
		mojo.setPy(245); //index 7
		mojo.setVx(-BUBBLES_VELOCITY);
		mojo.setVy(0);
		//mojo is moving westwards towards bubbles
		assertFalse("Is Not Dead", Character.isDead(bubbles, mojo));
	}

	@Test
	public void testIsDeadDifferentV(){
		bubbles.setPx(595); //index 17
		bubbles.setPy(245); //index 7
		bubbles.setVx(BUBBLES_VELOCITY);
		bubbles.setVy(0);
		//bubbles is moving upward into a not valid spot so should die
		mojo.setPx(630); //index 18
		mojo.setPy(210); //index 6
		mojo.setVx(0);
		mojo.setVy(BUBBLES_VELOCITY);
		assertTrue("Is Dead", Character.isDead(bubbles, mojo));
	}


	/*** --- BUBBLES***/

	@Test
	public void testInitVels() {
		Bubbles bubbles0 = new Bubbles(100);
		assertEquals("init Vx", 0, bubbles0.getVx());
		assertEquals("init Vy", 0, bubbles0.getVy());
	}

	@Test
	public void testInitPos() {
		Bubbles bubbles0 = new Bubbles(100);
		int posX = 100/20;
		int posY = 100/20;
		assertEquals("Pos X", posX, bubbles0.getPx());
		assertEquals("Pos Y", posY, bubbles0.getPy());
	}

	@Test
	public void testMoveValid() {
		int origPosX = bubbles.getPx();
		int origPosY = bubbles.getPy();
		bubbles.setVx(35);
		bubbles.setVy(0);
		int ExpectedX = origPosX + 35;
		int ExpectedY = origPosY + 0;
		bubbles.move();
		assertEquals("Pos X", ExpectedX, bubbles.getPx());
		assertEquals("Pos Y", ExpectedY, bubbles.getPy());
	}

	@Test
	public void testmoveNotValid() {
		int origPosX = bubbles.getPx();
		int origPosY = bubbles.getPy();
		bubbles.setVx(35);
		bubbles.setVy(35);
		bubbles.move();
		assertEquals("Pos X", origPosX, bubbles.getPx());
		assertEquals("Pos Y", origPosY, bubbles.getPy());
	}


	/*** --- MOJO***/   
	@Test
	public void testdirOpposite() {
		assertEquals("0 opposite", 1, MojoJojo.dirOpposite(0));
		assertEquals("1 opposite", 0, MojoJojo.dirOpposite(1));
		assertEquals("2 opposite", 3, MojoJojo.dirOpposite(2));
		assertEquals("3 opposite", 2, MojoJojo.dirOpposite(3));
	}

	/*****MAZE STATE*****/
	@Test
	public void mazeArrayValues() {
		int [][] mazeArr = MazeState.getMaze();
		assertEquals("sky : (0, 0)", 0, mazeArr[0][0]);
		assertEquals("sky : (19, 19)", 0, mazeArr[19][19]);
		assertEquals("sky : (10, 10)", 0, mazeArr[10][10]);
		assertEquals("sky : (12, 15)", 0, mazeArr[12][15]);
		assertEquals("maze : (1, 1)", 1, mazeArr[1][1]);
		assertEquals("maze : (9, 9)", 1, mazeArr[9][9]);
		assertEquals("maze : (2, 16)", 1, mazeArr[2][16]);
	}

	@Test (expected = ArrayIndexOutOfBoundsException.class)
	public void mazeArrayValuesOutofBounds() {
			int [][] mazeArr = MazeState.getMaze();
			int i = mazeArr[20][0];
	}
}
