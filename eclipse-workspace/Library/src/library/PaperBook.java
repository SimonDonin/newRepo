package library;

public class PaperBook extends Book{

	private String shelfLocation;
	
	public PaperBook(String title, Author author, String shelfLocation) {
		
		super(title, author);
		if (shelfLocation.isBlank()) {
			System.out.println("Location on the shelf can't be blank "
					+ "and it is being assigned to the default value");
			this.shelfLocation = "DEFAULT LOCATION";
		} else {
		this.shelfLocation = shelfLocation;
		}
	}
	
	public String getShelfLocation() {
		return shelfLocation;
	}

	@Override
	void displayDetails() {
		// TODO Auto-generated method stub
		
		System.out.println("The " + getClass().getSimpleName() + " \"" + super.getTitle() + "\" by " 
				+ super.getAuthor().getName() + (super.isAvailable() ? " is available " : " is not available ")
					+ "for renting and its location on the shelf is " + shelfLocation); 
	}

}
