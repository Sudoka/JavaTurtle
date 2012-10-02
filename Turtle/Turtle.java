// Need a pen object that is shared between Turtle and TurtleCanvas.
// Not sure what will need to be done for state information for the pen
// color/mode/width, but you understand your state stack implementation
// better than I do.

// Implemented drawText to use the Graphics2D, and reimplemented
// drawLine to also use it.  Haven't worked on the arc at all yet,
// since Java's drawArc is idiotic, as mentioned.

// Stroke is kinda confusing but I'll figure it out soon.


package Turtle;

//import com.apple.eawt.Application;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.UIManager;

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
	private int width;
	private int height;

	// debug stuff:
	protected boolean debug;
	private Vector <String> debugStates;

	public void printDebug() {
		for(String s : debugStates) {
			System.out.println(s);
		}
	}

	public Turtle(String title, int w, int h, boolean dbg) {
		super(title);

		width = w;
		height = h;
		canvas = new TurtleCanvas(w, h);
		debugStates = new Vector <String> ();
		states = new Stack <TurtleState> ();
		states.push(new TurtleState(w/2, h/2, 0));
		debug = dbg;

		this.add(canvas);

		// Do other swingy things
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// From http://stackoverflow.com/questions/3154638/setting-java-swing-application-name-on-mac
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Logo Turtle");
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);

		Insets dec = this.getInsets();
		this.setSize(this.width + dec.left + dec.right, this.height + dec.top + dec.bottom);
	}

	public Turtle(String title, int width, int height) {
		this(title, width, height, false);
	}

	private class TurtleState {
		protected int x;
		protected int y;
		protected double heading;

		protected PenState pen;
		protected WindowMode winMode;

		public TurtleState() {
			this(0, 0, 0);
		}

		public TurtleState(int x_, int y_, float head)
		{
			x = x_;
			y = y_;
			heading  = head;
			pen = new PenState();
			winMode = WindowMode.WINDOW;
		}
		public TurtleState(TurtleState old) {
			this.x = old.x;
			this.y = old.y;
			this.heading  = old.heading;
			this.pen = old.pen;
			this.winMode = old.winMode;
		}

		public String toString() {
			String sp = "";
			sp += ((int)heading / 100) == 0 ? " " : "";
			sp += ((int)heading /  10) == 0 ? " " : "";
			return "("+x+", "+y+") h: "+sp+heading+" pd: "+pen.penDown;
		}
	}
    
    protected class PenState {
        protected Color bgColor;
        protected Color penColor;
        protected boolean penDown;
        protected PenMode penMode;
        protected boolean showTurtle;
        protected int     penWidth;

        public PenState() {
            this(Color.black, Color.white, true, false, PenMode.PAINT, 1);
        }

        public PenState(Color b, Color p, boolean dn, boolean st, PenMode mode, int width) {
            this.bgColor = b;
            this.penColor = p;
            this.penDown = dn;
            this.showTurtle = st;
            this.penMode = mode;
            this.penWidth = width;
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
		cur.pen.showTurtle = show;
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

		if(cur.pen.penDown)
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
		int y2 = (canvas.getHeight()/2) - y;

		if(cur.pen.penDown)
			canvas.drawLine(x1, y1, x2, y2);

		cur.x = x2;
		cur.y = y2;

		if(debug)
			debugStates.add( cur.toString() );
	}
	public void setHeading(float deg) {
		TurtleState cur = states.peek();
		
        deg += 270;
        deg %= 360;
        deg = -deg;

		cur.heading = deg;
	}
	public void home() {
		TurtleState cur = states.peek();
		if(cur.pen.penDown)
			canvas.drawLine(cur.x, cur.y, 0, 0);

		cur.x = 0;
		cur.y = 0;
	}


	public void setPenDown(boolean isDown) {
		TurtleState cur = states.peek();
		cur.pen.penDown = isDown;
        
        canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, false);
	}
	public void setPenMode(PenMode mode) {
		TurtleState cur = states.peek();
		cur.pen.penMode = mode;
        
		canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, false);
	}
	public void setPenSize(int size) {
		TurtleState cur = states.peek();
        cur.pen.penWidth = size;
        
        canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, false);
	}

	public void setPenColor(int colorNum) {
		TurtleState cur = states.peek();
		Color c = Color.white; // colorNums.get(i);

		cur.pen.penColor = c;
		canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, false);
	}
	public void setPenRGB(int r, int g, int b) {
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);

		cur.pen.penColor = c;
		canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, false);
	}

	public void setBackgroundColor(int colorNum) {
		TurtleState cur = states.peek();
		Color c = Color.black; // colorNums.get(i);

		cur.pen.bgColor = c;
		canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, true);
	}
	public void setBackgroundColor(int r, int g, int b) {
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);

		cur.pen.bgColor = c;
		canvas.updatePen(cur.pen.bgColor, cur.pen.penColor, cur.pen.penMode, cur.pen.penWidth, true);
	}


	//
	//	Other Drawing Functions
	//
	public void arc(int radius, double degArc) {
		TurtleState cur = states.peek();
		if(cur.pen.penDown)
			canvas.drawArc(cur.x, cur.y, radius, cur.heading, -1 * degArc);
	}
	public void fill() {
		TurtleState cur = states.peek();
		canvas.floodFill(cur.x, cur.y);
	}
	public void label(String text) {
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

    public void tempDrawArc(int r, double degArc) {
        TurtleState cur = states.peek();

        canvas.drawArc(cur.x, cur.y, r, cur.heading, degArc);
    }

	public void tempFloodFill(int x, int y) {
		canvas.floodFill(x, y);
	}

	public void tempSetPenColor(Color c)
	{
		this.setPenRGB(c.getRed(), c.getGreen(), c.getBlue());
	}
}
