package Turtle;

import java.math.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @class TurtleCanvas - this manages the status of the actual pixels which
 *				get drawn on screen
 *
 * @author: Clay Reimann
 * date: 09-16-2012
 */
public class TurtleCanvas extends JPanel {

    private BufferedImage canvas;

    protected Color penColor;
    protected Color bgColor;
    protected PenMode mode;
    protected boolean showTurtle;


    public TurtleCanvas(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        mode = PenMode.PAINT;
        bgColor  = Color.black;
        penColor = Color.white;
        showTurtle = true;

        fillCanvas();
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas() {
        int color = bgColor.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }


    public void drawLine(int x1, int y1, int x2, int y2) {
        int color = penColor.getRGB();

        // Should implement: http://en.wikipedia.org/wiki/Bresenham's_line_algorithm


        repaint();
    }

    public void drawArc() {
    	// Should implement: http://en.wikipedia.org/wiki/Midpoint_circle_algorithm
    }



}