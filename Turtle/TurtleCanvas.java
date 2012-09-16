package Turtle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class TurtleCanvas extends JPanel {

    private BufferedImage canvas;

    public TurtleCanvas(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLACK);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }


    public void drawLine(Color c, int x) {
        int color = c.getRGB();
        for (int y = 0; y < canvas.getHeight(); y++) {
           canvas.setRGB(x, y, color);
        }
        repaint();
    }

}