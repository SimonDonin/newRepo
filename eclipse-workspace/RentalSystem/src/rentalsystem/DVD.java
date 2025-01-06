package rentalsystem;

public class DVD extends MediaItem {
	
	private int runtime;

	public DVD(String title, int runtime) {
		super(title);
		this.runtime = runtime;
		System.out.println("DVD " + title + " with " + runtime + " runtime added successfully");

	}

	@Override
	public void getDetails() {
		// TODO Auto-generated method stub
		System.out.println("The DVD " + super.getTitle() + " has runtime " + runtime 
				+ ((super.isRented()) ? " and it's rented" 
						: " and it's not rented so far") 
				+ ((super.isRented()) ? " by " + super.getRentedBy() : ""));
	}
	
	

}
