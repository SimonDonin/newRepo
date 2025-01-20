package companymanagementsystem;

public class Client extends Person {
	
	private String companyName;
	private double[][] dailySpending;
	
	public Client(String name, String companyName) {
		super(name);
		if (companyName.isBlank()) {
			System.out.println("Company name can't be blank. Setting up DEFAULT company name");
			this.companyName = "DEFAULT";
		} else {
		this.companyName = companyName.toUpperCase();
		}
		
		dailySpending = new double[12][30];
	}

	
	public String getCompanyName() {
		return companyName;
	}


	@Override
	public void displayInfo() {
		
		double spentYear = 0;
		double spentCurrentMonth = 0;
		
		for (int i = 1; i <= 12; i++) {
			for (double spentDay : dailySpending[i - 1]) {
				spentYear += spentDay;
				if ( i == Person.getCURRENT_MONTH()) {
					spentCurrentMonth += spentDay;
				}
			}
		}
		spentYear = (double) Math.round(spentYear * 100) / 100;
		spentCurrentMonth = (double) Math.round(spentCurrentMonth * 100) / 100;
		System.out.println("Client " + getName() + " has id "
				+ getId() + ", represents company " + companyName
				+ " and spent " + spentCurrentMonth + " current month and "+ spentYear + " for this year");
	}
	
	// assuming days have numbers 1 - 30
	public boolean updateDailySpending(int month, int day, double amount) {
		
		if (day < 1 || day > 30) {
			System.out.println("Day must be between 1 and 30 including");
			return false;
		}
		
		if (amount < 0) {
			System.out.println("Amount can't be negative");
			return false;
		}
		
		dailySpending[month - 1][day - 1] = amount;
		return true;
		
	}
	
	// populates client's daily spendings for whole year by months 
	public void populateDailySpending() {
		
		for (int j = 1; j <= 12; j++) {
			for (int i = 1; i <= 30; i++) {
				if (dailySpending[j - 1][i - 1] == 0) {
					dailySpending[j - 1][i - 1] = (double) Math.round(Math.random() * 5000 * 100) / 100;
				}
			}
		}
	}
	
	public void eraseDailySpendings() {
		for (int i = 1; i <= 12; i++) {
			for (int j = 1; j <= 30; j++) {
				updateDailySpending(i, j, 0.0);
			}
		}
	}
	
	// prints cleint's daily spendings for whole year by months
	public void printDailySpending() {
		
		System.out.println("\nFor client id = " + getId() + " daily spendings"
				+ " for this year are the following:");
		
		for (int i = 1; i <= 12; i++) {
			for (double spending : dailySpending[i - 1]) {
				System.out.print(spending + " ");
			}
			System.out.println();
		}
	}
	
}
