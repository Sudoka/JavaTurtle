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

	public Turtle(String title, int width, int height) {
		super(title);

		canvas = new TurtleCanvas(width, height);
		states = new Stack <TurtleState> ();
		states.push(new TurtleState());

		this.setSize(width, height);
		this.add(canvas);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			x = y = 0;
			heading  = 0;
			bgColor  = Color.black;
			penColor = Color.white;
			penDown  = true;
			showTurtle = true;
			penMode = PenMode.PAINT;
			winMode = WindowMode.WINDOW;
		}

		public TurtleState(int x_, int y_, float head, Color bg, Color pen,
							boolean dn, boolean show, PenMode m, WindowMode w)
		{
			x = x_;
			y = y_;
			heading  = head;
			bgColor  = bg;
			penColor = pen;
			penDown  = dn;
			showTurtle = show;
			penMode = m;
			winMode = w;
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
	}

	//
	//	Context Manipulation
	//
	public void push() {
		TurtleState cur = states.peek();
		states.push( new TurtleState(cur) );
	}
	public void pop() {
		states.pop();
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

		canvas.drawLine(x1, y1, x2, y2);

		cur.x = x2;
		cur.y = y2;
	}
	public void backward(int dist) {
		forward(-dist);
	}
	public void left(float deg) {
		TurtleState cur = states.peek();
	}
	public void right(float deg) {
		left(-deg);
	}


	//
	//	Turtle Manipulation
	//
	public void setXY(int x, int y) {

	}
	public void setHeading(float deg) {

	}
	public void home() {

	}
	public void setPenDown(boolean isDown) {

	}
	public void setPenMode(PenMode mode) {

	}
	public void setPenSize(int size) {

	}
	public void setPenColor(int colorNum) {

	}
	public void setPenRGB(int r, int g, int b) {

	}
	public void setBackgroundColor(int colorNum) {

	}
	public void setBackgroundColor(int r, int g, int b) {

	}


	//
	//	Other Drawing Functions
	//
	public void arc(int radius, float rads) {

	}
	public void fill() {

	}
	public void drawText(String text) {

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
}