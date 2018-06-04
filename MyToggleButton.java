import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

public class MyToggleButton extends JToggleButton{
	private DisplayPanel panel;
	
	// Constructor for initialising variables and creating the listener
	MyToggleButton (String name, DisplayPanel panel)
	{
		super(name, false);
		
		// adds the listener to this component
		addActionListener(new ActionListener () {

			// Checks which JToggleButton was pressed
			@Override
			public void actionPerformed(ActionEvent e) {
				if (name == "Reflect")
					panel.reflect();
				else
					panel.allowSectors();
				panel.repaint();
			}
		});
	}
}
