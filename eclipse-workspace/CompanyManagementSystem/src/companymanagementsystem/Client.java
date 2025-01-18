package companymanagementsystem;

public class Client extends Person {
	
	private String companyName;
	private double[] dailySpending;
	
	public Client(String name, String companyName) {
		super(name);
		if (companyName.isBlank()) {
			System.out.println("Company name can't be blank. Setting up DEFAULT company name");
			this.companyName = "DEFAULT";
		} else {
		this.companyName = companyName.toUpperCase();
		}
		
		dailySpending = new double[30];
	}

	
	public String getCompanyName() {
		return companyName;
	}


	@Override
	public void displayInfo() {
		
		double spentMonth = 0;
		
		for (double spentDay : dailySpending) {
			spentMonth += spentDay;
		}
		
		System.out.println("Client " + getName() + " has id "
				+ getId() + ", represents company " + companyName
				+ " and spent " + spentMonth + " for this month");
	}
	
	// assuming days have numbers 1 - 30
	public boolean updateDailySpending(int day, double amount) {
		
		if (day < 1 || day > 30) {
			System.out.println("Day must be between 1 and 30 including");
			return false;
		}
		
		if (amount < 0) {
			System.out.println("Amount can't be negative");
			return false;
		}
		
		dailySpending[day - 1] = amount;
		return true;
		
	}
	
	// populates client's daily spendings for whole month 
	public void populateDailySpending() {
		
		for (int i = 1; i <= 30; i++) {
			if (dailySpending[i - 1] == 0) {
				dailySpending[i - 1] = (double) Math.round(Math.random() * 5000 * 100) / 100;
			}
		}
	}
	
	// prints cleint's daily spendings for whole month
	public void printDailySpending() {
		
		System.out.println("\nFor client id = " + getId() + " daily spending"
				+ " for this month are the following:");
		for (double spending : dailySpending) {
			System.out.print(spending + " ");
		}
		System.out.println();
	}
	
}
