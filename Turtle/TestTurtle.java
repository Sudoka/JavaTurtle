package Turtle;

import java.awt.Color;
import Turtle.Turtle;

public class TestTurtle
{
	public static void drawFractal(Turtle t, int dist, int turn) {
		t.push();

		if(dist > 20) {
			t.forward(dist);
			t.left(turn);

			drawFractal(t, dist/2, turn);

			t.right(turn * 2);

			drawFractal(t, dist/2, turn);
		}

		t.pop();
		t.printDebug();
	}

	public static void main(String[] args) {

		Turtle myTurtle = new Turtle("Hello Turtle", 500, 500, true);
		myTurtle.setVisible(true);


		drawFractal(myTurtle, 100, 20);

		myTurtle.printDebug();
	}
}
