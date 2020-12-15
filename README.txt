=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

![winScreen](files/winScreen.png)

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Testable component. 
The testable component was essential in creating the game. One gets very tired of 
playing pacman over and over again to find and test bugs. The testable component was 
huge in testing helper functions and intended functionality such as validMovement(), 
nextOptions() and isDead() in character, dirOpposite in MojoJojo and many more 
functions. 

  2. 2D array.
 I use a 2D array to directly represent the rows and columns of the board. The array
 of the MazeState stores the information of if the value is part of the maze or not
 (1 if it is, 0 if not). The characters are only able to move in the coordinates 
 in the2D array that are equal to 1. I had to manually build the array and assign
 values in the method buildMaze() in mazeState, I then need to drawMaze on the board.
 I also need to getMaze() in the character functions and their successors in order to
 ensure that movement only takes place within the maze. To implement getMaze() I needed
 to copy the information over instead of simply returning the array. I also use getMaze() 
 and a 2D array in Food. Here, the values are either 0 or 1, holding the same meaning as 
 before, but also can be 2 if there is a piece of food in that spot on the maze. 
 
 The 2D array is a great model because it is easy to implement a drawing of it because
 its structure is the complete board. We can alse continuously reassign values to it in 
 Food to be able to alter the food count. 

  3. File IO.
 File IO stores the high scores and the Name of the player. Each game, even if its the same
 player/instance of MazeState, is evaluated so the same name could store all 3 high scores. 
 
 The code to rearrange the lines of scores is fairly long but I could not find a shorter 
 strategy.

  4. Subtyping and Inheritance.
I used subtyping to model the characters in the game. This makes sense to simplify getters 
and setters and move functions. 

This also helps in sight of further development. In further developping the game I would maybe
let the hero be chosen by the user (which Powerpuff : Bubbles, Buttercup or Blossom) and 
each character would have a different subtype. I would also maybe do different subtypes of 
food iwth different point levels.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
CHARACTER : the character abstract class is the class that defines the general
functionality of a character in the game. The field coodstoArray is invaluable
because it is the coefficient that helps convert the real coordinates in the
frame that we need to draw the photos to indices of the 2D array in the maze map.
It holds the position (x, y), velocity (x, y) and size of the character. 
Other methods : 
		- move() : checks if its a valid move in the maze array and then updates
		the position 
		- validMovement() : (public for testing) helper for move() function to
		check the mazeState array and the valid tiles. 
		- nextOptions() : returns an array of length 4: EastWestNorthSouth: ewns. 
		Each index represents a direction and its value is 1 if that tile is a
		valid movement from the current position and 0 if not.
		- draw() : self explanatory abstract function.

BUBBLES : Bubbles extends character and has its own image and initial velocity. 
She also has her own draw function. 
		- draw() : draws the image of bubbles in her current px and py. 
		
MOJO : Mojo is the super evil villain trying to kill Bubbles and her dreams. 
In addition to the fields of his photo and initial velocity, mojo also stores 
his current direction. I chose to store the directions as integers to simplify
things. In the ewns array (mentioned in the nextOptions() explanation) the same
integers translate to the indices of the length 4 array (one reason that 
directions are translated into ints). This int direction code is also what is 
passed into the changeDirection() method.
		- @Override move() : my decisions for the move function are explained in
		depth in the stumbling blocks question. In the end mojo cannot move back-
		wards in our random assignment of direction, making it so that we
		do not want him to enter the dead end blocks in the lower screen.
		- changeDirection() : setter that takes in the direction that we want to 
		change it to and executes. 
		- dirOpposite() : returns the direction that is backwards from the current
		trajectory. This is an example of another instance why having directions
		as ints is useful because here we can use simple modulus calculations to 
		return. 
		- draw() : self-explanatory.

FOOD : Food stores the 2D array of coordinates of the frame, mazeStateFood. Like in
MazeState, the values can be 1 or 0, 0 if it is not a valid tile, unlike in MazeState, 
the value can also be 2. 0 is an invalid tile, 1 is an empty tile and 2 is a tile with
a heart. The constructor gets the maze from MazeState and copies it into 
mazeStateFood, putting a piece of food into every tile to begin (all tiles that 
were previously == 1 are now = 2).
There is a field for the image that is the food. Again, there is a 
coordsToArray. There is also a field foodCount (and a getter) that keeps track 
of the amount of food eaten, essentially the score. 
Other methods : 
		- eatFood() : checks if bubbles is on top of the piece of food, if yes the value
		at that pair of indices is set to 1 (empty tile now) : the food is eaten. 
		Also increments the food count. 
		- draw() : loops through the array and draws the heart where mazeStateFood[x][y] 
		== 2. 

