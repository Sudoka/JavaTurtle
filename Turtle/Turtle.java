package Turtle;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.util.*;


/**
 * Class Turtle - a class to allow for simple graphical
 * drawing on a canvas.
 *
 * @author: Bruce Quig
 * date: 03-09-1999
 * @modified-by: Clay Reimann
 * date: 09-12-2012
 */
public class Turtle extends JFrame implements ComponentListener {
	// instance variables - replace the example below with your own
	// private Graphics2D context;
	private int x, y;
	private int width, height;
	private double heading;
	private Vector points;

	private abstract class DrawAction {
		protected int x;
		protected int y;
		protected boolean penDown;

		public DrawAction(int _x, int _y, boolean _penDown) {
			x = _x;
			y = _y;
			penDown = _penDown;
		}
		public DrawAction(int _x, int _y) {
			this(_x, _y, true);
		}
		public DrawAction() {
			this(0, 0, true);
		}

		public String toString() {
			return "DrawAction";
		}
	}
	private class DrawPoint extends DrawAction {
		public DrawPoint(int _x, int _y, boolean _penDown) {
			super(_x, _y, _penDown);
		}
		public DrawPoint(int _x, int _y) {
			this(_x, _y, true);
		}
		public DrawPoint() {
			this(0, 0, true);
		}

		public String toString() {
			return "("+x+", "+y+")";
		}
	}
	private class DrawContext extends DrawAction {
		protected Color bgColor;
		protected Color fgColor;

		public DrawContext() {
			super(0, 0, false);
			bgColor = null;
			fgColor = null;
		}
		public DrawContext(Color bg, Color fg) {
			super(0, 0, false);
			bgColor = bg;
			fgColor = fg;
		}

		public String toString() {
			return "bg: "+bgColor+" fg: "+fgColor;
		}
	}

	/**
	 * Constructor for objects of class DisplayTurtle
	 * @param title  title to appear in Turtle Frame
	 * @param width  the desired width for the canvas
	 * @param height  the desired height for the canvas
	 * @param bgClour  the desired background clour of the canvas
	 */
	public Turtle(String title, int w, int h, Color bgColor) {
		x = w / 2;	y = h / 2;
		this.width = w;	this.height = h;

		heading = 0.0;
		points = new Vector();
		points.add(new DrawPoint(x, y));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(title);
		this.setSize(width, height);
		this.getContentPane().setBackground(bgColor);

		this.addComponentListener(this);
	}

	/**
	 * Constructor for objects of class DisplayTurtle with default
	 * height, width and background color (600, 600, white).
	 * @param title  title to appear in Turtle Frame
	 */
	public Turtle(String title) {
		this(title, 600, 400, Color.white);
	}

	/**
	 * Constructor for objects of class DisplayTurtle with a default
	 * background color (white).
	 * @param title  title to appear in Turtle Frame
	 * @param width  the desired width for the canvas
	 * @param height  the desired height for the canvas
	 */
	public Turtle(String title, int width, int height) {
		this(title, width, height, Color.white);
	}

	/**
	 * Sets the canvas visibility and brings canvas to the front of screen
	 * when made visible. This method can also be used to bring an already
	 * visible canvas to the front of other windows.
	 *
	 * @param visible  boolean value representing the desired visibility of
	 * the canvas (true or false)
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// if(context == null)
		// 	context = (Graphics2D)this.getContentPane().getGraphics();
	}

	/**
	 * erases a given shape on the screen
	 * @param  shape  the shape object to be erased
	 */
	public void erase() {
		this.getContentPane().getGraphics().clearRect(0, 0, this.width, this.height);
	}

	/**
	 * sets the foreground color of the Turtle
	 *
	 * @param  newColor   the new color for the foreground of the Turtle
	 */
	public void setForegroundColor(Color newColor) {
		DrawContext newState = new DrawContext();
		newState.fgColor = newColor;
		points.add(newState);
	}

	/**
	 * sets the background color of the Turtle
	 *
	 * @param  newColor   the new color for the background of the Turtle
	 */
	public void setBackgroundColor(Color newColor) {
		DrawContext newState = new DrawContext();
		newState.bgColor = newColor;
		points.add(newState);
	}

	// /**
	//  * draws a String on the Turtle
	//  *
	//  * @param  text   the String to be displayed
	//	*/
	// public void drawString(String text) {
	// 	context.drawString(text, this.x, this.y);
	// }

	/**
	 * draws a line forward on the Turtle
	 *
	 * @param  dist   how far to move forward
	 */
	public void forward(int dist) {
		double ang = Math.toRadians(this.heading);
		int to_x = this.x + (int) (dist * Math.sin(ang));
		int to_y = this.y - (int) (dist * Math.cos(ang));
		points.add(new DrawPoint(to_x, to_y, true));

		this.x = to_x;
		this.y = to_y;
	}

