import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

class GalleryPanel extends JPanel {
	private BufferedImage image;
	private Dimension d;
	private int nrImages = 0;
	private ArrayList<JButton> removes = new ArrayList<JButton>();
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	private DisplayPanel panel;
	private int dif;
	
	// Constructor for initialising some variables
	GalleryPanel (int x, int y, DisplayPanel panel) {
		super();
		
		// Gives a better aspect to the JComponents
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		this.panel = panel;
		this.setLayout(new GridLayout(4, 3));
		d = new Dimension (x, y);
		for (int i = 0; i < 12; i++)
			addEmptyPanel();
	}
	
	// Saves image of the current display
	public void saveImage () {
		if (nrImages < 12) {
			addPanel(getImage(), nrImages++);
		}
	}
	
	// Adds an empty panel, useful when removing images or at the start
	private void addEmptyPanel () {
		JButton b = new JButton ("Remove");
		//b.setBackground(Color.WHITE);
		b.addActionListener(new MyActionListener());
		removes.add(b);
		JPanel p = new JPanel ();
		JLabel l = new JLabel ();
		labels.add(l);
		// Sets the layout and options for the image panel
		p.setLayout(new BorderLayout());
		p.add(l, BorderLayout.CENTER);
		p.add(b, BorderLayout.SOUTH);
		panels.add(p);
		add(p);
		p.setVisible(false);
		revalidate();
		repaint();
	}
	
	// Adds an image to the gallery
	private void addPanel(Image img, int i) {
		JLabel l = new JLabel (new ImageIcon(img));
		
		// Replaces an empty panel with a new non-empty panel
		removes.get(i).setActionCommand(String.valueOf(i));
		labels.set(i, l);
		panels.get(i).add(l, BorderLayout.CENTER);
		panels.get(i).setVisible(true);
		
	}
	
	// Returns the image of the display panel
	private Image getImage () {
		image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		panel.print(g2d);
		Image img = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		g2d.dispose();
		return img;
	}
	
	// Inner class listener for dealing with removals
	class MyActionListener implements ActionListener {
		
		// Removes an image panel from the gallery
		@Override
		public void actionPerformed(ActionEvent e) {
			dif++;
			
			// Replaces a Panel with an empty panel
			int i = Integer.parseInt(e.getActionCommand());
			remove(panels.get(i));
			panels.remove(i);
			revalidate();
			repaint();
			removes.remove(i);
			
			// Restructures the gallery, puts the empty panels at the end 
			for (int j = 0; j < removes.size(); j++)
				removes.get(j).setActionCommand(String.valueOf(j));
			addEmptyPanel();
			nrImages--;
		}
	}
}
