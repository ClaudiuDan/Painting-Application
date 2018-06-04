import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class DisplayPanel extends JPanel {
	private Controller controller;
	private BufferedImage image;
	
	// Constructor of the class, initialises some variables
	DisplayPanel (int sizeX, int sizeY, Controller controller) {
		super();
		
		// Gives a better aspect to the JComponents
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		setSize (sizeX, sizeY);
		points = controller.getPoints();
		this.controller = controller;
		init();
		setSectors();
	}
	private int nr;
	
	// Sets the size, colour, listener, basic number of sectors
	void init () {
		setBackground(Color.BLACK);
		oX = getWidth() / 2 - 180;
		oY = getHeight() / 2 + 60;
		nr = 10;
		color = Color.WHITE;
		MyMouseListener mouseListener = new MyMouseListener ();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		
	}

	private double posX, posY;
	private int oX, oY, penSize = 10;
	private ArrayList<MyPoint> points;
	private ArrayList<Point> sectors = new ArrayList<Point>();
	final private int R = 400; 
	private double rotation;
	
	// Sets the sectors, fills the array list
	void setSectors () {
		double dist = 0;
		rotation = 2 * Math.PI / nr;
		
		// Sets the position of the outer point of all the sectors 
		for (int i = 0; (i < nr && nr > 1); i++)
		{
			posX = oX + R * Math.sin(dist);
			posY = oY + R * Math.cos(dist);
			Point point = new Point();
			point.setLocation(posX, posY);
			sectors.add(point);
			dist += rotation;
			
		}
	}
	private int pointX, pointY;
	private int order;
	
	// Paint method for drawing sectors and drawings
	@Override 
	public void paint (Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		RenderingHints hint = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHints(hint);
		draw(g2D, 0);
		g2D.setColor(Color.white);
		g2D.setStroke(new BasicStroke(1));
		
		// Draws the sectors if supposed to
		if (showSectors == true) {
			for (Point p : sectors) {
				g2D.drawLine(p.x, p.y, oX, oY);
			}
		}
	}
	
	// Draws the drawings made by the user
	private void draw (Graphics2D g2D, int p) {
	
		if (points.size() > 1)		
			// Loops through all the stored points
			for (int j = 1; j < points.size(); j++) {
				g2D.setColor(points.get(j).color);
					for (int i = 0; i < nr; i++) {
						
						// Checks if it is just a point or a continuous drawing
						if (points.get(j).dragged == false) {
							int pointSize = points.get(j).penSize;
							if (points.get(j).penSize == 1)
								g2D.drawLine(points.get(j).x,
										points.get(j).y, points.get(j).x, points.get(j).y);
							else
								g2D.fillOval(points.get(j).x - points.get(j).penSize / 2, points.get(j).y
										 - points.get(j).penSize / 2, pointSize, pointSize);
	
							// Draws the reflection
							if (points.get(j).reflect  == true) {
								if (points.get(j).penSize == 1) 
									g2D.drawLine(points.get(j).x + 2 * (oX - points.get(j).x),
											points.get(j).y, points.get(j).x + 2 * (oX - points.get(j).x), points.get(j).y);
								else
									g2D.fillOval(points.get(j).x + 2 * (oX - points.get(j).x) - points.get(j).penSize / 2,
											 points.get(j).y  - points.get(j).penSize / 2, pointSize, pointSize);
							}
							g2D.rotate(rotation, oX, oY);
						}
						else {
							if (points.get(j).order == points.get(j - 1).order) {
								g2D.setStroke(new BasicStroke(points.get(j).penSize));
								g2D.drawLine(points.get(j).x,
										points.get(j).y, points.get(j - 1).x, points.get(j - 1).y);
								
								// Draws the reflection
								if (points.get(j).reflect == true)
									g2D.drawLine(points.get(j).x + 2 * (oX - points.get(j).x), points.get(j).y, 
											points.get(j - 1).x + 2 * (oX - points.get(j - 1).x),points.get(j - 1).y);
								g2D.rotate(rotation, oX, oY);
							}
						}
					}
				}
		
		// Deals with the edge case from the previous loop
		if (points.size() >= 1 && points.get(0).dragged == false) {
			g2D.setColor(points.get(0).color);
			int pointSize = points.get(0).penSize;
			for (int i = 0; i < nr; i++) {
				g2D.fillOval(points.get(0).x  - points.get(0).penSize / 2,
						points.get(0).y  - points.get(0).penSize / 2, pointSize, pointSize);
				
				// Draws the reflection
				if (points.get(0).reflect == true)
					g2D.fillOval(points.get(0).x + 2 * (oX - points.get(0).x)  - points.get(0).penSize / 2, points.get(0).y 
							 - points.get(0).penSize / 2 , pointSize, pointSize);
				g2D.rotate(rotation, oX, oY);
			}
		}
	}
	
	// Clears the screen
	public void clear () {
		points.clear();
		controller.clearStack();
		repaint();
	}
	
	private String pen = "Pen";
	
	// Changes from pen to eraser
	public void changeTool (String name) {
		pen = name;
	}
	
	boolean toReflect = false, showSectors = true;
	
	// Changes the option for reflection
	public void reflect () {
		toReflect = !toReflect;
	}
	
	// Changes the option for showing sectors
	public void allowSectors () {
		showSectors = !showSectors;
	}
	
	// Changes the size of the pen or eraser
	public void setSize(int penSize) {
		this.penSize = penSize;
		repaint();
	}
	
	// Changes the number of sectors
	public void setNumberOfSectors (int nr)
	{
		this.nr = nr;
		sectors.clear();
		setSectors();
		repaint();
		
	}
	
	Color color;
	JColorChooser colorChooser = new JColorChooser();
	
	// Changes the color of the pen
	public void changeColor () {
		color = colorChooser.showDialog(this, "Choose pen's color!", color);
	}
	
	
	// Inner class listener for drawing on the display 
	class MyMouseListener implements MouseListener, MouseMotionListener {
		
		@Override
		public void mouseEntered(MouseEvent e) {
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
		}
	
		// Increases the order of the current drawing
		@Override
		public void mouseReleased(MouseEvent e) {
			order++;
		}

		// Adds the points to the points list if there is a continuous drawing 
		@Override
		public void mouseDragged(MouseEvent e) {
			Color color2;
			if (pen.equals("Eraser"))
				color2 = Color.BLACK;
			else
				color2 = color;
			controller.setPoints(new MyPoint(e.getX(), e.getY(), true, color2, penSize, order + 1, toReflect));
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		// Adds the point to the point list if there is a single point drawn
		@Override
		public void mouseClicked(MouseEvent e) {
			Color color2;
			if (pen.equals("Eraser"))
				color2 = Color.BLACK;
			else
				color2 = color; 
			controller.setPoints(new MyPoint(e.getX(), e.getY(), false, color2, penSize, order, toReflect));
			repaint();
		}

	} 
}

