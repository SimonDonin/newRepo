package project2ShapeHierarchy;

public class Cuboid extends ThreeDimensionalShape {
	
	private double length;
	private double width;
	private double height;

	public Cuboid(String color, double length, double width, double height) {
		super(color);
		this.length = length;
		this.width = width;
		this.height = height;
	}

	public double volume() {
		return width * length * height;
	}
	
	public double surfaceArea() {
		return 2 * (width * length +  height * width +  height * length);
	}
	
	@Override
	public void printDetails() {
		// TODO Auto-generated method stub
		System.out.println("The figure #" + getId() + ". "
				+ "It is a " + getColor() + " cuboid with " + width + " width, " 
				+ length + " length and " + height + " height, " 
				+ surfaceArea() + " surface area and " + volume() + " volume");
		
	}

	
}
