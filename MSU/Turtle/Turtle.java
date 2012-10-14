// Need a pen object that is shared between Turtle and TurtleCanvas.
// Not sure what will need to be done for state information for the pen
// color/mode/width, but you understand your state stack implementation
// better than I do.

// Implemented drawText to use the Graphics2D, and reimplemented
// drawLine to also use it.  Haven't worked on the arc at all yet,
// since Java's drawArc is idiotic, as mentioned.

// Stroke is kinda confusing but I'll figure it out soon.


package MSU.Turtle;

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
	private class TurtleState {
		protected int x;
		protected int y;
		protected double heading;

		protected Color bgColor;
		protected Color penColor;
		protected boolean penDown;
		protected int penWidth;
		
		protected PenMode penMode;
		protected WindowMode winMode;
		protected boolean showTurtle;
		
		public TurtleState() {
			this(0, 0, 0);
		}

		public TurtleState(int x_, int y_, float head)
		{
			x = x_;
			y = y_;
			heading  = head;
			
			bgColor = Color.black;
            penColor = Color.white;
            penDown = true;
            penWidth = 1;

            showTurtle = true;
            penMode = PenMode.PAINT;
			winMode = WindowMode.WINDOW;
		}
		public TurtleState(TurtleState old) {
			this.x = old.x;
			this.y = old.y;
			this.heading  = old.heading;

			this.bgColor = old.bgColor;
			this.penColor = old.penColor;
			this.penDown = old.penDown;
			this.penWidth = old.penWidth;

			this.showTurtle = old.showTurtle;
			this.winMode = old.winMode;
			this.penMode = old.penMode;
		}

		public String toString() {
			String sp = "";
			sp += ((int)heading / 100) == 0 ? " " : "";
			sp += ((int)heading /  10) == 0 ? " " : "";
			return "("+x+", "+y+") h: "+sp+heading+" pd: "+penDown;
		}
	}

	private ArrayList <Color> palette;
	private Stack <TurtleState> states;
	private TurtleCanvas canvas;
	private int width;
	private int height;
	private boolean onScreen;

	//---- debug stuff:
	protected boolean debug;
	private Vector <String> debugStates;

	public void printDebug() {
		for(String s : debugStates) {
			System.out.println(s);
		}
	}

	public Turtle() {
		this("Logo Turtle Graphics", 500, 500, false);
	}

	public Turtle(String title, int width, int height) {
		this(title, width, height, false);
	}

	public Turtle(String title, int w, int h, boolean dbg) {
		super(title);

		width = w;
		height = h;
		onScreen = false;
		canvas = new TurtleCanvas(w, h);
		states = new Stack<TurtleState> ();
		states.push(new TurtleState(0, 0, 0));

		palette = new ArrayList<Color> ();
		palette.add(Color.black);
		palette.add(Color.blue);
		palette.add(Color.green);
		palette.add(Color.cyan);
		palette.add(Color.red);
		palette.add(Color.magenta);
		palette.add(Color.yellow);
		palette.add(Color.white);
		palette.add(new Color(160, 82, 45));
		palette.add(new Color(210, 180, 140));
		palette.add(new Color(34, 139, 34));
		palette.add(new Color(0, 255, 255));
		palette.add(new Color(250, 128, 114));
		palette.add(new Color(128, 0, 128));
		palette.add(Color.orange);
		palette.add(Color.gray);		

		debug = dbg;
		debugStates = new Vector <String> ();

		this.add(canvas);
		// Do other swingy things
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// From http://stackoverflow.com/questions/3154638/setting-java-swing-application-name-on-mac
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Logo Turtle");
	}
	public boolean onScreen() {
		return this.onScreen;
	}
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.onScreen = visible;

		Insets chrome = this.getInsets();
		this.setSize(this.width + chrome.left + chrome.right, this.height + chrome.top + chrome.bottom);
	}
	public void showTurtleWindow() {
		if(!this.onScreen())
			this.setVisible(true);
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
		if(!this.onScreen()) 
			this.setVisible(true);

		this.states.clear();
		this.states.push(new TurtleState());

		this.canvas.fillCanvas();
	}
	public void clean() {
		if(!this.onScreen()) 
			this.setVisible(true);

		this.canvas.fillCanvas();
	}
	public void showTurtle(boolean show) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		cur.showTurtle = show;

		canvas.showTurtle = show;
	}
	public void setWindowMode(WindowMode mode) {
		TurtleState cur = states.peek();
		cur.winMode = mode;
	}
	public void setPaletteColor(int colorNum, int r, int g, int b) {
		Color newColor = new Color(r, g, b);
		if(colorNum >= palette.size()) {
			palette.add(newColor);
		} else if(colorNum > 0) {
			palette.set(colorNum, newColor);
		}
	}


	//
	//	Turtle Movement
	//
	public void forward(int dist) {
		showTurtleWindow();
		TurtleState cur = states.peek();

		int x1, x2, y1, y2;
		int xOff = canvas.getWidth() / 2;
		int yOff = canvas.getHeight() / 2;
		double h;

		x1 = cur.x; y1 = cur.y;
		h = Math.toRadians(cur.heading+180);

		x2 = x1 + (int)(dist * Math.sin(h));
		y2 = y1 - (int)(dist * Math.cos(h));

		if(cur.penDown)
			canvas.drawLine(xOff + x1, yOff - y1, xOff + x2, yOff - y2);

		cur.x = x2;
		cur.y = y2;

		if(debug)
			debugStates.add( cur.toString() );
	}
	public void backward(int dist) {
		forward(-dist);
	}
	public void left(float deg) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		cur.heading += (deg % 360);
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
		showTurtleWindow();
		TurtleState cur = states.peek();
		int xOff = canvas.getWidth() / 2;
		int yOff = canvas.getHeight() / 2;
		int x1 = xOff + cur.x;
		int y1 = yOff - cur.y;
		int x2 = xOff + x;
		int y2 = yOff - y;

		if(cur.penDown)
			canvas.drawLine(x1, y1, x2, y2);

		cur.x = x;
		cur.y = y;
		if(debug)
			debugStates.add( cur.toString() );
	}
	public void setHeading(float deg) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		
		deg += 270;
        deg %= 360;
        deg = -deg;

		cur.heading = deg;
	}
	public void home() {
		showTurtleWindow();
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
		canvas.penMode = mode;
	}
	public void setPenSize(int size) {
		TurtleState cur = states.peek();
        cur.penWidth = size;
        
        canvas.setPenSize(size);
	}

	public void setPenColor(int colorNum) {
		if(colorNum < palette.size()) {
			TurtleState cur = states.peek();
			Color c = palette.get(colorNum);

			cur.penColor = c;
			canvas.setPenColor(c);
		}
	}
	public void setPenRGB(int r, int g, int b) {
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);

		cur.penColor = c;
		canvas.setPenColor(c);
	}

	public void setBackgroundColor(int colorNum) {
		if(colorNum < palette.size()) {
			showTurtleWindow();
			TurtleState cur = states.peek();
			Color c = palette.get(colorNum);

			cur.bgColor = c;
			canvas.setBackgroundColor(c);
		}
	}
	public void setBackgroundRGB(int r, int g, int b) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		Color c = new Color(r, g, b);

		cur.bgColor = c;
		canvas.setBackgroundColor(c);
	}


	//
	//	Other Drawing Functions
	//
	public void arc(int radius, double degArc) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		int xOff = canvas.getWidth() / 2;
		int yOff = canvas.getHeight() / 2;
		if(cur.penDown)
			canvas.drawArc(xOff + cur.x, yOff + cur.y, radius, cur.heading, -1 * degArc);
	}
	public void fill() {
		showTurtleWindow();
		TurtleState cur = states.peek();
		int xOff = canvas.getWidth() / 2; 
		int yOff = canvas.getHeight() / 2;
		canvas.floodFill(xOff + cur.x, yOff - cur.y);
	}
	public void label(String text) {
		showTurtleWindow();
		TurtleState cur = states.peek();
		int xOff = canvas.getWidth() / 2;
		int yOff = canvas.getHeight() / 2;
		canvas.drawText(text, xOff + cur.x, yOff - cur.y);
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
