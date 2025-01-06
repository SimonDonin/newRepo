package rentalsystem;

public abstract class MediaItem {

	private String title;
	private boolean isRented = false;
	private String rentedBy;
	
	public MediaItem(String title) {
		if (title.isEmpty()) {
			return;
		}
		this.title = title;
	}
	
	
	public String getTitle() {
		return title;
	}


	public boolean isRented() {
		return isRented;
	}


	public String getRentedBy() {
		return rentedBy;
	}


	public void rent(String name) {
		
		if (isRented || name.isBlank()) {
			System.out.println("The item is already rented or the name is blank");
		} else {
			isRented = true;
			rentedBy = name;
			System.out.println("-".repeat(50));
			System.out.println("The item " + title + " is now rented by " + name);
		}
	}
	
	public void returnItem(String name) {
		
		if (!isRented) {
			System.out.println("The item is not rented and therefore can't be returned");
		} else if (isRented && !rentedBy.equalsIgnoreCase(name)){
			System.out.println("The item is rented by another person " 
		+ rentedBy + " and can't be returned by "+ name);
		} else {
			isRented = false;
			rentedBy = "";
			System.out.println("-".repeat(50));
			System.out.println("The item " + title + " is successfully returned!");
		}
	}

	public boolean getStatus() {
		return isRented;
	}

	public abstract void getDetails(); 
	
	
	
}
