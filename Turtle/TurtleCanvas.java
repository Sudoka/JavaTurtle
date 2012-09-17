package Turtle;

import java.math.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.Queue;
import java.util.LinkedList;

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

    public void fillFromPoint(int x, int y) {

    	// flood fill

    	repaint();
    }


    public void drawLine(int x0, int y0, int x1, int y1) {
        int color = penColor.getRGB();

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
			int temp = x0;
			x0 = y0;
			y0 = temp;
			
			temp = x1;
			x1 = y1;
			y1 = temp;
		}
		if (x0 > x1) {
			int temp = x0;
			x0 = x1;
			x1 = temp;
			
			temp = y0;
			y0 = y1;
			y1 = temp;
		}
		
		int deltax = x1 - x0;
		int deltay = Math.abs(y1 - y0);
		double error = 0;
		double deltaerr = (double)deltay / (double)deltax;
		int ystep;
		int y = y0;
		ystep = (y0 < y1) ? 1 : -1;
		
		for (int x = x0; x <= x1; x++)
		{
			if (steep)
			{
				canvas.setRGB(y, x, color);
			}
			else
			{
				canvas.setRGB(x, y, color);
			}
			error += deltaerr;
			
			if (error > .5)
			{
				y += ystep;
				error--;
			}
		}


        repaint();
    }

    public void drawText(String text, int x, int y) {
    	// put some text on the bitmap
    }

    public void drawArc(int x, int y, int radius, double radStart, double radArc) {
    	// Should implement: http://en.wikipedia.org/wiki/Midpoint_circle_algorithm
    	
    	int f = 1 - radius;
    }
    
    
    public void floodFill(int x, int y) {
		int emptyColor = canvas.getRGB(x, y);
		int pColor = penColor.getRGB();
		int h = canvas.getHeight();
		int w = canvas.getWidth();
		
		boolean[][] visited = new boolean[w][h];
		
		Queue<Point> q = new LinkedList<Point>();
		
		q.add(new Point(x, y));
		
		while (!q.isEmpty())
		{
			Point p = q.remove();			
			if (visited[p.x][p.y]) continue;
			else visited[p.x][p.y] = true;
			if (p.x >= 0 && p.y >= 0 && p.x < w && p.y < h)
			{
				if (canvas.getRGB(p.x, p.y) == emptyColor)
				{
					canvas.setRGB(p.x, p.y, pColor);
					
					//System.out.println("Painted (" + p.x + ", " + p.y + ")");
					
					if (!visited[p.x-1][p.y]) q.add(new Point(p.x-1, p.y));
					if (!visited[p.x][p.y-1]) q.add(new Point(p.x, p.y-1));
					if (!visited[p.x+1][p.y]) q.add(new Point(p.x+1, p.y));
					if (!visited[p.x][p.y+1]) q.add(new Point(p.x, p.y+1));
				} 
			}
		}
	}
    
    
    /*
    public void floodFill(int x, int y) {
		int emptyColor = canvas.getRGB(x, y);
		
		floodFillR(x, y, emptyColor);
	}
	
	private void floodFillR(int x, int y, int emptyColor) {
		if (canvas.getRGB(x, y) != emptyColor) return;
		if (x < 0 || y < 0 || x > canvas.getWidth() - 1 || y > canvas.getWidth() - 1) return;
		
		canvas.setRGB(x, y, penColor.getRGB());
		
		floodFillR(x-1, y, emptyColor);
		floodFillR(x+1, y, emptyColor);
		floodFillR(x, y-1, emptyColor);
		floodFillR(x, y+1, emptyColor);
	}
	*/
    
    public void setBackgroundColor(Color _bgColor) {
		bgColor = _bgColor;
		
		repaint();
	}

	public void setPenColor(Color _penColor) {
		penColor = _penColor;
	}

}
