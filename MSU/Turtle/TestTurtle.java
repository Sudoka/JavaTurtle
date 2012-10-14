package MSU.Turtle;

import java.awt.Color;
import MSU.Turtle.Turtle;

public class TestTurtle
{
	/**
	 * to fractal :size
	 * 	if lessp :size 4 [stop]
	 * 	forward :size
	 * 	left 20
	 * 	fractal :size/2
	 * 	right 40
	 * 	fractal :size/2
	 * 	left 20
	 * 	back :size
	 * end
	 */
	public static void drawFractal(Turtle t, int dist, int turn) {
		t.push();

		if(dist > 4) {
			t.forward(dist);
			t.left(turn);

			drawFractal(t, 2*dist/3, turn);

			t.right(turn * 2);

			drawFractal(t, 2*dist/3, turn);
		}

		t.pop();
	}

	/**
	 * def f(length, depth):
	 *    if depth == 0:
	 *      forward(length)
	 *    else:
	 *      f(length/3, depth-1)
	 *      right(60)
	 *      f(length/3, depth-1)
	 *      left(120)
	 *      f(length/3, depth-1)
	 *      right(60)
	 *      f(length/3, depth-1)
	 */
	public static void drawSnowflakeSide(Turtle t, int len, int d) {
		// t.push();
		if(d == 0) {
			t.forward(len);
			// t.pop();
			return ;
		}
		drawSnowflakeSide(t, len/3, d-1);
		t.right(60);
		drawSnowflakeSide(t, len/3, d-1);
		t.left(120);
		drawSnowflakeSide(t, len/3, d-1);
		t.right(60);
		drawSnowflakeSide(t, len/3, d-1);

		// t.pop();
	}
	public static void drawSnowflake(Turtle t, int len, int d) {
		drawSnowflakeSide(t, len, d);
		t.left(120);
		drawSnowflakeSide(t, len, d);
		t.left(120);
		drawSnowflakeSide(t, len, d);
	}

	public static void main(String[] args) {

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Logo Turtle");

		Turtle myTurtle = new Turtle("Hello Turtle", 500, 500, true);
		// myTurtle.clearScreen();

		myTurtle.setPenDown(false);
		myTurtle.setXY(150, -150);
		myTurtle.setPenDown(true);
		myTurtle.setXY(0, 0);
		myTurtle.setXY(-150, -150);
		myTurtle.setXY(150, -150);
		myTurtle.setPenColor(4);
		myTurtle.setPenDown(false);
		myTurtle.setXY(0, -100);
		myTurtle.fill();

		myTurtle.setXY(0, 0);
		myTurtle.setPenDown(true);
		
		for(int i = 0; i < 8; i++) {
			double f = 0.125; double step = (2*Math.PI/3);
		   	int red   = (int)(Math.sin(f*i*4 +      0) * 127) + 128;
		   	int green = (int)(Math.sin(f*i*4 +   step) * 127) + 128;
		   	int blue  = (int)(Math.sin(f*i*4 + 2*step) * 127) + 128;
			myTurtle.setPenRGB(red, green, blue);

			if(i < 3 || i >= 6)
				drawFractal(myTurtle, 80, 25);
			myTurtle.right(45);
		}

        myTurtle.setPenDown(false);
        myTurtle.setXY(0, 0);
        myTurtle.setPenDown(true);

        myTurtle.setPenRGB(255, 255, 255);
        myTurtle.setPenSize(5);
        myTurtle.setHeading(-45);
        myTurtle.arc(225, 90);
        
        myTurtle.setPenMode(PenMode.ERASE);
        myTurtle.setHeading(-15);
        myTurtle.arc(225, 30);
        
        myTurtle.setPenMode(PenMode.PAINT);

        myTurtle.setPenDown(false);
        myTurtle.setXY(0, 100);
        myTurtle.setPenDown(true);
        myTurtle.label("Text!");

        // myTurtle.clean();
        // myTurtle.forward(50);

        myTurtle.setBackgroundColor(10);
        // myTurtle.clearScreen();
        // myTurtle.forward(100);
	}
}
