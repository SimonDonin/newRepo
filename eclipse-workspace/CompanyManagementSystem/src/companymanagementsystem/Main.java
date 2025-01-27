/*Program implementation assumes:
	1. Manager can't have another manager, i.e. he can't be a worker of another manager's team
	2. Paycheck calculation for regular workers:
	   - Out of all 30 days in a month 4 are free from work
	   - Standard hours are 8 per day
	3. Managers have no sick days and their paychecks do not depend on working hours.
	4. Managers have monthly bonuses, that are calculated as a random value between 0 and 0.2 * basic salary.
	5. In addition to current month program tracks data for current year. For this purpose
	days are considered to be inside 2-dimensional array: 12 months and 30 days.
	6. Current month is defined by Person.CURRENT_MONTH (1 - 12). Default value is 1.
	7. Once being calculated manager's bonus for the month can't be recalculated without
	monthly maintaince operation.
	8. Vacations and sickdays do not affect payment. Being entered on a certain day 
	they just add 8 daily hours that day.
	9. Vacations and sickdays can be entered only for days of current month
	   Spendings can be entered for days of any month (1 - 12)
	10. Due to taking sickdays or vacations must be simultaneous with setting 8 working hours for each of those days,
		some validation logic is doubled in RegularWorker/Worker and Main classes.
	11. Sickdays and vacations values are assumed to be reset to default values EVERY YEAR, NOT MONTH! 
	12. PRogram implements single common TECHNICAL operation that does following actions:
	     - adds 1 to CURRENT_MONTH (after 12 it switches to 1)
	     - if month changes from 12 to 1 then additionally:
	     	- erases workers' daily hours for all days in year
	     	- erases clients' daily spendings for all days in year
	     	- resets calculated bonus for managers enabling it to be calculated again
	     	- resets vacations and sickdays to default values
	   This operation is assumed to be performed after ending of each month.
*/
package companymanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static ArrayList<RegularWorker> regWorkers;
	static ArrayList<Client> clients;
	static ArrayList<Manager> managers;
	static Scanner scan = new Scanner(System.in);
	static String inputString = "";
	static Double inputDouble = 0.0;
	static int inputInt = -1;
	static boolean isMenuExit = false;
	static String line = "-".repeat(50);

	public static void main(String[] args) {

		try {
			regWorkers = new ArrayList<>();
			clients = new ArrayList<>();
			managers = new ArrayList<>();

			// creating test objects
			Client sam = new Client("Sam", "Shufer");
			clients.add(sam);
			Manager alex = new Manager("Alex", 12000.0);
			managers.add(alex);
			RegularWorker tom = new RegularWorker("Tom", 7500);
			regWorkers.add(tom);

			Client sam2 = new Client("Sam2", "Shufer2");
			clients.add(sam2);
			Manager alex2 = new Manager("Alex2", 12002.0);
			managers.add(alex2);
			RegularWorker tom2 = new RegularWorker("Tom2", 7502);
			regWorkers.add(tom2);

			RegularWorker tom22 = new RegularWorker("Tom22", 7522);
			regWorkers.add(tom22);

			alex2.addTeamMember(tom2);
			alex2.addTeamMember(tom22);

			// populating some objects' data
			sam.populateDailySpending();
			alex.populateDailyHours();
			tom.populateDailyHours();

			// main menu cycle
			while (!isMenuExit) {
				printUserMenu();
				inputString = scan.nextLine();
				inputInt = tryStringToInt(inputString);

				if (inputInt == -1) {
					continue;
				}

				switch (inputInt) {
				case 1 -> printClients();
				case 2 -> printRegWorkers();
				case 3 -> printManagers();
				case 4 -> addPerson();
				case 5 -> assignWorker();
				case 6 -> enterHours();
				case 7 -> enterSpending();
				case 8 -> enterSickday();
				case 9 -> enterVacation();
				case 10 -> calculatePaycheck();
				case 11 -> monthlyMaintaince();
				case 12 -> workersYearlyReports();
				case 13 -> clientsYearlyReports();
				case 14 -> topYearlyClients();
				case 15 -> topYearlyWorkers();
				case 16 -> populateAll();
				default -> {
					isMenuExit = true;
					System.out.println("Exiting the menu...");
				}
				}
			}
		} finally {
			scan.close();
		}
	}

	private static boolean populateAll() {
		for (RegularWorker regWorker : regWorkers) {
			regWorker.populateDailyHours();
		}
		for (Manager manager : managers) {
			manager.populateDailyHours();
		}
		for (Client client : clients) {
			client.populateDailySpending();
		}
		System.out.println("\nAll workers and clients were populated with data successfully!");
		return true;
	}

	private static double round(double num) {
		return (double) ((long) Math.round(num * 100)) / 100;
	}

	// calculates and prints best and worst regular workers and managers both for
	// every month and overall for the whole year
	// Criteria - number of working hours for the period of comparison
	private static boolean topYearlyWorkers() {
		int regWorkersNum = regWorkers.size();
		int managersNum = managers.size();
		double monthTotal;
		double yearTotal;
		int indexMax;
		int indexMin;
		double max;
		double min;
		StringBuilder str;

		// check whether there is only one regular worker
		if (regWorkersNum == 1) {
			System.out.println(
					"Since there is the only one regular worker" + " there are no best or worst workers this year");
		} else {
			// array for saving working hours for every month for every reg worker
			// the last element is for saving yearly totals
			double[][] workersYearlyResults = new double[regWorkersNum][13];
			for (int i = 1; i <= regWorkersNum; i++) {
				yearTotal = 0;
				for (int month = 1; month <= 12; month++) {
					monthTotal = 0.0;
					for (int day = 1; day <= 30; day++) {
						monthTotal += regWorkers.get(i - 1).getDailyHours()[month - 1][day - 1];
					}
					workersYearlyResults[i - 1][month - 1] = round(monthTotal);
					yearTotal += workersYearlyResults[i - 1][month - 1];
				}
				workersYearlyResults[i - 1][12] = round(yearTotal);
			}

			// printing workers' monthly hours by workers - just for testing
			/*
			 * for (int i = 1; i <= regWorkersNum; i++) { str = new StringBuilder("\n" +
			 * workersYearlyResults[i - 1][0] + " "); for (int month = 2; month <= 13;
			 * month++) { str.append(workersYearlyResults[i - 1][month - 1] + " "); }
			 * System.out.println(str); }
			 */

			// searching for max and min values among all reg workers for every month and
			// the whole year
			for (int month = 1; month <= 13; month++) {
				max = workersYearlyResults[0][month - 1];
				min = workersYearlyResults[0][month - 1];
				indexMax = 0;
				indexMin = 0;
				for (int i = 2; i <= regWorkersNum; i++) {
					if (max < workersYearlyResults[i - 1][month - 1]) {
						max = workersYearlyResults[i - 1][month - 1];
						indexMax = i - 1;
					}
					if (min > workersYearlyResults[i - 1][month - 1]) {
						min = workersYearlyResults[i - 1][month - 1];
						indexMin = i - 1;
					}
				}

				// printing yearly results
				if (month == 13) {
					System.out.println("\nOverall this year the best worker is the worker with id "
							+ regWorkers.get(indexMax).getId()
							+ "\nOverall this year the worst worker is the worker with id "
							+ regWorkers.get(indexMin).getId());
				} else {
					// printing monthly results
					System.out.println("\nIn month # " + month + " the best worker is the worker with id "
							+ regWorkers.get(indexMax).getId() + "\nIn month # " + month
							+ " the worst worker is the worker with id " + regWorkers.get(indexMin).getId());
				}
			}

		}

		// check whether there is only one manager
		if (managersNum == 1) {
			System.out.println("Since there is the only one manager there are no best or worst managers this year");
		} else {
			// array for saving working hours for every month for every manager
			// the last element is for saving yearly totals
			double[][] managersYearlyResults = new double[managersNum][13];
			for (int i = 1; i <= managersNum; i++) {
				yearTotal = 0;
				for (int month = 1; month <= 12; month++) {
					monthTotal = 0.0;
					for (int day = 1; day <= 30; day++) {
						monthTotal += round(managers.get(i - 1).getDailyHours()[month - 1][day - 1]);
					}
					managersYearlyResults[i - 1][month - 1] = round(monthTotal);
					yearTotal += managersYearlyResults[i - 1][month - 1];
				}
				managersYearlyResults[i - 1][12] = round(yearTotal);
			}

			// printing managers' monthly hours by managers - just for testing
			/*
			 * for (int i = 1; i <= managersNum; i++) { str = new StringBuilder("\n" +
			 * managersYearlyResults[i - 1][0] + " "); for (int month = 2; month <= 13;
			 * month++) { str.append(managersYearlyResults[i - 1][month - 1] + " "); }
			 * System.out.println(str); }
			 */

			// searching for max and min values among all managers for every month and the
			// whole year
			for (int month = 1; month <= 13; month++) {
				max = managersYearlyResults[0][month - 1];
				min = managersYearlyResults[0][month - 1];
				indexMax = 0;
				indexMin = 0;
				for (int i = 2; i <= managersNum; i++) {
					if (max < managersYearlyResults[i - 1][month - 1]) {
						max = managersYearlyResults[i - 1][month - 1];
						indexMax = i - 1;
					}
					if (min > managersYearlyResults[i - 1][month - 1]) {
						min = managersYearlyResults[i - 1][month - 1];
						indexMin = i - 1;
					}
				}

				// printing yearly results
				if (month == 13) {
					System.out.println("\nOverall this year the best manager is the manager with id "
							+ managers.get(indexMax).getId()
							+ "\nOverall this year the worst manager is the manager with id "
							+ managers.get(indexMin).getId());
				} else {
					// printing monthly results
					System.out.println("\nIn month # " + month + " the best manager is the manager with id "
							+ managers.get(indexMax).getId() + "\n" + "In month # " + month
							+ " the worst manager is the manager with id " + managers.get(indexMin).getId());
				}
			}
		}
		return true;
	}

	private static boolean topYearlyClients() {
		int clientsNum = clients.size();
		double monthTotal;
		double yearTotal;
		int indexMax;
		int indexMin;
		double max;
		double min;
		StringBuilder str;

		// check whether there is only one client
		if (clientsNum == 1) {
			System.out.println("Since there is the only one client there are no best or worst clients this year");
		} else {
			// array for saving spendings for every month for every client
			// the last element is for saving yearly totals
			double[][] clientsYearlyResults = new double[clientsNum][13];
			for (int i = 1; i <= clientsNum; i++) {
				yearTotal = 0;
				for (int month = 1; month <= 12; month++) {
					monthTotal = 0.0;
					for (int day = 1; day <= 30; day++) {
						monthTotal += clients.get(i - 1).getDailySpending()[month - 1][day - 1];
					}
					clientsYearlyResults[i - 1][month - 1] = round(monthTotal);
					yearTotal += clientsYearlyResults[i - 1][month - 1];
				}
				clientsYearlyResults[i - 1][12] = round(yearTotal);
			}

			// printing clients' spendings by clients - just for testing
			/*
			 * for (int i = 1; i <= clientsNum; i++) { str = new StringBuilder("\n" +
			 * clientsYearlyResults[i - 1][0] + " "); for (int month = 2; month <= 13;
			 * month++) { str.append(clientsYearlyResults[i - 1][month - 1] + " "); }
			 * System.out.println(str); }
			 */

			// searching for max and min values among all managers for every month and
			// the whole year
			for (int month = 1; month <= 13; month++) {
				max = clientsYearlyResults[0][month - 1];
				min = clientsYearlyResults[0][month - 1];
				indexMax = 0;
				indexMin = 0;
				for (int i = 2; i <= clientsNum; i++) {
					if (max < clientsYearlyResults[i - 1][month - 1]) {
						max = clientsYearlyResults[i - 1][month - 1];
						indexMax = i - 1;
					}
					if (min > clientsYearlyResults[i - 1][month - 1]) {
						min = clientsYearlyResults[i - 1][month - 1];
						indexMin = i - 1;
					}
				}

				// printing yearly results
				if (month == 13) {
					System.out.println(
							"\nOverall this year the best client is the client with id " + clients.get(indexMax).getId()
									+ "\nOverall this year the worst client is the client with id "
									+ clients.get(indexMin).getId());
				} else {
					// printing monthly results
					System.out.println("\nIn month # " + month + " the best client is the client with id "
							+ clients.get(indexMax).getId() + "\nIn month # " + month
							+ " the worst client is the client with id " + clients.get(indexMin).getId());
				}
			}
		}
		return true;
	}

	private static boolean clientsYearlyReports() {

		Client clientFound;
		System.out.println(line + "\nYEARLY REPORTS menu is chosen...\n");
		printClients();
		System.out
				.println(line + "\n" + "Please choose from LIST ABOVE ID of CLIENT:" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}
		System.out.println(line + "\nClient ID " + inputString + " was chosen");

		clientFound = findClientById(inputString);

		if (clientFound == null) {
			System.out.println("There is no client with id = " + inputString);
			return false;
		}
		clientFound.detailedYearlyReport();
		return true;
	}

	private static boolean workersYearlyReports() {
		RegularWorker regWorkerFound;
		Manager managerFound;
		Worker workerFound;
		System.out.println(line + "\nWORKERS' YEARLY REPORTS menu is chosen...\n");
		printRegWorkers();
		printManagers();

		System.out.println(line + "\n" + "Please choose from LIST ABOVE ID of REGULAR WORKER or MANAGER:" + "\n" + line
				+ "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}
		System.out.println(line + "\nWorker ID " + inputString + " was chosen");

		regWorkerFound = findWorkerById(inputString);
		managerFound = findManagerById(inputString);
		workerFound = (regWorkerFound == null ? managerFound : regWorkerFound);

		if (workerFound == null) {
			System.out.println("There is no worker with id = " + inputString);
			return false;
		}
		workerFound.detailedYearlyReport();
		return true;
	}

	public static void printUserMenu() {

		System.out.println(line + "\n" + "CHOOSE one of the following available options:\n"
				+ " 1  - to VIEW a CLIENTS list\n" + " 2  - to VIEW a REGULAR WORKERS list\n"
				+ " 3  - to VIEW a MANAGERS list\n" + " 4  - to ADD a PERSON (client or worker)\n"
				+ " 5  - to ASSIGN REGULAR WORKER to MANAGER's TEAM\n" + " 6  - to ENTER / MODIFY HOURS\n"
				+ " 7  - to ENTER / MODIFY SPENDING\n" + " 8  - to TAKE SICKDAYS FOR REGULAR WORKERS\n"
				+ " 9  - to TAKE VACATIONS FOR WORKERS (both regular workers and managers)\n"
				+ " 10 - to CALCULATE PAYCHECK\n"
				+ " 11 - >> TECHNICAL << monthly maintaince\n"
				+ line + "\n"
				+ " 12 - to get DETAILED YEARLY REPORT FOR WORKERS\n"
				+ " 13 - to get DETAILED YEARLY REPORT FOR CLIENTS\n"
				+ " 14 - to get CLIENTS YEARLY CHART\n"
				+ " 15 - to get WORKERS YEARLY CHART\n"
				+ " 16 - to POPULATE WORKERS' HOURS and CLIENTS' SPENDINGS with data\n"
				+ " 17 - to EXIT MENU\n" + line + "\n\n" + "YOUR CHOICE: ");
	}

	public static int tryStringToInt(String myInput) {

		int inputInt = 0;

		try {
			inputInt = Integer.parseInt(myInput);
			return inputInt;
		} catch (NumberFormatException nfe) {
			System.out.println("\n" + "The number entered is not integer");
			return -1;
		}
	}

	public static double tryStringToDouble(String myInput) {

		double inputDouble = 0.0;

		try {
			inputDouble = Double.parseDouble(myInput);
			return inputDouble;
		} catch (NumberFormatException nfe) {
			System.out.println("\n" + "The number entered is not double");
			return -1.0;
		}
	}

	public static void printClients() {

		System.out.println(line + "\nList of clients is following:");
		for (Client client : clients) {
			System.out.print(" - ");
			client.displayInfo();
		}
	}

	public static void printRegWorkers() {

		System.out.println(line + "\nList of regular workers is following:");
		for (RegularWorker regWorker : regWorkers) {
			System.out.print(" - ");
			regWorker.displayInfo();
		}
	}

	public static void printManagers() {

		System.out.println(line + "\nList of managers is following:");
		for (Manager manager : managers) {
			manager.displayInfo();
		}
	}

	// Client search by name + companyName
	public static Client findClient(String name, String companyName) {

		for (Client client : clients) {
			if (client.getName().equalsIgnoreCase(name) && client.getCompanyName().equalsIgnoreCase(companyName)) {
				return client;
			}
		}
		return null;
	}

	// Finds worker by id
	public static RegularWorker findWorkerById(String workerId) {

		if (workerId.isBlank()) {
			System.out.println("Worker id can't be blank");
			return null;
		}

		for (RegularWorker worker : regWorkers) {
			if (worker.getId().equalsIgnoreCase(workerId)) {
				return worker;
			}
		}

		return null;
	}

	// Finds manager by id
	public static Manager findManagerById(String managerId) {

		if (managerId.isBlank()) {
			System.out.println("Manager id can't be blank");
			return null;
		}

		for (Manager manager : managers) {
			if (manager.getId().equalsIgnoreCase(managerId)) {
				return manager;
			}
		}

		return null;
	}

	// Finds client by id
	public static Client findClientById(String clientId) {

		if (clientId.isBlank()) {
			System.out.println("Worker id can't be blank");
			return null;
		}

		for (Client client : clients) {
			if (client.getId().equalsIgnoreCase(clientId)) {
				return client;
			}
		}

		return null;
	}

	// creating new person
	public static boolean addPerson() {

		String personType;
		String personName;
		String companyName;
		Client clientFound;
		double basicSalary;

		// identifying type of person
		System.out.println(line + "\n" + "Person adding menu is chosen..." + "\n" + "\n"
				+ "Please choose TYPE of PERSON:" + "\n" + "  1 - CLIENT" + "\n" + "  2 - MANAGER" + "\n"
				+ "  3 - REGULAR WORKER" + "\n" + "YOUR CHOICE: ");
		inputString = scan.nextLine();
		inputInt = tryStringToInt(inputString);
		if (inputInt == 1) {
			personType = "Client";
		} else if (inputInt == 2) {
			personType = "Manager";
		} else if (inputInt == 3) {
			personType = "RegularWorker";
		} else {
			System.out.println("The value entered is not correct." + "\n" + "Returning to the main menu...");
			return false;
		}

		// identifying person's name
		System.out.println(line + "\n" + personType + " was chosen\n" + line + "\n" + "Please enter person's name: "
				+ "\n" + "NAME: ");
		personName = scan.nextLine();

		if (personName.isBlank()) {
			System.out.println("Name can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}

		System.out.println(line + "\nName " + personName + " was chosen");

		// 2 CASES: 1st - for type CLIENT , 2nd - for types MANAGER and REGULARWORKER

		// client case
		if (personType == "Client") {
			System.out.println(line + "\n" + "Please enter company name: " + "\n" + "COMPANY NAME: ");
			companyName = scan.nextLine();

			if (companyName.isBlank()) {
				System.out.println("Company name can't be blank" + "\n" + "Returning to the main menu...");
				return false;
			}

			System.out.println(line + "\nCompany name " + companyName + " was chosen");

			clientFound = findClient(personName, companyName);

			if (clientFound != null) {
				System.out.println(line + "\nSuch client with name " + personName + " and company name " + companyName
						+ " is already exist with id = " + clientFound.getId());
				return false;
			}

			clients.add(new Client(personName, companyName));
			System.out
					.println(line + "\nClient " + personName + " from company " + companyName + " added successfully");
			return true;
		}

		// case when person is either manager or regular worker
		System.out.println(line + "\n" + "Please enter basic salary: " + "\n" + "BASIC SALARY: ");
		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("Basic salary can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}

		basicSalary = tryStringToDouble(inputString);
		if (basicSalary == -1.0) {
			System.out.println("Basic salary is not correct");
			return false;
		} else if (basicSalary <= 0) {
			System.out.println("Basic salary can't be not positive");
			return false;
		} else {
			if (personType == "Manager") {
				managers.add(new Manager(personName, basicSalary));
				System.out.println(
						line + "\nManager " + personName + " with basic salary " + basicSalary + " added successfully");
				return true;
			}
			regWorkers.add(new RegularWorker(personName, basicSalary));
			System.out.println(line + "\nRegular worker " + personName + " with basic salary " + basicSalary
					+ " added successfully");
			return true;
		}
	}

	// assigns regular worker to manager's team
	public static boolean assignWorker() {

		String managerId;
		Manager managerFound;
		Worker workerFound;

		System.out.println(line + "\nAssigning WORKER TO MANAGER menu is chosen...\n");
		printRegWorkers();
		printManagers();

		System.out.println(
				line + "\n" + "Please choose ID of MANAGER from LIST ABOVE:" + "\n" + line + "\nYOUR CHOICE: ");

		managerId = scan.nextLine();

		if (managerId.isBlank()) {
			System.out.println("MANAGER ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}
		System.out.println(line + "\nManager ID " + managerId + " was chosen");

		System.out.println(line + "\n" + "Please choose from LIST ABOVE ID of WORKER to be assign to manager:" + "\n"
				+ line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("WORKER ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;

		}
		System.out.println(line + "\nWORKER ID " + inputString + " was chosen");

		managerFound = findManagerById(managerId);
		workerFound = findWorkerById(inputString);

		if (workerFound == null) {
			System.out.println("There is no worker with id = " + inputString);
			return false;
		}

		if (managerFound == null) {
			System.out.println("There is no manager with id = " + managerId);
			return false;
		}

		// if already in team
		if (managerFound.getTeam().contains(workerFound)) {
			System.out.println(line + "\nWorker id " + inputString + " is already" + " assigned to manager's id "
					+ managerId + " team");
			return false;
		}

		managerFound.addTeamMember(workerFound);
		System.out.println(line + "\nWorker id " + inputString + " was assigned to team of manager id " + managerId
				+ " successfully");
		return true;
	}

	// allows to set working hours for exact day
	public static boolean enterHours() {

		Worker workerFound;
		Manager managerFound;

		System.out.println(line + "\nENTERING / MODIFYING HOURS menu is chosen...\n");
		printRegWorkers();
		printManagers();

		System.out.println(line + "\n" + "Please choose from LIST ABOVE ID of WORKER or MANAGER:" + "\n" + line
				+ "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;

		}
		System.out.println(line + "\nPERSON ID " + inputString + " was chosen");

		workerFound = findWorkerById(inputString);
		managerFound = findManagerById(inputString);

		if (workerFound == null && managerFound == null) {
			System.out.println("There is no worker and manager with id = " + inputString);
			return false;
		}

		if (workerFound == null) {
			workerFound = managerFound;
		}

		workerFound.printDailyHours();

		System.out.println(line + "\n" + "Please choose number of day in current month " + Person.getCURRENT_MONTH()
				+ " to modify (1 - 30):" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nNUMBER OF DAY " + inputString + " was chosen");

		inputInt = tryStringToInt(inputString);

		if (inputInt < 1 || inputInt > 30) {
			System.out.println("Day number must be between 1 and 30 including");
			return false;
		}

		System.out.println(line + "\n" + "Please choose number for updating hours of day number " + inputInt
				+ " (0 - 24):" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nHOURS " + inputString + " was chosen");

		inputDouble = tryStringToDouble(inputString);

		if (inputDouble < 0 || inputDouble > 24) {
			System.out.println("Hours must be between 0 and 24 including");
			return false;
		}

		workerFound.logHours(Person.getCURRENT_MONTH(), inputInt, inputDouble);
		System.out.println(line + "\nDaily hours for day " + inputInt + " of month " + Person.getCURRENT_MONTH()
				+ " for worker id " + workerFound.getId() + " were updated successfully and updated hours are below");
		workerFound.printDailyHours();
		return true;
	}

	// allows to set spending of client for specific day of specific month
	public static boolean enterSpending() {

		Client clientFound;
		int monthChosen;

		System.out.println(line + "\nENTERING / MODIFYING CLIENT's SPENDING menu is chosen...\n");
		printClients();

		// identifying client's id
		System.out
				.println(line + "\n" + "Please choose from LIST ABOVE ID of CLIENT:" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;

		}
		System.out.println(line + "\nCLIENT ID " + inputString + " was chosen");

		// searching for existing client
		clientFound = findClientById(inputString);

		if (clientFound == null) {
			System.out.println("There is no client with id = " + inputString);
			return false;
		}

		// printing daily spending
		clientFound.printDailySpending();

		// identifying month number
		System.out.println(line + "\n" + "Please choose number of month to modify day within (1 - 12):" + "\n" + line
				+ "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nNUMBER OF MONTH " + inputString + " was chosen");

		monthChosen = tryStringToInt(inputString);

		if (monthChosen < 1 || monthChosen > 12) {
			System.out.println("Month number must be between 1 and 12 including");
			return false;
		}

		System.out.println(
				line + "\n" + "Please choose number of day to modify (1 - 30):" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nNUMBER OF DAY " + inputString + " was chosen");

		inputInt = tryStringToInt(inputString);

		if (inputInt < 1 || inputInt > 30) {
			System.out.println("Day number must be between 1 and 30 including");
			return false;
		}

		System.out.println(line + "\n" + "Please choose amount for updating daily spending for day number " + inputInt
				+ "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nAMOUNT " + inputString + " was chosen");

		inputDouble = tryStringToDouble(inputString);

		if (inputDouble < 0) {
			System.out.println("Daily spending must be not negative");
			return false;
		}

		clientFound.updateDailySpending(monthChosen, inputInt, inputDouble);
		System.out
				.println(line + "\nDaily spendings for day " + inputInt + " in month " + monthChosen + " for client id "
						+ clientFound.getId() + " were updated successfully and updated spendings are below");
		clientFound.printDailySpending();
		return true;
	}

	// allows to set sickdays for regular workers for specific day
	public static boolean enterSickday() {
		RegularWorker workerFound;
		int sickdaysEntered;

		System.out.println(line + "\nENTERING SICKDAYS menu is chosen...\n");
		printRegWorkers();

		System.out.println(
				line + "\n" + "Please choose from LIST ABOVE ID of REGULAR WORKER:" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}
		System.out.println(line + "\nRegular Worker ID " + inputString + " was chosen");

		workerFound = findWorkerById(inputString);

		if (workerFound == null) {
			System.out.println("There is no client with id = " + inputString);
			return false;
		}

		workerFound.printSickDays();

		System.out.println(line + "\n" + "Please choose number of sickdays to take:" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nNUMBER OF DAYS " + inputString + " was chosen");

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}

		inputInt = tryStringToInt(inputString);

		if (inputInt <= 0 || inputInt > RegularWorker.getStartSickDays()) {
			System.out.println("Not correct number of sick days");
			return false;
		}

		// if we have enough sickdays left
		if (workerFound.getSickDays() - inputInt < 0) {
			System.out.println("Taking sickdays from person with id " + workerFound.getId()
					+ "\nCan't take more sickdays than it is left = " + workerFound.getSickDays());
			return false;
		}

		sickdaysEntered = inputInt;

		workerFound.printDailyHours();

		// getting days for which 8 working hours will be set
		System.out.println(line + "\n" + "Please choose " + inputInt + " of sickdays: each on a new line.\n"
				+ " For all of them working hours will be updated to 8");

		// saving days that are entered to forbid repetition
		ArrayList<Integer> daysChosen = new ArrayList<>();
		for (int i = 1; i <= sickdaysEntered; i++) {
			boolean exitDays = false;
			while (!exitDays) {
				System.out.println(line + "\nYOUR CHOICE #" + i + ": ");
				inputString = scan.nextLine();
				System.out.println(line + "\nDAY # " + inputString + " was chosen");
				inputInt = tryStringToInt(inputString);
				if (inputInt == -1) {
					System.out.println("Input was not correct. Try again.");
					continue;
				}
				if (inputInt < 1 || inputInt > 30) {
					System.out.println("Day number must be between 1 and 30 including");
					continue;
				}
				if (daysChosen.contains(inputInt)) {
					System.out.println("Day entered can't be equal to entered before\n"
							+ "Numbers that were entered before are following: \n" + daysChosen);
					continue;
				}
				daysChosen.add(inputInt);

				// updating working hours for specific sickday
				workerFound.logHours(Person.getCURRENT_MONTH(), inputInt, Worker.getDEFAULT_WORKING_HOURS());

				System.out.println("\nFor worker id " + workerFound.getId() + " for day number " + inputInt
						+ " in month " + Person.getCURRENT_MONTH() + " working hours updated to 8");
				exitDays = true;
			}
		}

		// taking sickdays
		if (workerFound.takeSickDays(sickdaysEntered)) {
			System.out.println("\nFor regular worker id " + workerFound.getId() + " " + sickdaysEntered
					+ " sickdays were taken successfully and " + workerFound.getSickDays() + " left");
			workerFound.printDailyHours();
			return true;
		}
		return false;
	}

	public static boolean enterVacation() {
		RegularWorker regWorkerFound;
		Manager managerFound;
		Worker workerFound;
		int vacationsEntered;

		System.out.println(line + "\nENTERING VACATIONS menu is chosen...\n");
		printRegWorkers();
		printManagers();

		System.out.println(line + "\n" + "Please choose from LIST ABOVE ID of REGULAR WORKER or MANAGER:" + "\n" + line
				+ "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;
		}
		System.out.println(line + "\nWorker ID " + inputString + " was chosen");

		// user entered id of either regular worker or manager
		regWorkerFound = findWorkerById(inputString);
		managerFound = findManagerById(inputString);
		workerFound = (regWorkerFound == null ? managerFound : regWorkerFound);

		if (workerFound == null) {
			System.out.println("There is no worker with id = " + inputString);
			return false;
		}

		workerFound.printVacations();

		System.out
				.println(line + "\n" + "Please choose number of vacations to take:" + "\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();
		System.out.println(line + "\nNUMBER OF DAYS " + inputString + " was chosen");

		inputInt = tryStringToInt(inputString);

		if (inputInt <= 0 || inputInt > workerFound.getDefaultVacationDays()) {
			System.out.println("Number of vacation days is not correct");
			return false;
		}

		if (workerFound.getVacationDays() - inputInt < 0) {
			System.out.println("Taking vacations from person with id " + workerFound.getId()
					+ "\nCan't take more vacations than it is left = " + workerFound.getVacationDays());
			return false;
		}

		vacationsEntered = inputInt;

		workerFound.printDailyHours();

		// getting days for which 8 working hours will be set
		System.out.println(line + "\n" + "Please choose " + inputInt + " of vacation days: each on a new line.\n"
				+ " For all of them working hours will be updated to 8");

		// ArrayList for saving days that are entered to forbid repetition
		ArrayList<Integer> daysChosen = new ArrayList<>();
		for (int i = 1; i <= vacationsEntered; i++) {
			boolean exitDays = false;
			while (!exitDays) {
				System.out.println(line + "\nYOUR CHOICE #" + i + ": ");
				inputString = scan.nextLine();
				System.out.println(line + "\nDAY # " + inputString + " was chosen");
				inputInt = tryStringToInt(inputString);
				if (inputInt == -1) {
					System.out.println("Input was not correct. Try again.");
					continue;
				}
				if (inputInt < 1 || inputInt > 30) {
					System.out.println("Day number must be between 1 and 30 including");
					continue;
				}
				if (daysChosen.contains(inputInt)) {
					System.out.println("Day entered can't be equal to entered before\n"
							+ "Numbers that were entered before are following: \n" + daysChosen);
					continue;
				}
				daysChosen.add(inputInt);

				// updating working hours for specific vacation
				workerFound.logHours(Person.getCURRENT_MONTH(), inputInt, Worker.getDEFAULT_WORKING_HOURS());
				System.out.println("\nFor worker id " + workerFound.getId() + " for day number " + inputInt
						+ " working hours updated to 8");
				exitDays = true;
			}
		}

		// taking vacations
		if (workerFound.takeVacationDays(vacationsEntered)) {
			System.out.println("\nFor regular worker id " + workerFound.getId() + " " + vacationsEntered
					+ " vacations were taken successfully and " + workerFound.getVacationDays() + " left");
			workerFound.printDailyHours();
			return true;
		}
		return false;
	}

	// calculates paycheck for specific worker and prints it
	public static boolean calculatePaycheck() {

		Worker workerFound;
		Manager managerFound;

		System.out.println(line + "\nCALCULATE PAYCHECK menu is chosen...\n");
		printRegWorkers();
		printManagers();

		System.out.println(line + "\n" + "Please choose from LIST ABOVE ID of WORKER or MANAGER:" + "\n" + line
				+ "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("ID can't be blank" + "\n" + "Returning to the main menu...");
			return false;

		}
		System.out.println(line + "\nPERSON ID " + inputString + " was chosen");

		workerFound = findWorkerById(inputString);
		managerFound = findManagerById(inputString);

		if (workerFound == null && managerFound == null) {
			System.out.println("There is no worker and manager with id = " + inputString);
			return false;
		}

		if (workerFound == null) {
			workerFound = managerFound;
		}

		workerFound.printDailyHours();

		System.out.println(line + "\n" + "Worker with id " + workerFound.getId() + " earned "
				+ workerFound.calculatePaycheck(Person.getCURRENT_MONTH()) + " this month");
		return true;
	}

	// resets managers' bonuses
	private static void resetManagersBonuses() {
		for (Manager manager : managers) {
			for (int month = 1; month <= 12; month++) {
				manager.setCalculatedBonus(month, 0.0);
			}
		}
	}

	// resets daily hours for workers
	private static void resetDailyHours() {
		for (RegularWorker regWorker : regWorkers) {
			regWorker.eraseDailyHours();
		}
		for (Manager manager : managers) {
			manager.eraseDailyHours();
		}
	}

	// resets daily spendings for clients
	private static void resetDailySpendings() {
		for (Client client : clients) {
			client.eraseDailySpendings();
		}
	}

	/*
	 * - adds 1 to CURRENT_MONTH (after 12 it switches to 1) - if month changes from
	 * 12 to 1 then additionally: - erases workers' daily hours for all days in year
	 * - erases clients' daily spendings for all days in year - resets calculated
	 * bonus for managers enabling it to be calculated again - resets vacations and
	 * sickdays to default values
	 */
	public static boolean monthlyMaintaince() {

		System.out.println(line + "\nRESET ALL DAYS menu is chosen...\n");

		System.out.println(line + "\n"
				+ "This operation is A TECHNICAL ONE and it is allowed to be performed after ending of a month only!\n"
				+ "Please, confirm previous month is over: Y / N\n" + line + "\nYOUR CHOICE: ");

		inputString = scan.nextLine();

		if (inputString.isBlank()) {
			System.out.println("Answer can't be blank" + "\n" + "Returning to the main menu...");
			return false;

		}
		System.out.println(line + "\nAnswer = " + inputString + " was chosen");

		if (inputString.toUpperCase().charAt(0) != 'Y') {
			System.out.println("Returning to the main menu...");
			return false;
		}

		// changes current month to next one
		Person.setCURRENT_MONTH(Person.getCURRENT_MONTH() == 12 ? 1 : Person.getCURRENT_MONTH() + 1);
		System.out.println("Month number was changed to " + Person.getCURRENT_MONTH() + " successfully");

		// if month changes from 12 to 1 then additionally:
		// - erases workers' daily hours for all days in year
		// - erases clients' daily spendings for all days in year
		// - resets calculated bonus for managers enabling it to be calculated again
		// - resets vacations and sickdays to default values
		if (Person.getCURRENT_MONTH() == 1) {

			// creating technical arraylist of class Worker just to use corresponding method
			// in PERSON class
			ArrayList<Worker> entireWorkersList = new ArrayList<>();
			entireWorkersList.addAll(regWorkers);
			entireWorkersList.addAll(managers);

			Person.resetAllDays(entireWorkersList);
			System.out.println(line + "\nRESET ALL DAYS operation was performed successfully!");

			// resets calculated bonuses for managers
			resetManagersBonuses();
			System.out.println("Managers' bonuses reset completed successfully");

			resetDailyHours();
			System.out.println("Workers' daily hours' reset completed successfully");
			resetDailySpendings();
			System.out.println("Clients' daily spendings' reset completed successfully");
		}
		return true;
	}
}
