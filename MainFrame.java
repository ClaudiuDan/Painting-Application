import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

class MainFrame extends JFrame {
	
	MainFrame (String name) {
		super(name);
	}
	
	public static void main (String[] args) {
		MainFrame mainFrame = new MainFrame("Digital Doilies");
		mainFrame.init();
	}

	// Initialises the main panels and sets their layout
	private void init () {
		setSize(new Dimension (1100, 700));
		Controller controller = new Controller();
		DisplayPanel displayPanel = new DisplayPanel (getWidth(), getHeight() - 150, controller);
		GalleryPanel galleryPanel = new GalleryPanel (getWidth() - 350, getHeight() - 150, displayPanel);
		DisplayGuiPanel displayGuiPanel = new DisplayGuiPanel (displayPanel, controller, galleryPanel);
		JPanel mainPanel = new JPanel ();
		
		// Display panel, gui panel and gallery panel are grouped in a border layout
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		JPanel rightPanel = new JPanel ();
		rightPanel.setLayout (new BorderLayout());
		mainPanel.add(rightPanel, BorderLayout.EAST);
		rightPanel.add(displayGuiPanel, BorderLayout.NORTH);
		rightPanel.add(galleryPanel, BorderLayout.CENTER);
		setResizable(false);
		
		// Gives a better aspect to the JComponents
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		setContentPane (mainPanel);
		setVisible(true);
	}
}
