package companymanagementsystem;

import java.util.ArrayList;

public abstract class Person {

// Assuming number of persons is less than 1000. Otherwise ids won't be unique any longer	
	private static int idCounter = 1;
	private String name;
	private final String id;
	private static int CURRENT_MONTH = 1;

	public Person(String name) {

		if (name.isBlank()) {
			System.out.println("Name can't be blank. Setting up DEFAULT name");
			this.name = "DEFAULT";
		} else {
			this.name = name.toUpperCase();
		}
		this.id = getClass().getSimpleName().charAt(0) + "-" + "0".repeat(nullsNumber(idCounter)) + idCounter++;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public static int getCURRENT_MONTH() {
		return CURRENT_MONTH;
	}

	public static void setCURRENT_MONTH(int cURRENT_MONTH) {
		CURRENT_MONTH = cURRENT_MONTH;
	}

	public abstract void displayInfo();

	// calculates number of leading nulls for adding to a person's id
	private int nullsNumber(int num) {

		if (num <= 0) {
			System.out.println("Argument must be positive");
			return -1;
		}

		num = num % 1000;

		if (num < 10) {
			return 2;
		} else if (num < 100) {
			return 1;
		}
		return 0;
	}

	public static boolean resetAllDays(ArrayList<Worker> workers) {

		if (workers.isEmpty()) {
			System.out.println("There are no workers to reset days for");
			return false;
		}

		for (Worker worker : workers) {
			worker.resetAllDays();
		}
		return true;

	}

}
