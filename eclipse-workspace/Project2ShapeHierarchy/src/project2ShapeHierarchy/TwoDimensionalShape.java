package project2ShapeHierarchy;

public abstract class TwoDimensionalShape extends Shape {
	
	final String type = "TwoDimensional";
	
	public TwoDimensionalShape(String color) {
		super(color);
	}

	abstract double area();
	
	abstract double perimeter() ;
	
}
