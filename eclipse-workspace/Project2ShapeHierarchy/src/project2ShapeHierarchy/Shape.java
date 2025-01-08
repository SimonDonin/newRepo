package project2ShapeHierarchy;

public abstract class Shape {
	private int id;
	private String color;
	private static int counter = 0;
	
	public Shape(String color) {
		this.id = ++counter;
		this.color = color;
	}

	public static void printCounter() {
		System.out.println("Counter = " + Shape.counter);
	}
	
	public abstract void printDetails();

	public int getId() {
		return id;
	}

	public String getColor() {
		return color;
	}
	
	

	
}
