package companymanagementsystem;

import java.time.DayOfWeek;

public abstract class Worker extends Person {

	private final static int START_VACATION_DAYS = 10; // Initial vacation days for workers
	private double[] dailyHours; // Tracks hours worked for the last 30 days
	private int vacationDays; // Remaining vacation days
	private double basicSalary;
	private static final double DEFAULT_WORKING_HOURS = 8.0;
	
	
	public Worker(String name, double basicSalary) {
		
		super(name);
		
		if (basicSalary < 0) {
			System.out.println("Salary can't be negative. Setting up default value 0");
			this.basicSalary = 0.0;
		} else {
			this.basicSalary = basicSalary;
		}

		// assigning default values
		dailyHours = new double[30];
		vacationDays = getDefaultVacationDays();
	}
	

	public int getVacationDays() {
		return vacationDays;
	}


	public double getBasicSalary() {
		return basicSalary;
	}


	public double[] getDailyHours() {
		return dailyHours;
	}

	public static double getDEFAULT_WORKING_HOURS() {
		return DEFAULT_WORKING_HOURS;
	}

	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}

	public abstract double calculatePaycheck();

	@Override
	public abstract void displayInfo();
	
	// Logs hours for a specific day (validates hours between 0–24)
	public boolean logHours(int day, double hours) {
		
		// day validation
		if (day < 1 || day > 30) {
			System.out.println("Day must be between 1 and 30 including");
			return false;
		}
		
		// hours validation
		if (hours < 0 || hours > 24) {
			System.out.println("Hours must be between 0 and 24 including");
			return false;
		}
		
		dailyHours[day - 1] = hours;
		return true;
	}
	
	
	public int getDefaultVacationDays() {
		
		return START_VACATION_DAYS;
	}
	
	/*
	 * Deducts vacation days if sufficient; returns true if successful, otherwise
	 * false
	 */
	public boolean takeVacationDays(int days) {
		
		if (days > getDefaultVacationDays()) {
			System.out.println("Taking vacation days from person with id "
					+ getId() + "\nCan't take more than default number of vacation days = " + getDefaultVacationDays());
			return false;
		}
		
		if (days <= 0) {
			System.out.println("Taking vacation days from person with id "
					+ getId() + "\nNumber of vacation days taken must be positive");
			return false;
		}
		
		if (vacationDays - days < 0) {
			System.out.println("Taking vacation days from person with id "
					+ getId() + "\nCan't take more vacation days than it is left = " + vacationDays);
			return false;
		}
		vacationDays -= days;
		
		return true;
	}
	
	// sets number of vacation days to default 
	protected void resetAllDays() {
		
		setVacationDays(START_VACATION_DAYS);
	}
	
	// calculates total month working hours 
		public double getTotalMonthHours() {
			
			double totalMonthHours = 0;
			
			for (double dayHours : getDailyHours()) {
				totalMonthHours += dayHours;
			}
			return  (double) Math.round(totalMonthHours * 100) / 100;
		}
	
		public void populateDailyHours() {
			
			for (int i = 1; i <= 30; i++) {
				if (dailyHours[i - 1] == 0) {
					dailyHours[i - 1] = (double) Math.round(Math.random() * 12 * 100) / 100;
				}
			}
		}
		
		public void printVacations() {
			
			System.out.println("\nWorker id = " + getId() + " has "
					+ vacationDays + " vacations left for this month out of "
					+ getDefaultVacationDays() + " default vacations\n");
			
		}
		
		public void printDailyHours() {
			
			System.out.println("\nFor worker id = " + getId() + " daily working"
					+ " hours for this month are the following:");
			for (double hours : dailyHours) {
				System.out.print(hours + " ");
			}
			System.out.println();
		}
	
}
