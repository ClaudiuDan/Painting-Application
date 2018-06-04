import java.awt.Color;
import java.awt.Point;

public class MyPoint extends Point{
	boolean dragged;
	Color color;
	int penSize;
	int order;
	boolean reflect;
	
	// Constructor, sets up the variables of the class
	MyPoint (int x, int y, boolean dragged, Color color, int penSize, int order, boolean reflect)
	{
		super(x, y);
		this.dragged = dragged;
		this.color = color;
		this.penSize = penSize;
		this.order = order;
		this.reflect = reflect;
	}
}
