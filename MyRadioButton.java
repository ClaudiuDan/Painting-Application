import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class MyRadioButton extends JRadioButton {
	DisplayPanel panel;
	
	// Constructor for initialising variables and creating the listener
	MyRadioButton (String name, ButtonGroup group, DisplayPanel panel) {
		super(name);
		setSelected(true);
		group.add(this);
		this.panel = panel;
		
		// adds the listener to this component
		this.addActionListener(new ActionListener() {
			
			// Checks which JToggleButton was pressed
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.changeTool(name);
			}
		});
		
	}
}
