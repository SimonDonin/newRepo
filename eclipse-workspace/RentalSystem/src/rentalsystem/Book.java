package rentalsystem;

public class Book extends MediaItem{

	private int pages;

	public Book(String title, int pages) {
		super(title);
		System.out.println("Book " + title + " with " + pages + " pages added successfully");
		if (pages <= 0) {
			System.out.println("The pages number can't be not positive");
			this.pages = 100; // default value
			return;
		}
		this.pages = pages;
		System.out.println("Book " + title + " with " + pages + " pages added successfully");
	}

	@Override
	public void getDetails() {
		// TODO Auto-generated method stub
		System.out.println("The book " + super.getTitle() + " has " + pages + " pages" +
		((super.isRented()) ? " and it's rented" : " and it's not rented so far") 
		+ ((super.isRented()) ? " by " + super.getRentedBy() : ""));
	}
	
	
	
	
}
