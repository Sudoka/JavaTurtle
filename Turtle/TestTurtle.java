package Turtle;

import java.awt.Color;
import Turtle.Turtle;

public class TestTurtle
{
	private static void Triangle(Turtle t) {
		t.forward(100);
		t.right(120);

		t.forward(100);
		t.right(120);

		t.forward(100);
		t.right(60);
	}

	public static void main(String[] args) {

		Turtle myTurtle = new Turtle("Hello Turtle", 500, 500);
		myTurtle.setVisible(true);

		// myTurtle.setBackgroundColor(Color.orange);
		for(int i=0; i < 6; i++) {
			myTurtle.setPos(0, 0);
			Color newColor;
			switch(i) {
				case 0:
					newColor = Color.yellow;
					break;
				case 1:
					newColor = Color.green;
					break;
				case 2:
					newColor = Color.cyan;
					break;
				case 3:
					newColor = Color.blue;
					break;
				case 4:
					newColor = Color.magenta;
					break;
				case 5:
					newColor = Color.red;
					break;
				default:
					newColor = Color.black;
					break;
			}
			myTurtle.setForegroundColor(newColor);
			Triangle(myTurtle);
		}

		// myTurtle.setBackgroundColor(Color.orange);
	}
}