MAZESTATE :  An instance of MazeState is an instance of opening the game. There is a 
boolean, playing, if the game is still going. At each tick the frame is repainted. The
constructor focuses the keyboard and implements the KeyListeners for the keys for bubbles'
movement which updates her velocity. 
		- paintComponent() : if the game is still playing (playing == true) it calls all 
		the draw methods to tie together the frame with all the elements we have 
		implemented. If the game is lost or won it draws the appropriate screen. 
		- reset() : sets playing to true. Makes the window have the keyboard focus. 
		Is called initially when the game starts in Game but also is connected to the 
		"Play!" JButton. 
		- tick() : at the pace of the timer, the tick updates the state of the game 
		at each interval, it moves the characters, eats the food and checks if the 
		game is over.
		- writeHighscore() : this method updates the scores file with adding in the
		new highscore if necessary. The file contains names and scores. We need to read
		the file and parse the information, then proceed to check the scores if/where the
		new score fits into the file of high scores, storing only 3 scores at a time, then
		rewrite the new information (whether or not it changed) into the file. 
		- getPreferedSize() : to draw the frame to scale
		
		- Maze Methods :
		Instead of making a Maze class I decided to just include it in the MazeState class
		because I didn't deem it necessary to make a new class. I would need to use 
		getters in MazeState anyways so I figured I could just make a private builder and
		drawer method in the MazeState.
		- buildMaze(): manually builds the array
		- getMaze(): copies and returns the maze array
		- drawMaze() : self explanatory 
		
GAME : This class implements the JPannels, playing area, the reset button and the instructions 
screen and getting the player names. It puts the frame on the screen and then finally starts 
the game. We also need that main method. 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
Implementing the maze movement was an extreme time crunch to design. I tried many
different things (creating its own class and other ways) before deciding to have a 
more simplistic minimal approach (and making it work) thorough the two methods in 
character : validMovement() and nextOptions().

Even more difficult than implementing the maze was simulating the movement of
MojoJojo. This movement is 1. still contained to the maze 2. random (to an extent).
Firstly the randomization had to stay within the blocks of the maze. Second, the 
movement is not truly random in each step. The villain does not more sporadically east 
to west back and forth on a path. It continues along the path it is on. But this 
last sentence is not exact. I first implemented a model where mojo continued
on its path until it was not valid to move any more and then it found a new path. 
This was not a good implementation because then mojo would continuously get stuck 
at the top half because there are no true corners leading it south. Even if 
the current velocity is already valid, the nature of the maze has kinks that 
will never be entered unless we sometimes move even when the current trajectory 
is valid (there are no true corners that point south into the bottom half). 

The way I decided to implement was first to find the valid movements in the matrix
of the current options centered with the character's current position. Then I got the
 array of the options and picked a random variable that randomized the order of the
loop in which we chose direction. Then if that direction was an option AND it was not
the opposite direction of the current direction (N/S and E/W) I would change velocity. 
Mojo can't move backwards.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
There is a very good separation of classes and functionality in general. Each class
is exactly necessary (no more no less – not overkill but enough). I think I did a very
good job of making methods also, with good naming convention. I realized this 
while doing the ReadMe that there was not a single method that I could not justify
exactly why its existence is pertinent and sufficient. 

While functionality is preserved, one thing I cannot say confidently is if I 
distributed the methods wellbetween classes, for example the methods in Character 
vs the methods in MojoJojo. This is something I would need to self evaluate a 
little more closely. 

Private state is encapsulated very well. I only use getters and setters to pass
information and in the getters I copy it if it is an array (getMaze()). 

I explained earlier in talking about extending classes, how I would make a much
more dimensional and interesting game through different characters and food. 
Besides that I loved making the game and am super happy with the product and
functionality.

I did not implement more keyboard functionality than the mushroom of doom because 
I think it would diminish user PacMan experience to have too many keys to press. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  none
