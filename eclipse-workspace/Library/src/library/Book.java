package library;

public abstract class Book {

	private String title;
	private Author author;
	private boolean isAvailable = true;
	private String rentBy;
	
	public Book(String title, Author author) {
		if (title.isBlank()) {
			System.out.println("A title can't be blank and it is being assigned to the default value");
			this.title = "DEFAULT TITLE";
			this.author = author;
		} else {
			this.title = title.toUpperCase();
			this.author = author;
		}
	}

	public String getTitle() {
		return title;
	}
	
	public String getRentBy() {
		return rentBy;
	}

	public Author getAuthor() {
		return author;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailability (boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	abstract void displayDetails();
	
	public boolean rentBook(String name) {
		
		if (name.isBlank()) {
			System.out.println("-".repeat(50) + "\n"
					+ "The name of the renter can't be blank");
			return false;
		}
		
		if (!isAvailable) {
			System.out.println("-".repeat(50) + "\n"
					+ "This book is already rented");
			return false;
		}
		
		isAvailable = false;
		rentBy = name;
		System.out.println("-".repeat(50) + "\n"
				+ "The " + getClass().getSimpleName() +  " \"" + title 
				+ "\" by " + author.getName() + " is now rented by " + name);
		return true;
	}
	
public boolean returnBook() {
		
		if (isAvailable) {
			System.out.println("-".repeat(50) + "\n"
					+ "The book \"" + title + "\" is not rented yet and hence can't be returned");
			return false;
		}
		
		isAvailable = true;
		rentBy = null;
		System.out.println("-".repeat(50) + "\n"
				+ "The " + getClass().getSimpleName() +  " \"" + title 
				+ "\" by " + author.getName() + "\" was returned successfully"
				+ " and now it is available for renting");
		return true;
	}
}
