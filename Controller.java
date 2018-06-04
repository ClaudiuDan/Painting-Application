import java.util.ArrayList;

public class Controller {
	private ArrayList<MyPoint> pointStack = new ArrayList<MyPoint>();
	private ArrayList<MyPoint> points = new ArrayList<MyPoint>();
	
	// Updates the stack of points that can be redone
	void updateStack (String command) {
		
		// Checks if points should be taken from the normal array and put into the stack
		if (command == "Undo" && points.size() > 0) {
			int n = points.size();
			int i = n - 1;
			
			// If it is a continuous drawing, it moves more points to the stack
			if (points.get(i).dragged == true) {
				while (i > 0 && points.get(i).order == points.get(--i).order) {
					n = points.size();
					pointStack.add(points.get(n - 1));
					points.remove(n - 1);
				}
				points.remove(points.size() - 1);
			}
			else {
				pointStack.add(points.get(n - 1));
				points.remove(n - 1);
			}
				
		}
		
		// Checks if points should be take from the stack and put in the normal array
		if (command == "Redo" && pointStack.size() > 0) {
			int n = pointStack.size();
			int i = n - 1;
			
			// If it is a continuous drawing, it moves more points to the array
			if (pointStack.get(i).dragged == true) {
				while (i > 0 && pointStack.get(i).order == pointStack.get(--i).order) { 
					n = pointStack.size();
					points.add(pointStack.get(n - 1));
					pointStack.remove(n - 1);
				}
				pointStack.remove(pointStack.size() - 1);
			}
			else {
				points.add(pointStack.get(n - 1));
				pointStack.remove(n - 1);
			}
		}
	}
	
	// Returns the array of points
	public ArrayList<MyPoint> getPoints () {
		return points;
	}
	
	// Adds points to the array
	public void setPoints (MyPoint point) {
		clearStack();
		points.add(point);
	}
	
	// Clears the stack
	public void clearStack () {
		pointStack.clear();
	}
}
