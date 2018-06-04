import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MyButton extends JButton {
	private String name;
	private DisplayPanel panel;
	private GalleryPanel galleryPanel;
	private Controller controller; 
	
	// Constructor for initialising some variables
	MyButton (String name, DisplayPanel panel, Controller controller, GalleryPanel galleryPanel) {
		super(name);
		this.controller = controller;
		this.panel = panel;
		this.name = name;
		this.galleryPanel = galleryPanel;
		addActionListener(new MyActionListener());
	}
	
	// Inner Class Listener, deals with buttons interaction
	class MyActionListener implements ActionListener {
		
		// Checks what button is called and calls the appropiate method 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (name == "Clear")
				panel.clear ();
			if (name == "Colour")
				panel.changeColor();
			if (name == "Undo" || name == "Redo") {
				controller.updateStack(name);
				panel.repaint();
			}
			if (name == "Save")
				galleryPanel.saveImage();
		}
	}
}
