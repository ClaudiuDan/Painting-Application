import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

class DisplayGuiPanel extends JPanel {
	DisplayGuiPanel (DisplayPanel displayPanel, Controller controller, GalleryPanel galleryPane) {
		super ();
		init(displayPanel, controller, galleryPane);
	}
	
	// Creates the gui components and sets the layouts
	private void init (DisplayPanel displayPanel, Controller controller, GalleryPanel galleryPanel) {
		setLayout(new GridLayout (3, 1));
		JPanel topPanel = new JPanel ();
		topPanel.setLayout(new FlowLayout());
		add(topPanel);
		
		// Gives a better aspect to the JComponents
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		// Adds the buttons to the panel
		topPanel.add(new MyButton ("Clear", displayPanel, controller, null));
		topPanel.add(new MyButton ("Undo", displayPanel, controller, null));
		topPanel.add(new MyButton ("Redo", displayPanel, controller, null));
		topPanel.add(new MyButton ("Save", displayPanel, controller,  galleryPanel));
		topPanel.add(new MyButton ("Colour", displayPanel, controller, null));
		
		// Adds the spinners and their labels to the panel
		JPanel midPanel = new JPanel ();
		midPanel.setLayout(new FlowLayout());
		add(midPanel);
		midPanel.add(new JLabel("Pen's size"));
		midPanel.add(new MySpinner(displayPanel, "Size"));
		midPanel.add(new JLabel("Number of sectors"));
		midPanel.add(new MySpinner(displayPanel, "Sectors"));
		
		// Adds the radio buttons and toggle buttons
		JPanel bottomPanel = new JPanel ();
		bottomPanel.setLayout(new FlowLayout());
		add(bottomPanel);
		ButtonGroup group = new ButtonGroup();
		bottomPanel.add(new MyRadioButton("Pen", group, displayPanel));
		bottomPanel.add(new MyRadioButton("Eraser", group,  displayPanel));
		bottomPanel.add(new MyToggleButton ("Reflect", displayPanel));
		bottomPanel.add(new MyToggleButton ("Hide sectors", displayPanel));
	}
}
