import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MySpinner extends JSpinner{
	private DisplayPanel panel;
	private String name;
	
	// Constructor for initialising variables and adding listener
	MySpinner (DisplayPanel panel, String name)
	{
		super(new SpinnerNumberModel(10, 0, 1000, 1));
		this.panel = panel;
		this.name = name;
		addChangeListener(new MyChangeListener());
	}
	
	class MyChangeListener implements ChangeListener{

		// Gets the new size from the spinner
		@Override
		public void stateChanged(ChangeEvent e) {
			if (name == "Size")
				panel.setSize((int)getValue());
			else {
				panel.setNumberOfSectors((int)getValue());
			}
		}
		
	}
}
