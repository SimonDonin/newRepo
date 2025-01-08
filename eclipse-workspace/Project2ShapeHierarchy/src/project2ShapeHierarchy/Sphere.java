package project2ShapeHierarchy;

public class Sphere extends ThreeDimensionalShape {
	
	private double radius;

	public Sphere(String color, double radius) {
		super(color);
		this.radius = radius;
		// TODO Auto-generated constructor stub
	}

	public double volume() {
		return 4 * Math.PI * radius * radius * radius / 3;
	}
	
	public double surfaceArea() {
		return 4 * Math.PI * radius * radius;
	}
	
	@Override
	public void printDetails() {
		
		System.out.println("The figure #" + getId() + ". "
				+ "It is a " + getColor() + " sphere with " + radius + " radius, " 
				+ surfaceArea() + " surface area and " + volume() + " volume");
	}

	

}