	/**
	 * draws a line forward on the Turtle
	 *
	 * @param  dist   how far to move forward
	 */
	public void backward(int dist) {
		double ang = Math.toRadians(this.heading);
		int to_x = this.x - (int) (dist * Math.sin(ang));
		int to_y = this.y + (int) (dist * Math.cos(ang));
		points.add(new DrawPoint(to_x, to_y, true));

		this.x = to_x;
		this.y = to_y;
	}

	/**
	 * moves the pen location
	 *
	 * @param  x   new x coord
	 * @param  y	 new y coord
	 */
	public void setPos(int x, int y) {
		int to_x = (this.getWidth()  / 2) + x;
		int to_y = (this.getHeight() / 2) - y;
		this.x = to_x;
		this.y = to_y;
		DrawPoint newPos = new DrawPoint(to_x, to_y, false);
		points.add(newPos);
	}

	/**
	 * rotates the Turtle
	 *
	 * @param   deg     how many degrees to rotate
	 */
	public void left(double deg) {
		this.heading -= deg;
		if(this.heading < 0) {
			this.heading += 360;
		}
		// this.dumpTurtleStatus();
	}

	/**
	 * rotates the Turtle
	 *
	 * @param   deg     how many degrees to rotate
	 */
	public void right(double deg) {
		this.heading += deg;
		if(this.heading > 360) {
			this.heading -= 360;
		}
		// this.dumpTurtleStatus();
	}

	/**
	 * rotates the Turtle
	 *
	 * @param   deg     how many degrees to rotate
	 */
	public void setHeading(double deg) {
		this.heading = deg % 360;
		// this.dumpTurtleStatus();
	}

	private void walkDrawAction(Graphics2D context, DrawAction cur, int last, int i) {
		if(cur instanceof DrawPoint && last != -1) {
			if(cur.penDown) {
				// System.out.println(last + " to " + i + " " + cur);
				DrawPoint fr = (DrawPoint)points.get(last);
				DrawPoint to = (DrawPoint)cur;
				context.drawLine(fr.x, fr.y, to.x, to.y);
			}

		} else if(cur instanceof DrawContext) {
			if(((DrawContext)cur).fgColor != null) {
				context.setColor(((DrawContext)cur).fgColor);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D context = (Graphics2D)g;
		int lastPoint = -1;
		if(points.get(0) instanceof DrawPoint)
			lastPoint = 0;
		for(int i = 1; i < points.size(); i++) {
			DrawAction cur = (DrawAction)points.get(i);
			this.walkDrawAction(context, cur, lastPoint, i);
			if(cur instanceof DrawPoint) {
				lastPoint = i;
			} else if(cur instanceof DrawContext) {
				// System.out.println(cur);
				DrawContext status = (DrawContext)cur;
				if(status.bgColor != null) {
					context.setBackground(status.bgColor);
					context.clearRect(0, 0, this.width, this.height);

					// we have to loop back because when we set the background color we wipe
					// all of the previous lines we've drawn
					// System.out.println("recursing on points");
					int first = -1;
					if(points.get(0) instanceof DrawPoint)
						first = 0;
					DrawAction recurse;
					for(int j = 1; j < i; j++) {
						recurse = (DrawAction)points.get(j);
						this.walkDrawAction(context, recurse, first, j);
						if(recurse instanceof DrawPoint)
							first = j;
					}
				}

				if(status.fgColor != null)
					context.setColor(status.fgColor);
			}
			// System.out.println(fr+" -> "+to);
		}
	}

	/**
	 *		ComponentListener interface
	 */
	public void componentResized(ComponentEvent e) {
		double xScale =   this.getWidth() / ((double)this.width);
		double yScale =  this.getHeight() / ((double)this.height);

		int xDiff =  this.getWidth() - this.width;
		int yDiff = this.getHeight() - this.height;

		Iterator itr = points.iterator();
		while(itr.hasNext()) {
			DrawAction a = (DrawAction)itr.next();
			a.x += xDiff/2;
			a.y += yDiff/2;
		}
		this.height = this.getHeight(); this.width = this.getWidth();
		System.out.println("resized the turtle: "+ xDiff + ", " + yDiff);
		this.paint(this.getContentPane().getGraphics());
	}
	public void componentMoved(ComponentEvent e) {
		// System.out.println("moved the turtle");
	}
	public void componentHidden(ComponentEvent e) {
		// System.out.println("hid the turtle");
	}
	public void componentShown(ComponentEvent e) {
		// System.out.println("showed the turtle");
	}


	/**
	 * waits for a specified number of milliseconds before finishing.
	 * This provides an easy way to specify a small delay which can be
	 * used when producing animations.
	 * @param  milliseconds  the number
	 **/
	public void wait(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		}
		catch (Exception e) {
			// ignoring exception at the moment
		}
	}

}