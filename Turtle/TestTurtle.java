package Turtle;

import java.awt.Color;
import Turtle.Turtle;

public class TestTurtle
{
	public static void main(String[] args) {

		Turtle myTurtle = new Turtle("Hello Turtle", 500, 500);
		myTurtle.setVisible(true);
		
		myTurtle.TempDrawLine(400, 400, 250, 250);
		myTurtle.TempDrawLine(250, 250, 100, 400);
		myTurtle.TempDrawLine(100, 400, 400, 400);
		
		myTurtle.TempSetPenColor(Color.RED);
		
		myTurtle.TempFloodFill(250, 300);
		

		// Some random comment
	}
}
