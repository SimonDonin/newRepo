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

	public double[][] getDailySpending() {
		return dailySpending;
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
				if (i == Person.getCURRENT_MONTH()) {
					spentCurrentMonth += spentDay;
				}
			}
		}
		spentYear = round(spentYear);
		spentCurrentMonth = round(spentCurrentMonth);
		System.out.println("Client " + getName() + " has id " + getId() + ", represents company " + companyName
				+ " and spent " + spentCurrentMonth + " current month and " + spentYear + " for this year");
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
					dailySpending[j - 1][i - 1] = round(Math.random() * 5000);
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
		StringBuilder str;

		str = new StringBuilder("\nFor client id = " + getId() + " daily spendings" + " for this year are the following:\n");

		for (int i = 1; i <= 12; i++) {
			str.append("Month # " + i + ":  ");
			for (double spending : dailySpending[i - 1]) {
				str.append(spending + " ");
			}
			str.append('\n');
		}
		System.out.println(str);
	}
	
	private static double round(double num) {
		return (double) ((long) Math.round(num * 100)) / 100;
	}
	
	public void detailedYearlyReport() {
		StringBuilder str;
		double monthTotal;
		double yearTotal = 0.0;
		str = new StringBuilder("\nFor client id = " + getId() + " yearly spendings are the following:\n\n");
		for (int month = 1; month <= 12; month++) {
			monthTotal = 0.0;
			for (double hours : dailySpending[month - 1]) {
				monthTotal += hours;
			}
			yearTotal += monthTotal;
			str.append("\t- Total spending for month # " + month + " = " + round(monthTotal) + "\n");
		}
		str.append("\nTotal spending for the whole year = " + round(yearTotal) + "\n");
		System.out.println(str);
	}

}
