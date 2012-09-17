package Turtle;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.util.*;

/**
 * @class Turtle - 	an inteface to allow simulated turtle graphics
 * 			manages the interaction between the client and the actual
 *			canvas (buffer)
 *
 * @author: Clay Reimann
 * date: 09-16-2012
 */
public class Turtle extends JFrame implements ComponentListener {
	private Stack <TurtleState> states;
	private TurtleCanvas canvas;

	// debug stuff:
	protected boolean debug;
	private Vector <String> debugStates;

	public void printDebug() {
		for(String s : debugStates) {
			System.out.println(s);
		}
	}

	public Turtle(String title, int width, int height, boolean dbg) {
		super(title);

		canvas = new TurtleCanvas(width, height);
		debugStates = new Vector <String> ();
		states = new Stack <TurtleState> ();
		states.push(new TurtleState(width/2, height/2, 0));
		debug = dbg;

		this.setSize(width, height);
		this.add(canvas);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Turtle(String title, int width, int height) {
		this(title, width, height, false);
	}

	private class TurtleState {
		protected int x;
		protected int y;
		protected double heading;
		protected Color bgColor;
		protected Color penColor;
		protected boolean penDown;
		protected boolean isRadians;
		protected boolean showTurtle;
		protected PenMode penMode;
		protected WindowMode winMode;

		public TurtleState() {
			this(0, 0, 0);
		}

		public TurtleState(int x_, int y_, float head)
		{
			x = x_;
			y = y_;
			heading  = head;
			bgColor  = Color.black;
			penColor = Color.white;
			penDown  = true;
			showTurtle = true;
			penMode = PenMode.PAINT;
			winMode = WindowMode.WINDOW;
		}
		public TurtleState(TurtleState old) {
			this.x = old.x;
			this.y = old.y;
			this.heading  = old.heading;
			this.bgColor  = old.bgColor;
			this.penColor = old.penColor;
			this.penDown  = old.penDown;
			this.showTurtle = old.showTurtle;
			this.penMode = old.penMode;
			this.winMode = old.winMode;
		}

		public String toString() {
			String sp = "";
			sp += ((int)heading / 100) == 0 ? " " : "";
			sp += ((int)heading /  10) == 0 ? " " : "";
			return "("+x+", "+y+") h: "+sp+heading+" pd: "+penDown;
		}
	}

	//
	//	Context Manipulation
	//
	public void push() {
		TurtleState cur = states.peek();
		states.push( new TurtleState(cur) );

		if(debug)
			debugStates.add( "Pushing: "+cur );
	}
	public void pop() {
		states.pop();

		if(debug)
			debugStates.add("Popping to: "+states.peek());
	}

	public void clearScreen() {

	}
	public void clean() {

	}
	public void showTurtle(boolean show) {
		TurtleState cur = states.peek();
		cur.showTurtle = show;
	}
	public void setTurtleMode(WindowMode mode) {
		TurtleState cur = states.peek();
		cur.winMode = mode;
	}
	public void setPaletteColor(int colorNum, int r, int g, int b) {

	}


	//
	//	Turtle Movement
	//
	public void forward(int dist) {
		TurtleState cur = states.peek();

		int x1, x2, y1, y2;
		double h;

		x1 = cur.x; y1 = cur.y;
		h = Math.toRadians(cur.heading);

		x2 = x1 + (int)(dist * Math.sin(h));
		y2 = y1 - (int)(dist * Math.cos(h));

		if(cur.penDown)
			canvas.drawLine(x1, y1, x2, y2);

		cur.x = x2;
		cur.y = y2;

		if(debug)
			debugStates.add( cur.toString() );
	}
	public void backward(int dist) {
		forward(-dist);
	}
	public void left(float deg) {
		TurtleState cur = states.peek();
		cur.heading -= (deg % 360);
		if(cur.heading < 0)
			cur.heading += 360;

		if(debug)
			debugStates.add( cur.toString() );
	}
	public void right(float deg) {
		left(-deg);
	}


	//
	//	Turtle Manipulation
	//
	public void setXY(int x, int y) {
		TurtleState cur = states.peek();
		int x1 = cur.x;
		int y1 = cur.y;
		int x2 = (canvas.getWidth() /2) + x;
		int y2 = (canvas.getHeight()/2) + y;

		// System.out.println(x+" "+y+" "+canvas.getWidth()+" "+canvas.getHeight());
		// System.out.println(x1+" "+y1+" "+x2+" "+y2);

		if(cur.penDown)
			canvas.drawLine(x1, y1, x2, y2);

		cur.x = x2;
		cur.y = y2;

		if(debug)
			debugStates.add( cur.toString() );
	}
	public void setHeading(float deg) {
		TurtleState cur = states.peek();
		cur.heading = (deg % 360);
	}
	public void home() {
		TurtleState cur = states.peek();
		if(cur.penDown)
			canvas.drawLine(cur.x, cur.y, 0, 0);

		cur.x = 0;
		cur.y = 0;
	}
	public void setPenDown(boolean isDown) {
		TurtleState cur = states.peek();
		cur.penDown = isDown;
	}
	public void setPenMode(PenMode mode) {
		TurtleState cur = states.peek();
		cur.penMode = mode;
		canvas.mode = mode;
	}
	public void setPenSize(int size) {
		TurtleState cur = states.peek();
	}
	public void setPenColor(int colorNum) {
		TurtleState cur = states.peek();
		Color c = Color.white; // colorNums.get(i);
		cur.penColor = c;
		canvas.penColor = c;
	}
	public void setPenRGB(int r, int g, int b) {
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);
		cur.penColor = c;
		canvas.penColor = c;
	}
	public void setBackgroundColor(int colorNum) {
		TurtleState cur = states.peek();
		Color c = Color.black; // colorNums.get(i);
		cur.bgColor = c;
		canvas.bgColor = c;
	}
	public void setBackgroundColor(int r, int g, int b) {
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);
		cur.bgColor = c;
		canvas.bgColor = c;
	}


	//
	//	Other Drawing Functions
	//
	public void arc(int radius, double rads) {
		TurtleState cur = states.peek();
		if(cur.penDown)
			canvas.drawArc(cur.x, cur.y, radius, rads);
	}
	public void fill() {
		TurtleState cur = states.peek();
		canvas.fillFromPoint(cur.x, cur.y);
	}
	public void drawText(String text) {
		TurtleState cur = states.peek();
		canvas.drawText(text, cur.x, cur.y);
	}


	/**
	 *		ComponentListener interface
	 */
	public void componentResized(ComponentEvent e) {
		System.out.println("resized the turtle");
	}
	public void componentMoved(ComponentEvent e) {
		System.out.println("moved the turtle");
	}
	public void componentHidden(ComponentEvent e) {
		System.out.println("hid the turtle");
	}
	public void componentShown(ComponentEvent e) {
		System.out.println("showed the turtle");
	}

	/**
	 *		Other Swing stuff
	 */
	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}

	/**
	 *		Test harnass-y stuff
	 */
	public void tempDrawLine(int x0, int y0, int x1, int y1) {
		canvas.drawLine(x0, y0, x1, y1);
	}

	public void tempFloodFill(int x, int y) {
		canvas.floodFill(x, y);
	}

	public void tempSetPenColor(Color c)
	{
		canvas.setPenColor(c);
	}
}
