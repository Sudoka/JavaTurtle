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
	private Stack states;
	private TurtleCanvas canvas;

	public Turtle(String title, int width, int height) {
		super(title);
		this.setSize(width, height);

		canvas = new TurtleCanvas(width, height);

		this.add(canvas);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas.drawLine(Color.WHITE, 60);
	}

	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
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
}