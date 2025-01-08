package project2ShapeHierarchy;

public class Rectangle extends TwoDimensionalShape {
	
	private double length;
	private double width;
	
	public Rectangle(String color, double length, double width) {
		super(color);
		this.length = length;
		this.width = width;
	}

	@Override
	double area() {
		// TODO Auto-generated method stub
		return width * length;
	}

	@Override
	double perimeter() {
		// TODO Auto-generated method stub
		return 2 * (length + width);
	}

	@Override
	public void printDetails() {
		// TODO Auto-generated method stub
		System.out.println("The figure #" + getId() + ". It is a " + getColor() + " rectangle with " + width + " width, " 
				+ length + " length, " + perimeter() + " perimeter and " + area() + " area");
		
		
	}
	
	
	
	
}
