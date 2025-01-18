package companymanagementsystem;

import java.util.ArrayList;

public class Manager extends Worker {
	
	private final static int EXTRA_VACATION_DAYS = 10; // Additional vacation days for managers
	private ArrayList<Worker> team; // List of workers managed by the manager
	private double calculatedBonus = 0;
	
	public Manager(String name, double basicSalary) {
		super(name, basicSalary);
		this.team = new ArrayList<>();
		resetAllDays();
		}

	public ArrayList<Worker> getTeam() {
		return team;
	}

	// calculates manager's bonus (only if it's not calculated)
	private double calculateBonus() {
		if (calculatedBonus == 0) {
			calculatedBonus = Math.random() * (getBasicSalary() / 5);
		}
		return calculatedBonus;
	}
	
	//calculates manager's paycheck
	@Override
	public double calculatePaycheck() {
		double bonus = calculateBonus();
		return (double) Math.round((getBasicSalary() + bonus) * 100) / 100;
	}

	// Displays all workers managed by manager
	@Override
	public void displayInfo() {
		
		String managerName = getName();
		System.out.println("\n - " + managerName + " with id " + getId()
		 + " is a manager\n"
		 + "   " + managerName + " has basic salary " + getBasicSalary() + ", worked " 
			+ getTotalMonthHours() + " hours this month and has "+ getVacationDays()
			+ " vacation days left");
		
		if (team.isEmpty()) {
			System.out.println("   And " + managerName + "'s team is empty");
		} else {
		System.out.println("   And " + managerName + "'s team consists of the following workers:");
		for (Worker worker : team) {
			System.out.print("    - ");
			worker.displayInfo();
			}
		}
	}
	
	private Worker findWorker(Worker workerToAdd) {
		
		if (workerToAdd == null) {
			System.out.println("Worker can't be blank");
			return null;
		}
		
		for (Worker worker : team) {
			if (worker.getId().equalsIgnoreCase(workerToAdd.getId())) {
				return worker;
			}
		}
		
		return null;
	}
	
	public boolean addTeamMember(Worker worker) {
		
		if (worker == null) {
			System.out.println("Worker can't be null");
			return false;
		}
		
		Worker workerFound = findWorker(worker);
		
		if (workerFound != null) {
			System.out.println("\nWorker " + worker.getName() + " is already in the team list "
					+ "and has id = " + worker.getId());
			return false;
		}
		
		team.add(worker);
		return true;
	}

	@Override
	protected void resetAllDays() {
		
		setVacationDays(getDefaultVacationDays());	
	}

	@Override
	public int getDefaultVacationDays() {
		return super.getDefaultVacationDays() + EXTRA_VACATION_DAYS;
	}
	
	
	

}
