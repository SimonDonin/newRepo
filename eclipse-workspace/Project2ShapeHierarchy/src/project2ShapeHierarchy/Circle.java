package project2ShapeHierarchy;

public class Circle extends TwoDimensionalShape {
	
	private double radius;

	public Circle(String color, double radius) {
		super(color);
		this.radius = radius;
	}

	@Override
	double area() {
		// TODO Auto-generated method stub
		return Math.PI * radius * radius;
	}

	@Override
	double perimeter() {
		// TODO Auto-generated method stub
		return 2 * Math.PI * radius;
	}

	@Override
	public void printDetails() {
		// TODO Auto-generated method stub
		System.out.println("The figure #" + getId() + ". It is a " + getColor() + " circle with " + radius 
				+ " radius, " + perimeter() + " perimeter and " + area() + " area");
		
	}
	
	
}
