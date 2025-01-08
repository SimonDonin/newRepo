package library;

public class EBook extends Book {

	private double fileSize;
	
	public EBook(String title, Author author, double fileSize) {
		
		super(title, author);
		if (fileSize <= 0) {
			System.out.println("Size of the file must be positive "
					+ "and it is being assigned to the default value 1.0");
			fileSize = 1.0;
		} else {
			this.fileSize = fileSize;
		}
	}

	public double getFileSize() {
		return fileSize;
	}

	@Override
	void displayDetails() {
		// TODO Auto-generated method stub
		
		System.out.println("The " + getClass().getSimpleName() + " \"" + super.getTitle() + "\" by " 
				+ super.getAuthor().getName() + (super.isAvailable() ? " is available " : " is not available ")
					+ "for renting and its size is " + fileSize);
	}
}
