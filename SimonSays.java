/* Name: Hazel & Nicole
   Teacher: Ms. Krasteva & Ms. Cruceanu
   Date: November 16
   Assignment: This program is our ISP - a Simon Says game
*/

//IMPORTS
import java.awt.*; //allow use for java.awt
import hsa.Console; //allow use for console
import hsa.Message; //allow use for new Message
import java.awt.image.*; //allow use for BufferedImages
import javax.imageio.ImageIO; //allow us to import images
import java.io.*; //allow java input/output libraries
import javax.swing.JOptionPane; //allow use for JOptionPane

public class SimonSays //SimonSays class
{
    //console variable
    Console c;
    //main declarations:
    int menuChoice = 0; //user's menuchoice
    Font mc, mcSmall, mcLarge, mcMed, mcMS; //fonts used throughout the program
    //color declarations:
    Color white = new Color (245, 245, 245);
    Color red = new Color (242, 95, 92);
    Color orange = new Color (235, 162, 85);
    Color yellow = new Color (255, 224, 102);
    Color green = new Color (111, 204, 104);
    Color turqoise = new Color (112, 193, 180);
    Color blue = new Color (36, 123, 160);
    Color indigo = new Color (128, 126, 204);
    Color violet = new Color (195, 125, 209);
    //game declarations:
    int str1, str2, str3; //locations for which colour the user is supposed to pick
    String str2value; //word for the colour the user is supposed to pick
    Color squareColor; //font colour for what the user is supposed to pick
    int blobX = 240, blobY = 250; //blob location
    int levelChoice = 0; //user can choose which level to start on
    final int LVL1TIME = 2000; //level 1 - 2 seconds to read each prompt
    final int LVL2TIME = 1000; //level 2 - 1 second to read each prompt
    final int LVL3TIME = 1000; //level 3 - 1 second to read each prompt
    final int LVL4TIME = 500; //level 4 - 0.5 seconds to read each prompt
    final int LVL5TIME = 750; //level 5 - 0.75 seconds to read each prompt
    final int LVL6TIME = 250; //level 6 - 0.25 seconds to read each prompt
    //leaderboard variables
    String[] [] lines = {{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}; //array that stores the top 10 rank/intials/scores
    String initials = ""; //to be entered by user
    int score; //calculated from game


    public SimonSays ()  //create constructor
    {
	c = new Console (); //create the console
    }


    public void splashScreen ()  //splash screen - first thing the user sees
    {
	int time = 0; //timer
	try //try catch block for importing images
	{
	    BufferedImage background1 = ImageIO.read (new File ("images//background1.png")); //alternating between two background images
	    BufferedImage background2 = ImageIO.read (new File ("images//background2.png")); //import second background image
	    BufferedImage blob1 = ImageIO.read (new File ("images//blob1.png")); //alternating between two blob images
	    BufferedImage blob2 = ImageIO.read (new File ("images//blob2.png")); //import second blob image

	    for (int i = 0 ; i < 3 ; i++) //the blob jumps a total of three times
	    {
		try //try catch block for Thread.sleep
		{
		    //frame 1 - blob is on the ground
		    c.drawImage (background1, 0, 0, null); //draws background
		    c.drawImage (blob1, 130 * i, 320, null); //draws blob
		    Thread.sleep (500); //0.5 second pause
		    time += 500; //adds to total timer
		    //frame 2 - blob is jumping up
		    c.drawImage (background1, 0, 0, null); //redraw the background to cover the first blob
		    c.drawImage (blob2, 130 * i + 35, 270, null); //redraw the blob
		    Thread.sleep (300); //0.3 second pause
		    time += 300; //add to time
		    //frame 3 - blob is in the air
		    c.drawImage (background2, 0, 0, null); //switches background
		    c.drawImage (blob1, 130 * i + 50, 265, null); //draw the blob again
		    Thread.sleep (300); //0.3 second pause
		    time += 300; //add to time
		    //frame 4 - blob is jumping down
		    c.drawImage (background2, 0, 0, null); //cover the old blob by redrawing background
		    c.drawImage (blob2, 130 * i + 90, 270, null); //draw the next blob
		    Thread.sleep (300); //0.3 second pause
		    time += 300; //add to time
		}
		catch (InterruptedException e)  //catch for error
		{
		    c.println ("Error - Thread.sleep at splash screen part 1"); //output what/where the error is
		}
	    }
	    try //try catch block for Thread.sleep - ending of splash screen, 1.3 second pause
	    {
		c.drawImage (background1, 0, 0, null); //draws background
		c.drawImage (blob1, 390, 320, null); //draws blob on the ground
		Thread.sleep (1300); //1.3 second pause
		time += 1300; //add to the time
	    }
	    catch (InterruptedException e)  //catch an error
	    {
		c.println ("Error - Thread.sleep at splash screen part 2"); //output what/where the error is
	    }
	}
	catch (IOException e)  //check for error
	{
	    c.println ("Error - Unable to load imagese"); //output what the error is
	}
    }


    public void mainMenu ()  //display the mainmenu - user can navigate to [INSTRUCTIONS], [PLAY], [LEADERBOARD], or [QUIT]
    {
	try //try catch block for font
	{
	    InputStream is = SimonSays.class.getResourceAsStream ("fonts\\mcFont.ttf"); //imports the font
	    mc = Font.createFont (Font.TRUETYPE_FONT, is); //creates a font with size 1
	    mcSmall = mc.deriveFont (Font.PLAIN, 10); //scales the original font to size 10
	    mcMS = mc.deriveFont (Font.PLAIN, 15); //scales the original font to size 15
	    mcMed = mc.deriveFont (Font.PLAIN, 30); //scales the original font to size 30
	    mcLarge = mc.deriveFont (Font.PLAIN, 50); //scales the original font to size 50
	    mc = mc.deriveFont (Font.PLAIN, 20); //changes the original font to size 20
	}
	catch (Exception e)  //catch exception
	{
	    c.println ("Error - Unable to load font"); //output what the error is
	}

	int menuHover = 1; //the selection the user is currently hovering
	char menuMove = ' '; //what they press
	int ballX = 110, ballY = 120; //hover (disco ball x and y coordinates)

	while (true) //infinite loop until a choice is selected
	{
	    menuMove = ' '; //reset the variable
	    try //try catch block for image
	    {
		BufferedImage menubg = ImageIO.read (new File ("images\\menubg.png")); //import menu background image
		c.drawImage (menubg, 0, 0, null); //draw the background to fill the screen
	    }
	    catch (IOException e)  //catch for error
	    {
		c.println ("Error - Unable to load image"); //output what the error is
	    }

	    c.setFont (mcLarge); //set font
	    c.setColor (white); //set text colour
	    c.drawString ("SIMON SAYS", 155, 80); //title
	    c.setFont (mcMed); //set font again
	    c.drawString ("Instructions", 205, 170); //selections
	    c.drawString ("Play", 282, 250); //display the options
	    c.drawString ("Leaderboard", 195, 330); //display options
	    c.drawString ("Quit", 288, 410); //display options
	    c.setFont (mcSmall); //set font again
	    c.drawString ("Made by Hazel and Nicole for ICS3U, 2020", 190, 480); //write names and class + year at bottom
	    try //try catch block for image
	    {
		BufferedImage ball = ImageIO.read (new File ("images//ball.png")); //import ball image
		c.drawImage (ball, ballX, ballY, null); //draws the ball image at ballX and ballY - changes with user selection
	    }
	    catch (IOException e)  //catch error
	    {
		c.println ("Error - Unable to load image"); //output what the error is
	    }
	    if (menuHover == 1) //menu hovers first option (instructions)
	    {
		menuMove = c.getChar (); //checks what the user presses
		if (menuMove == 's') //move down
		{
		    menuHover = 2; //go to option 2
		}
		else if (menuMove == '\n') //if they press enter
		{
		    menuChoice = 1; //choose first option
		    break; //exit infinite loop
		}
	    }
	    else if (menuHover == 2) //menu hovers second option (game)
	    {
		menuMove = c.getChar (); //checks what the user presses
		if (menuMove == 'w') //move up
		{
		    menuHover = 1; //go to option 1
		}
		else if (menuMove == 's') //move down
		{
		    menuHover = 3; //go to option 3
		}
		else if (menuMove == '\n') //if they press enter
		{
		    menuChoice = 2; //choose second option
		    break; //exit infinite loop
		}
	    }
	    else if (menuHover == 3) //menu hovers third option (leaderboard)
	    {
		menuMove = c.getChar (); //move up
		if (menuMove == 'w') //move up
		{
		    menuHover = 2; //go to option 2
		}
		else if (menuMove == 's') //move down
		{
		    menuHover = 4; //go to option 4
		}
		else if (menuMove == '\n') //if they press enter
		{
		    menuChoice = 3; //choose third option
		    break; //exit infinite loop
		}
	    }
	    else if (menuHover == 4) //menu hovers fourth option (quit)
	    {
		menuMove = c.getChar ();
		if (menuMove == 'w') //move up
		{
		    menuHover = 3; //go to option 3
		}
		else if (menuMove == '\n') //if they press enter
		{
		    menuChoice = 4;  //choose fourth option
		    break; //exit infinite loop
		}
	    }
	    //changing ball location depending on user selection:
	    if (menuHover == 1) //if they hover option 1
	    {
		ballX = 110; //change ballX
		ballY = 120; //change ballY
	    }
	    else if (menuHover == 2) //if they hover option 2
	    {
		ballX = 187; //change ballX
		ballY = 200; //change ballY
	    }
	    else if (menuHover == 3) //if they hover option 3
	    {
		ballX = 100; //change ballX
		ballY = 280; //change ballY
	    }
	    else if (menuHover == 4) //if they hover option 4
	    {
		ballX = 193; //change ballX
		ballY = 360; //change ballY
	    }
	}
    }


    public void instructions ()  //instructions
    {
	try //try catch for background
	{
	    BufferedImage menubg = ImageIO.read (new File ("images\\menubg.png")); //import menubg image
	    c.drawImage (menubg, 0, 0, null); //draws as bg
	}
	catch (IOException e)  //catch error
	{
	    c.println ("Error - Unable to import image"); //output what the error is
	}
	//output instructions to console
	c.setFont (mcLarge);
	c.drawString ("SIMON SAYS", 155, 80); //title
	c.setFont (mcMS); //text
	c.drawString ("Welcome to Simon Says, a game where you help a blob dance!", 60, 120);
	c.drawString ("Use the following keys that correspond with the colour location", 20, 150);
	c.drawString ("to move the blob to the correct square:", 20, 175);
	c.drawString ("The overall game difficulty will increase as the", 20, 200);
	c.drawString ("game continues, with more colours to memorize", 20, 225);
	c.drawString ("and less time to read them. Input the correct", 20, 250);
	c.drawString ("keys in the sequence of colours shown. You", 20, 275);
	c.drawString ("will have three hearts, losing them when a", 20, 300);
	c.drawString ("sequence is entered incorrectly. Your score will be calculated", 20, 325);
	c.drawString ("depending on the level and number of correct entries.", 20, 350);
	c.setFont (mc);
	c.drawString ("Good luck!", 260, 400);
	c.drawString ("Press any key to continue", 160, 440); //prompt user to continue
	c.setFont (mcSmall);
	c.drawString ("Made by Hazel and Nicole for ICS3U, 2020", 190, 480);
	//SQUARE WITH LETTERS
	try //small square
	{
	    BufferedImage smallsquare = ImageIO.read (new File ("images\\smallsquare.png")); //import image
	    c.drawImage (smallsquare, 450, 155, null); //draw the square
	}
	catch (IOException e)  //catch exception
	{
	    c.println ("Error - Unable to import image"); //output what the error is
	}
	c.setFont (mcMed); //set the font
	c.setColor (Color.black); //letters
	//draw the letters
	c.drawString ("Q", 468, 195);
	c.drawString ("W", 518, 195);
	c.drawString ("E", 568, 195);
	c.drawString ("A", 468, 245);
	c.drawString ("S", 518, 245);
	c.drawString ("D", 568, 245);
	c.drawString ("Z", 468, 295);
	c.drawString ("X", 518, 295);
	c.drawString ("C", 568, 295);

	c.getChar (); //wait for user input
    }


    public void levelSelect ()  //level select menu where user can choose level 1,2, or 3 and enter 4 to go back to mainMenu
    {
	boolean error = true; //if there is an invalid input
	while (error) //loop until the program gets valid input
	{
	    try //background
	    {
		BufferedImage menubg = ImageIO.read (new File ("images\\menubg.png")); //import background
		c.drawImage (menubg, 0, 0, null); //draw background
	    }
	    catch (IOException e)  //catch exception
	    {
		c.println ("Error - Unable to import image"); //output what the error is
	    }
	    c.setFont (mcLarge); //set font
	    c.setColor (white); //set text colour
	    c.drawString ("SIMON SAYS", 155, 80); //draw title
	    c.setFont (mc); //reset font
	    c.drawString ("Please choose a level using 1, 2 or 3:", 105, 170); //give user options
	    c.drawString ("Level 1: Easy", 240, 240); //display option
	    c.drawString ("Level 2: Medium", 230, 290); //display option
	    c.drawString ("Level 3: Hard", 240, 340); //display option
	    c.drawString ("Press 4 to return to the Main menu", 120, 400); //display option
	    c.setFont (mcSmall); //set font
	    c.drawString ("Made by Hazel and Nicole for ICS3U, 2020", 190, 480); //output name + class & year
	    char level = c.getChar (); //wait for user to press a key
	    if (level != 49 && level != 50 && level != 51 && level != 52) //check if the user did not press 1,2,3 or 4 (ASCII values since it is a char)
	    {
		new Message ("Please enter a valid level."); //tell user to enter a valid option
	    }
	    else
	    {
		if (level == 49) //if they pressed 1
		    levelChoice = 1; //set levelChoice to 1
		else if (level == 50) //if they pressed 2
		    levelChoice = 2; //set levelChoice to 2
		else if (level == 51) //if they pressed 3
		    levelChoice = 3; //set levelChoice to 3
		else if (level == 52) //if they pressed 4
		    levelChoice = 4; //set levelChoice to 4
		error = false; //set error to false since the program got a valid input
	    }
	}
    }


    public void game ()  //method where the actual game is drawn and run
    {
	int lives = 3; //number of lives the user has
	while (lives > 0) //while they have lives left
	{
	    boolean correct = true;
	    try //allow the program to import images
	    {
		//import the images
		BufferedImage blob = ImageIO.read (new File ("images\\blobtop.png"));
		BufferedImage heart = ImageIO.read (new File ("images\\heart.png"));
		BufferedImage heartLost = ImageIO.read (new File ("images\\heartLost.png"));
		BufferedImage fail = ImageIO.read (new File ("images\\fail.png"));
		BufferedImage check = ImageIO.read (new File ("images\\check.png"));

		//set the x,y for the hearts
		int heartX = 55;
		int heart1Y = 160;
		int heart2Y = 280;
		int heart3Y = 400;
		int randNum = 0;
		c.setFont (mc); //set font
		int colours = 3; //number of colours displayed, default is 3
		int maxTime = LVL1TIME; //set default timer max
		int scoreCounter = 5; //variable to store how much score increases by, default is 5
		String ans = ""; //String variable to store all the random numbers chosen
		String userPress = ""; //store the chars the user pressed
		if (levelChoice == 1) //if they chose level 1
		{
		    //set the defaults
		    maxTime = LVL1TIME;
		    colours = 3;
		    scoreCounter = 5;
		    if (score > 375) //level 6
		    {
			maxTime = LVL6TIME;
			colours = 5;
			scoreCounter = 30;
		    }
		    else if (score > 250)  //level 5
		    {
			maxTime = LVL5TIME;
			colours = 5;
			scoreCounter = 25;
		    }
		    else if (score > 150)  //level 4
		    {
			maxTime = LVL4TIME;
			colours = 4;
			scoreCounter = 20;
		    }
		    else if (score > 75)  //level 3
		    {
			maxTime = LVL3TIME;
			colours = 4;
			scoreCounter = 15;
		    }
		    else if (score > 25)  //level 2
		    {
			maxTime = LVL2TIME;
			colours = 3;
			scoreCounter = 10;
		    }

		}
		else if (levelChoice == 2) //if they chose level 2
		{
		    //set the values
		    maxTime = LVL3TIME;
		    colours = 4;
		    scoreCounter = 15;
		    if (score > 375) //level 6
		    {
			maxTime = LVL6TIME;
			colours = 5;
			scoreCounter = 30;
		    }
		    else if (score > 250)  //level 5
		    {
			maxTime = LVL5TIME;
			colours = 5;
			scoreCounter = 25;
		    }
		    else if (score > 150)  //level 4
		    {
			maxTime = LVL4TIME;
			colours = 4;
			scoreCounter = 20;
		    }
		}
		else if (levelChoice == 3) //if they choose level 3
		{
		    //set the values
		    maxTime = LVL5TIME;
		    colours = 5;
		    scoreCounter = 25;
		    if (score > 375) //level 6
		    {
			maxTime = LVL6TIME;
			colours = 5;
			scoreCounter = 30;
		    }
		}
		int timer = 0; //create variable that serves as timer
		for (int i = 0 ; i < colours ; i++)
		{
		    timer = 0;
		    try //background
		    {
			BufferedImage gamebg = ImageIO.read (new File ("images\\gamebg.png")); //import the game background
			c.drawImage (gamebg, 0, 0, null);
		    }
		    catch (IOException e)  //catch the image
		    {
			c.println ("Error - error importing image");
		    }
		    if (lives == 1) //if they have 1 life
		    {
			//draw 2 broken hearts, 1 heart
			c.drawImage (heartLost, heartX, heart1Y, null);
			c.drawImage (heartLost, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }
		    else if (lives == 2) //if they have 2 lives
		    {
			//draw 1 broken heart, 2 hearts
			c.drawImage (heartLost, heartX, heart1Y, null);
			c.drawImage (heart, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }
		    else if (lives == 3) //if they have all 3 lives
		    {
			//draw 3 hearts
			c.drawImage (heart, heartX, heart1Y, null);
			c.drawImage (heart, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }

		    randNum = (int) ((9) * Math.random () + 1); //generate the random number
		    ans += randNum; //add it to the ans string that stores the correct sequence

		    //the specified amount of time based on the level
		    while (timer <= maxTime)
		    {
			try
			{
			    c.setColor (Color.white); //set text colour
			    c.drawString ("COLOR NUMBER: " + (i + 1), 220, 40); //display the colours
			    drawInstruction (randNum); //draw the instruction
			    Thread.sleep (10); //wait 10 milliseconds
			    timer += 10; //add 10 milliseconds to time
			}
			catch (InterruptedException e)
			{
			    c.println ("Error - threads");
			}
		    }
		}

		try //background
		{
		    BufferedImage gamebg = ImageIO.read (new File ("images\\gamebg.png")); //import image
		    c.drawImage (gamebg, 0, 0, null); //draw the background
		}
		catch (IOException e)  //catch exception
		{
		    c.println ("Error - could not import image"); //catch the error
		}
		c.setColor (white); //set text colour
		c.drawString ("Enter the sequence!", 195, 70); //prompt user to enter their sequence

		//redraw the lives (hearts & broken hearts)
		if (lives == 1) //if they have 1 life
		{
		    //draw 2 broken hearts, 1 heart
		    c.drawImage (heartLost, heartX, heart1Y, null);
		    c.drawImage (heartLost, heartX, heart2Y, null);
		    c.drawImage (heart, heartX, heart3Y, null);
		}
		else if (lives == 2) //if they have 2 lives
		{
		    //draw 1 broken heart, 2 hearts
		    c.drawImage (heartLost, heartX, heart1Y, null);
		    c.drawImage (heart, heartX, heart2Y, null);
		    c.drawImage (heart, heartX, heart3Y, null);
		}
		else if (lives == 3) //if they have all 3 lives
		{
		    //draw 3 hearts
		    c.drawImage (heart, heartX, heart1Y, null);
		    c.drawImage (heart, heartX, heart2Y, null);
		    c.drawImage (heart, heartX, heart3Y, null);
		}


		//loop through the number of colours
		for (int i = 0 ; i < colours ; i++)
		{

		    char pressed = c.getChar (); //get the pressed character
		    userPress += pressed; //add the character to the string of characters the user pressed
		}
		userPress = userPress.toLowerCase (); //change them all to lowercase
		for (int i = 0 ; i < colours ; i++) //loop through each colour
		{
		    try //background
		    {
			BufferedImage gamebg = ImageIO.read (new File ("images\\gamebg.png")); //re-import background
			c.drawImage (gamebg, 0, 0, null); //draw background
		    }
		    catch (IOException e)  //catch exception
		    {
			c.println ("Error - could not import image"); //output the error
		    }
		    if (lives == 1) //if they have 1 life
		    {
			//draw 2 broken hearts, 1 heart
			c.drawImage (heartLost, heartX, heart1Y, null);
			c.drawImage (heartLost, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }
		    else if (lives == 2) //if they have 2 lives
		    {
			//draw 1 broken heart, 2 hearts
			c.drawImage (heartLost, heartX, heart1Y, null);
			c.drawImage (heart, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }
		    else if (lives == 3) //if they have all 3 lives
		    {
			//draw 3 hearts
			c.drawImage (heart, heartX, heart1Y, null);
			c.drawImage (heart, heartX, heart2Y, null);
			c.drawImage (heart, heartX, heart3Y, null);
		    }
		    try
		    {
			char correctKey = ' '; //char that stores the correct key
			c.setColor (Color.white); //set text colour
			if (ans.charAt (i) == '1') //if the answer is 1
			{
			    c.drawString ("Correct colour is orange. Correct key is q.", 50, 50);
			    correctKey = 'q';
			}
			else if (ans.charAt (i) == '2') //if the answer is 2
			{
			    c.drawString ("Correct colour is yellow. Correct key is w.", 50, 50);
			    correctKey = 'w';
			}
			else if (ans.charAt (i) == '3') //if the answer is 3
			{
			    c.drawString ("Correct colour is green. Correct key is e.", 50, 50);
			    correctKey = 'e';
			}
			else if (ans.charAt (i) == '4') //if the answer is 4
			{
			    c.drawString ("Correct colour is red. Correct key is a.", 50, 50);
			    correctKey = 'a';
			}
			else if (ans.charAt (i) == '5') //if the answer is 5
			{
			    c.drawString ("Correct colour is white. Correct key is s.", 50, 50);
			    correctKey = 's';
			}
			else if (ans.charAt (i) == '6') //if the answer is 6
			{
			    c.drawString ("Correct colour is turqoise. Correct key is d.", 50, 50);
			    correctKey = 'd';
			}
			else if (ans.charAt (i) == '7') //if the answer is 7
			{
			    c.drawString ("Correct colour is violet. Correct key is z.", 50, 50);
			    correctKey = 'z';
			}
			else if (ans.charAt (i) == '8') //if the answer is 8
			{
			    c.drawString ("Correct colour is indigo. Correct key is x.", 50, 50);
			    correctKey = 'x';
			}
			else if (ans.charAt (i) == '9') //if the answer is 9
			{
			    c.drawString ("Correct colour is blue. Correct key is c.", 50, 50);
			    correctKey = 'c';
			}
			;
			c.drawString ("You pressed " + userPress.charAt (i) + ".", 200, 100);

			if (correctMove (ans.charAt (i), userPress.charAt (i), i, correctKey)) //if they pressed the correct sequence
			{
			    c.drawImage (blob, blobX, blobY, null); //draw the blob accordingly
			    score += scoreCounter; //add to their score
			}
			else //if they get it wrong
			{
			    lives--; //subtract a life
			    correct = false; //set the correct boolean flag to false
			    c.drawImage (fail, 185, 120, null); //draw the x-mark
			    break; //break loop
			}
			Thread.sleep (1500); //wait 1.5 seconds
		    }
		    catch (InterruptedException e)
		    {
			c.println ("Error - thread exception"); //output the error
		    }
		}
		if (correct) //if it is correct
		{
		    c.drawImage (check, 135, 100, null); //draw the checkmark
		}

		c.getChar (); //wait for user to press a key
	    }

	    catch (IOException e)
	    {
		c.println ("Error - could not import file");
	    }
	}
	boolean confirmed = false; //if the user confirmed the initials
	while (!confirmed) //while they have not confirmed it, run
	{
	    try
	    {
		initials = JOptionPane.showInputDialog ("Enter initials:"); //prompt for initials

		if (initials.length () == 2) //if the initials are 2 letters
		{
		    if (initials.charAt (0) > 122 || initials.charAt (0) < 65 || initials.charAt (1) > 122 || initials.charAt (1) < 65) //if it is a letter
		    {
			new Message ("Please enter only letters.");

		    }
		    else
		    {
			String confirmation = "";
			confirmation = JOptionPane.showInputDialog ("Your initials are: " + initials + "\nPress any key to continue, or b to re-enter."); //ask for confirmation
			if (!confirmation.equals ("b")) //if they want to go back
			{
			    confirmed = true;
			}
		    }
		}
		else
		{
		    new Message ("Please enter 2 letters for your intials."); //tell user
		}
	    }
	    catch (Exception e)  //catch if user presses cancel on the JOptionPane
	    {
		c.println ("Error - Unable to read initials");
	    }

	}
	initials = initials.toUpperCase (); //make it uppercase
	writeLB (); //write to lb
	showLB (); //display lb
    }


    public void drawInstruction (int num)  //draw the instructions
    {
	switch (num) //switch case for the number
	{
	    case 1: //orange
		//change string x values and the colour
		str1 = 150;
		str2 = 293;
		str3 = 390;
		squareColor = orange;
		str2value = "ORANGE";
		break;
	    case 2: //yellow
		//change string x values and the colour
		str1 = 150;
		str2 = 291;
		str3 = 390;
		squareColor = yellow;
		str2value = "YELLOW";
		break;
	    case 3: //green
		//change string x values and the colour
		str1 = 155;
		str2 = 300;
		str3 = 385;
		squareColor = green;
		str2value = "GREEN";
		break;
	    case 4: //red
		//change string x values and the colour
		str1 = 170;
		str2 = 315;
		str3 = 370;
		squareColor = red;
		str2value = "RED";
		break;
	    case 5: //white
		//change string x values and the colour
		str1 = 155;
		str2 = 302;
		str3 = 385;
		squareColor = white;
		str2value = "WHITE";
		break;
	    case 6: //turqoise
		//change string x values and the colour
		str1 = 135;
		str2 = 280;
		str3 = 405;
		squareColor = turqoise;
		str2value = "TURQOISE";
		break;
	    case 7: //violet
		//change string x values and the colour
		str1 = 152;
		str2 = 296;
		str3 = 388;
		squareColor = violet;
		str2value = "VIOLET";
		break;
	    case 8: //indigo
		//change string x values and the colour
		str1 = 152;
		str2 = 298;
		str3 = 388;
		squareColor = indigo;
		str2value = "INDIGO";
		break;
	    case 9: //blue
		//change string x values and the colour
		str1 = 160;
		str2 = 307;
		str3 = 380;
		squareColor = blue;
		str2value = "BLUE";
		break;
	}
	//set font colour
	c.setColor (white);
	c.drawString ("Move to the", str1, 80);
	c.drawString ("square", str3, 80);
	//draw the strings
	c.setColor (squareColor);
	c.drawString (str2value, str2, 80);
    }


    public boolean correctMove (char num, char pressed, int index, char correctLetter)  //checking to see if the correct keys were pressed
    {

	int x1 = 205, x2 = 325, x3 = 445; //blob locations (x1-x3 from left to right)
	int y1 = 140, y2 = 260, y3 = 380; //blob locations (y1-y3 from top to bottom)
	boolean correct = false; //return variable, starts as false
	c.setColor (white);
	c.setTextColor (white);
	if (pressed == correctLetter || (pressed - 32) == correctLetter) //uppercase and lowercase
	{
	    switch (num) //switchcase depending on the value of num
	    {
		case '1': //orange
		    if (pressed == 'q' || pressed == 'Q')
		    {
			blobX = x1; //change blob locations
			blobY = y1;
			correct = true; //returns true
		    }
		    break;
		case '2': //yellow
		    if (pressed == 'w' || pressed == 'W')
		    {
			blobX = x2;
			blobY = y1;
			correct = true;
		    }
		    break;
		case '3': //green
		    if (pressed == 'e' || pressed == 'E')
		    {
			blobX = x3;
			blobY = y1;
			correct = true;
		    }
		    break;
		case '4': //red
		    if (pressed == 'a' || pressed == 'A')
		    {
			blobX = x1;
			blobY = y2;
			correct = true;
		    }
		    break;
		case '5': //white
		    if (pressed == 's' || pressed == 'S')
		    {
			blobX = x2;
			blobY = y2;
			correct = true;
		    }
		    break;
		case '6': //turqoise
		    if (pressed == 'd' || pressed == 'D')
		    {
			blobX = x3;
			blobY = y2;
			correct = true;
		    }
		    break;
		case '7': //violet
		    if (pressed == 'z' || pressed == 'Z')
		    {
			blobX = x1;
			blobY = y3;
			correct = true;
		    }
		    break;
		case '8': //indigo
		    if (pressed == 'x' || pressed == 'X')
		    {
			blobX = x2;
			blobY = y3;
			correct = true;
		    }
		    break;
		case '9': //blue
		    if (pressed == 'c' || pressed == 'C')
		    {
			blobX = x3;
			blobY = y3;
			correct = true;
		    }
		    break;
	    }
	}
	return correct;
    }


    public void readLB ()  //method to read the leaderboard from the file, if printOut is true then it outputs, otherwise just reads
    { //read the leaderboard from a file called lb.txt
	try
	{ //try catch
	    BufferedReader input = new BufferedReader (new FileReader ("leaderboard.txt")); //create bufferedReader for reading # of lines
	    String title = input.readLine (); //get the header of leaderboard
	    input.readLine (); //take in the empty line
	    String tableHeader = input.readLine (); //get in the table header
	    int count = 0; //variable to store # of lines
	    while (input.readLine () != null)
	    { //while the line that is read in is not blank (there is data)
		count++; //add one to linecount
	    }
	    input.close ();
	    input = new BufferedReader (new FileReader ("leaderboard.txt")); //recreate bufferedReader for reading # of lines
	    title = input.readLine (); //get the title
	    input.readLine (); //take in empty line
	    tableHeader = input.readLine (); //take in table header
	    int numLines = Math.min (count, 10);
	    for (int i = 0 ; i < numLines ; i++)
	    { //read in first 10 values
		String str = input.readLine (); //take in the row
		lines [i] [0] = str.substring (0, 5).trim (); //get the first number
		lines [i] [1] = str.substring (7, 9).trim (); //get the intials
		lines [i] [2] = str.substring (18, str.length ()).trim (); //get the score
	    }
	}
	catch (IOException e)
	{ //check for error
	    c.println ("error reading file");
	}
    }


    public void writeLB ()
    {
	try //try catch for BufferedReader
	{
	    BufferedReader input = new BufferedReader (new FileReader ("leaderboard.txt")); //create bufferedReader for reading # of lines
	    String title = input.readLine (); //get the header of leaderboard
	    input.readLine (); //take in the empty line
	    String tableHeader = input.readLine (); //get in the table header
	    int count = 0; //variable to store # of lines
	    while (input.readLine () != null)
	    { //while the line that is read in is not blank (there is data)
		count++; //add one to linecount
	    }
	    input.close ();

	    String[] [] lbData = new String [count + 1] [3]; //array that stores all scores
	    readLB (); //get in the values into the lines[][] array (global array)
	    boolean inserted = false; //flag to check if you inserted the new value
	    for (int i = 0 ; i < (count + 1) ; i++) //loop to loop through the new array
	    {
		if (i == count) //if it is the last element, since lines[i] would throw an error
		{
		    if (inserted) //check if you have already inserted the value, if you have then you need to get the last value of lines[][]
		    {
			lbData [i] [0] = "" + (i + 1);
			lbData [i] [1] = lines [i - 1] [1];
			lbData [i] [2] = lines [i - 1] [2];
		    }
		    else //if you have not, then add the value to lbData
		    {
			lbData [i] [0] = (i + 1) + "";
			lbData [i] [1] = initials;
			lbData [i] [2] = score + "";
		    }
		}
		else //if it is not the last element
		{
		    if (lines [i] [2] != "")
		    {
			if (Integer.parseInt (lines [i] [2]) > score) //check if the current score is smaller than the previously stored score
			{
			    lbData [i] [0] = (i + 1) + "";
			    lbData [i] [1] = lines [i] [1];
			    lbData [i] [2] = lines [i] [2];
			}
			else //if it is larger, then you have to insert it
			{
			    inserted = true; //set to true
			    lbData [i] [0] = (i + 1) + ""; //add the data
			    lbData [i] [1] = initials;
			    lbData [i] [2] = score + "";
			}
		    }
		}
	    }
	    //outputting to the lb.txt file
	    PrintWriter output = new PrintWriter (new FileWriter ("leaderboard.txt"));
	    //output headers
	    output.println ("Simon Says Leaderboard");
	    output.println ("");
	    output.println ("Rank | Initials | Score");
	    //loop through each value of the lbData array
	    for (int i = 0 ; i < count + 1 ; i++)
	    {
		if (i < 9)
		{
		    output.println (lbData [i] [0] + "    | " + lbData [i] [1] + "       | " + lbData [i] [2]); //output to the file
		}
		else if (i <= 10)
		{
		    output.println (lbData [i] [0] + "   | " + lbData [i] [1] + "       | " + lbData [i] [2]); //output to the file
		}
	    }
	    output.close (); //close the printwriter
	}
	catch (IOException e)
	{
	    c.println ("error reading file");
	}
    }


    public void showLB ()
    {
	try //try catch for importing images
	{
	    BufferedImage menubg = ImageIO.read (new File ("images\\menubg.png")); //import menubg image
	    c.drawImage (menubg, 0, 0, null); //draw image as background
	}
	catch (IOException e)
	{
	}
	c.setFont (mcLarge);
	c.setColor (white); //title
	c.drawString ("SIMON SAYS", 155, 80);
	c.setFont (mcMed); //heading
	c.drawString ("Rank         Initials       Score", 90, 145);
	c.setFont (mc);
	readLB (); //receive data
	for (int x = 0 ; x < 10 ; x++)
	{
	    switch (x) //moving between colours for each row
	    {
		case 0:
		    c.setColor (red); //start with red, cycle through
		    break;
		case 1:
		    c.setColor (orange);
		    break;
		case 2:
		    c.setColor (yellow);
		    break;
		case 3:
		    c.setColor (green);
		    break;
		case 4:
		    c.setColor (turqoise);
		    break;
		case 5:
		    c.setColor (blue);
		    break;
		case 6:
		    c.setColor (indigo);
		    break;
		case 7:
		    c.setColor (violet);
		    break;
		case 8:
		    c.setColor (red);
		    break;
		case 9:
		    c.setColor (orange);
		    break;
		case 10:
		    c.setColor (yellow);
		    break;
		case 11:
		    c.setColor (green);
		    break;
	    }
	    c.drawString ((lines [x] [0]), 130, 175 + x * 25); //column 1 - ranks
	    c.drawString ((lines [x] [1]), 290, 175 + x * 25); //column 2 - initials
	    c.drawString ((lines [x] [2]), 440, 175 + x * 25); //column 3 - score
	}
	//vertical lines
	c.setColor (white);
	c.fillRect (210, 110, 5, 300);
	c.fillRect (400, 110, 5, 300);
	//return to menu:
	c.drawString ("Press any key to go back to the menu", 100, 470);
	c.getChar ();
    }


    public void goodbye ()
    {
	int timer = 3; //create timer variable
	while (timer > 0) //while the timer ticks down
	{
	    try //try catch to import image
	    {
		BufferedImage menubg = ImageIO.read (new File ("images\\menubg.png")); //import image
		c.drawImage (menubg, 0, 0, null); //display image as background
	    }
	    catch (IOException e)
	    {
	    }
	    //text
	    c.setFont (mcLarge);
	    c.setColor (white);
	    c.drawString ("SIMON SAYS", 155, 80); //title
	    c.setFont (mcMed);
	    c.drawString ("Thank you for playing!", 115, 200);
	    c.setFont (mcSmall);
	    c.drawString ("Made by Hazel and Nicole for ICS3U, 2020", 190, 480);
	    c.setFont (mcMed);
	    try //countdown timer
	    {
		c.drawString ("Exiting in " + timer + "", 230, 270); //timer print
		Thread.sleep (1000); //1 second wait
		timer -= 1; //remove 1 from timer
	    }
	    catch (InterruptedException e)
	    {
	    }
	}
	c.close (); //close the console
    }


    public static void main (String[] args)
    {
	SimonSays s = new SimonSays ();
	s.splashScreen (); //splash screen
	while (s.menuChoice != 4) //while the user does not choose to quit
	{
	    s.mainMenu (); //call menu
	    if (s.menuChoice == 1) //instructions option
	    {
		s.instructions ();
	    }
	    else if (s.menuChoice == 2) //play option
	    {
		s.levelSelect (); //choose the level
		if (s.levelChoice != 4) //level choice 4 is to return to main menu
		{
		    s.game (); //run the game
		}
	    }
	    else if (s.menuChoice == 3) //leaderboard option
	    {
		s.showLB (); //visual leaderboard
	    }
	}
	s.goodbye (); //when the quit choice is selected
    }
}


