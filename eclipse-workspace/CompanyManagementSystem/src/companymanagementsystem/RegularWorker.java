package companymanagementsystem;

public class RegularWorker extends Worker {

	private final static int START_SICK_DAYS = 15;
	private int sickDays;
	
	public RegularWorker(String name, double basicSalary) {
		
		super(name, basicSalary);
		this.sickDays = START_SICK_DAYS;
	}

		public int getSickDays() {
		return sickDays;
	}
		
	public static int getStartSickDays() {
			return START_SICK_DAYS;
		}

	@Override
	public double calculatePaycheck(int month) {
		
		/*Assuming:
		 - Out of all 30 days in a month 4 are free from work
		 - Standard hours are 8 per day
		 - */
		double salaryPerHour = getBasicSalary() / (26 * 8);
		
		return (double) Math.round(salaryPerHour * getTotalMonthHours(month) * 100) / 100;
	}

	@Override
	public void displayInfo() {

		System.out.println("Regular worker " + getName() + " with id " + getId()
			+ " has basic salary " + getBasicSalary() + ", worked " 
			+ getTotalMonthHours(Person.getCURRENT_MONTH()) + " hours this month and has "
			+ getVacationDays() + " vacation days and " + sickDays + " sickdays left");
	}
	
	//deducts sickdays if sufficient; returns true if successful, otherwise false
	public boolean takeSickDays(int days) {
		
		if (days > START_SICK_DAYS) {
			System.out.println("Taking sickdays from person with id "
					+ getId() + "\nCan't take more than default number of sickdays = " + START_SICK_DAYS);
			return false;
		}
		
		if (days <= 0) {
			System.out.println("Taking sickdays from person with id "
					+ getId() + "\nNumber of sickdays taken must be positive");
			return false;
		}
		
		if (sickDays - days < 0) {
			System.out.println("Taking sickdays from person with id "
					+ getId() + "\nCan't take more sickdays than it is left = " + sickDays);
			return false;
		}
		sickDays -= days;
		return true;
		
	}
	
	public void printSickDays() {
		
		System.out.println("\nWorker id = " + getId() + " has "
				+ sickDays + " sickdays left for this year out of "
				+ START_SICK_DAYS + " default sickdays\n");
		
	}
	
	@Override
	public void resetAllDays() {
		
		this.sickDays = START_SICK_DAYS;
		setVacationDays(getDefaultVacationDays());
		
	}
	
	
}
