package project2ShapeHierarchy;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Shape.printCounter();
		Circle myCircle1 = new Circle("Green", 7.7);
		Circle myCircle2 = new Circle("Brown", 0.67);
		
		Shape.printCounter();
		myCircle1.printDetails();
		myCircle2.printDetails();
		
		System.out.println("-".repeat(50));
		Rectangle myRectangle1 = new Rectangle("Blue", 7.7, 4.5);
		Rectangle myRectangle2 = new Rectangle("White", 7.7, 4.5);
		Shape.printCounter();
		
		myRectangle1.printDetails();
		myRectangle2.printDetails();
		
		System.out.println("-".repeat(50));
		Cuboid myCuboid1 = new Cuboid("Gold", 1.3, 4.5, 6.7);
		Cuboid myCuboid2 = new Cuboid("Silver", 2.4, 5.6, 7.8);
		Shape.printCounter();
		
		myCuboid1.printDetails();
		myCuboid2.printDetails();
		
		System.out.println("-".repeat(50));
		Sphere mySphere1 = new Sphere("Black", 13.5);
		Sphere mySphere2 = new Sphere("Red", 86.17);
		Shape.printCounter();
		
		mySphere1.printDetails();
		mySphere2.printDetails();
}
}
