/*Program implementation assumes:
	1. Manager can't have another manager, i.e. he can't be a worker of another manager's team
	2. Paycheck calculation:
	   - Out of all 30 days in a month 4 are free from work
	   - Standard hours are 8 per day
	3. Managers have no sick days and their paychecks do not depend on working hours.
	4. Managers have monthly bonuses, that are calculated as a random value between 0 and 0.2 * basic salary.
	5. Once being calculated manager's bonus for the month can't be recalculated without monthly maintaince operation.
	6. Vacations and sickdays do not affect payment. Being entered on a certain day
	   they just add 8 daily hours that day.
	7. Due to taking sickdays or vacations must be simultaneous with setting 8 working hours for each of those days,
		some validation logic is doubled in RegularWorker/Worker and Main classes.
	8. Default sickdays and vacations values are assumed to be reset every month. 
	9. TECHNICAL operation that resets all days additionally resets calculated bonus for managers enabling it to be calculated again.
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
			case 11 -> resetAllDays();
			default -> {
				isMenuExit = true;
				System.out.println("Exiting the menu...");
			}
			}
		}

		

	}
	
		public static void printUserMenu() {
			
			System.out.println(line + "\n"
					+ "CHOOSE one of the following available options:\n"
					+ " 1 - to VIEW a CLIENTS list\n"
					+ " 2 - to VIEW a REGULAR WORKERS list\n"
					+ " 3 - to VIEW a MANAGERS list\n"
					+ " 4 - to ADD a PERSON (client or worker)\n"
					+ " 5 - to ASSIGN REGULAR WORKER to MANAGER's TEAM\n"
					+ " 6 - to ENTER / MODIFY HOURS\n"
					+ " 7 - to ENTER / MODIFY SPENDING\n"
					+ " 8 - to TAKE SICKDAYS FOR REGULAR WORKERS\n"
					+ " 9 - to TAKE VACATIONS FOR WORKERS\n"
					+ " 10 - to CALCULATE PAYCHECK\n"
					+ " 11 - >> TECHNICAL << monthly maintaince\n"
					+ " 12 - to EXIT MENU\n"
					+ line + "\n\n"
					+ "YOUR CHOICE: " );
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
				if (client.getName().equalsIgnoreCase(name) 
						&& client.getCompanyName().equalsIgnoreCase(companyName)) {
					return client;
				}
			}
			return null;
		}
		
		//Finds worker by id
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

		//Finds manager by id
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
		
		//Finds client by id
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
			System.out.println(line + "\n"
					+ "Person adding menu is chosen..." + "\n" + "\n"
					+ "Please choose TYPE of PERSON:" + "\n"
					+ "  1 - CLIENT" + "\n"
					+ "  2 - MANAGER" + "\n"
					+ "  3 - REGULAR WORKER" + "\n"
					+ "YOUR CHOICE: " );
			inputString = scan.nextLine();
			inputInt = tryStringToInt(inputString);
			if (inputInt == 1) {
				personType = "Client";
			} else if (inputInt == 2) {
				personType = "Manager";
			} else if (inputInt == 3) {
				personType = "RegularWorker";
			} else {
				System.out.println("The value entered is not correct." + "\n"
						+ "Returning to the main menu...");
				return false;
			}

			// identifying person's name
			System.out.println(line + "\n"
					+ personType + " was chosen\n"+ line + "\n"
					+ "Please enter person's name: " + "\n"
					+ "NAME: " );
			personName = scan.nextLine();
			
			if (personName.isBlank()) {
				System.out.println("Name can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			
			System.out.println(line + "\nName " + personName + " was chosen");
			
			// 2 CASES: 1st - for type CLIENT , 2nd - for types MANAGER and REGULARWORKER 

			//client case
			if (personType == "Client") {
				System.out.println(line + "\n"
						+ "Please enter company name: " + "\n"
						+ "COMPANY NAME: " );
				companyName = scan.nextLine();
				
				if (companyName.isBlank()) {
					System.out.println("Company name can't be blank" + "\n"
							+ "Returning to the main menu...");
					return false;
				}
				
				System.out.println(line + "\nCompany name " + companyName + " was chosen");
				
				clientFound = findClient(personName, companyName);
				
				if (clientFound != null) {
					System.out.println(line + "\nSuch client with name " + personName
							+ " and company name " + companyName + " is already exist with id = "
							+ clientFound.getId());
					return false;
				}
				
				clients.add(new Client(personName, companyName));
				System.out.println(line + "\nClient " + personName + " from company "
						+ companyName + " added successfully");
				return true;
			}
			
			// case when person is either manager or regular worker
			System.out.println(line + "\n"
					+ "Please enter basic salary: " + "\n"
					+ "BASIC SALARY: " );
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("Basic salary can't be blank" + "\n"
						+ "Returning to the main menu...");
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
					System.out.println(line + "\nManager " + personName + " with basic salary "
							+ basicSalary + " added successfully");
					return true;
				}
				regWorkers.add(new RegularWorker(personName, basicSalary));
				System.out.println(line + "\nRegular worker " + personName + " with basic salary "
						+ basicSalary + " added successfully");
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
			
			System.out.println(line + "\n"
					+ "Please choose ID of MANAGER from LIST ABOVE:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			managerId = scan.nextLine();
			
			if (managerId.isBlank()) {
				System.out.println("MANAGER ID can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			System.out.println(line + "\nManager ID " + managerId + " was chosen");

			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of WORKER to be assign to manager:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("WORKER ID can't be blank" + "\n"
						+ "Returning to the main menu...");
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
				System.out.println(line + "\nWorker id " + inputString + " is already"
						+ " assigned to manager's id " + managerId + " team");
				return false;
			}
			
			managerFound.addTeamMember(workerFound);
			System.out.println(line + "\nWorker id " + inputString + " was assigned to team of manager id "
					+ managerId + " successfully");
			return true;
		}
		
		// allows to set working hours for exact day 
		public static boolean enterHours() {
			
			Worker workerFound;
			Manager managerFound;
			
			System.out.println(line + "\nENTERING / MODIFYING HOURS menu is chosen...\n");
			printRegWorkers();
			printManagers();

			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of WORKER or MANAGER:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
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
		
			System.out.println(line + "\n"
					+ "Please choose number of day to modify (1 - 30):" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nNUMBER OF DAY " + inputString + " was chosen");

			inputInt = tryStringToInt(inputString);
			
			if (inputInt < 1 || inputInt > 30) {
				System.out.println("Day number must be between 1 and 30 including");
				return false;
			}
			
			System.out.println(line + "\n"
					+ "Please choose number for updating hours of day number "
					+ inputInt + " (0 - 24):" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nHOURS " + inputString + " was chosen");

			inputDouble = tryStringToDouble(inputString);
			
			if (inputDouble < 0 || inputDouble > 24) {
				System.out.println("Hours must be between 0 and 24 including");
				return false;
			}
			
			workerFound.logHours(inputInt, inputDouble);
			System.out.println(line + "\nDaily hours for day " + inputInt
					+ " for worker id " + workerFound.getId() + " were updated successfully and updated hours are below");
			workerFound.printDailyHours();
			return true;
		}
		
		// allows to set spending of client for specific day
		public static boolean enterSpending() {
			
			Client clientFound;
			
			System.out.println(line + "\nENTERING / MODIFYING CLIENT's SPENDING menu is chosen...\n");
			printClients();

			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of CLIENT:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
				
			}
			System.out.println(line + "\nCLIENT ID " + inputString + " was chosen");
			
			clientFound = findClientById(inputString);
			
			if (clientFound == null) {
				System.out.println("There is no client with id = " + inputString);
				return false;
			}
				
			clientFound.printDailySpending();
		
			System.out.println(line + "\n"
					+ "Please choose number of day to modify (1 - 30):" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nNUMBER OF DAY " + inputString + " was chosen");

			inputInt = tryStringToInt(inputString);
			
			if (inputInt < 1 || inputInt > 30) {
				System.out.println("Day number must be between 1 and 30 including");
				return false;
			}
			
			System.out.println(line + "\n"
					+ "Please choose amount for updating daily spending for day number "
					+ inputInt + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nAMOUNT " + inputString + " was chosen");

			inputDouble = tryStringToDouble(inputString);
			
			if (inputDouble < 0) {
				System.out.println("Daily spending must be not negative");
				return false;
			}
			
			clientFound.updateDailySpending(inputInt, inputDouble);
			System.out.println(line + "\nDaily spendings for day " + inputInt
					+ " for client id " + clientFound.getId() + " were updated successfully and updated spendings are below");
			clientFound.printDailySpending();
			return true;
		}

		// allows to set sickdays for regular workers for specific day  
		public static boolean enterSickday() {
			RegularWorker workerFound;
			int sickdaysEntered;
			
			System.out.println(line + "\nENTERING SICKDAYS menu is chosen...\n");
			printRegWorkers();
			
			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of REGULAR WORKER:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			System.out.println(line + "\nRegular Worker ID " + inputString + " was chosen");
			
			workerFound = findWorkerById(inputString);
			
			if (workerFound == null) {
				System.out.println("There is no client with id = " + inputString);
				return false;
			}
				
			workerFound.printSickDays();
		
			System.out.println(line + "\n"
					+ "Please choose number of sickdays to take:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nNUMBER OF DAYS " + inputString + " was chosen");
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}

			inputInt = tryStringToInt(inputString);
			
			if (inputInt <= 0 || inputInt > RegularWorker.getStartSickDays()) {
				System.out.println("Not correct number of sick days");
				return false;
			}
			
			// if we have enough sickdays left
			if (workerFound.getSickDays() - inputInt < 0) {
				System.out.println("Taking sickdays from person with id "
						+ workerFound.getId() + "\nCan't take more sickdays than it is left = " + workerFound.getSickDays());
				return false;
			}
			
			sickdaysEntered = inputInt;
						
			workerFound.printDailyHours();
			
			// getting days for which 8 working hours will be set
			System.out.println(line + "\n"
					+ "Please choose " + inputInt + " of sickdays: each on a new line.\n"
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
				workerFound.logHours(inputInt, Worker.getDEFAULT_WORKING_HOURS());
				
				System.out.println("\nFor worker id " + workerFound.getId() + " for day number "
						+ inputInt + " working hours updated to 8");
				exitDays = true;
				}
			}
					
			// taking sickdays		
			if (workerFound.takeSickDays(sickdaysEntered)) {
				System.out.println("\nFor regular worker id " + workerFound.getId() + " "
						+ sickdaysEntered + " sickdays were taken successfully and "
						+ workerFound.getSickDays() + " left");
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
			
			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of REGULAR WORKER or MANAGER:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
			}
			System.out.println(line + "\nRegular Worker ID " + inputString + " was chosen");
			
			// user entered id of either regular worker or manager
			regWorkerFound = findWorkerById(inputString);
			managerFound = findManagerById(inputString);
			workerFound = (regWorkerFound == null ? managerFound : regWorkerFound);
						
			if (workerFound == null) {
				System.out.println("There is no worker with id = " + inputString);
				return false;
			}
				
			workerFound.printVacations();
		
			System.out.println(line + "\n"
					+ "Please choose number of vacations to take:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			System.out.println(line + "\nNUMBER OF DAYS " + inputString + " was chosen");

			inputInt = tryStringToInt(inputString);

			if (inputInt <= 0 || inputInt > workerFound.getDefaultVacationDays()) {
				System.out.println("Number of vacation days is not correct");
				return false;
			}
			
			if (workerFound.getVacationDays() - inputInt < 0) {
				System.out.println("Taking vacations from person with id "
						+ workerFound.getId() + "\nCan't take more vacations than it is left = " + workerFound.getVacationDays());
				return false;
			}
			
			vacationsEntered = inputInt;
						
			workerFound.printDailyHours();
			
			// getting days for which 8 working hours will be set
			System.out.println(line + "\n"
					+ "Please choose " + inputInt + " of vacation days: each on a new line.\n"
					+ " For all of them working hours will be updated to 8");
			
			// saving days that are entered to forbid repetition
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
				workerFound.logHours(inputInt, Worker.getDEFAULT_WORKING_HOURS());
				System.out.println("\nFor worker id " + workerFound.getId() + " for day number "
						+ inputInt + " working hours updated to 8");
				exitDays = true;
				}
			}
					
			// taking vacations
			if (workerFound.takeVacationDays(vacationsEntered)) {
				System.out.println("\nFor regular worker id " + workerFound.getId() + " "
						+ vacationsEntered + " vacations were taken successfully and "
						+ workerFound.getVacationDays() + " left");
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

			System.out.println(line + "\n"
					+ "Please choose from LIST ABOVE ID of WORKER or MANAGER:" + "\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("ID can't be blank" + "\n"
						+ "Returning to the main menu...");
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

			System.out.println(line + "\n"
					+ "Worker with id " + workerFound.getId() + " earned "
					+ workerFound.calculatePaycheck() + " this month");
			return true;
		}
		
		// for all workers resets sickdays and vacations left to default values
		//also rolls back managers' bonuses calculation 
		public static boolean resetAllDays( ) {
			
			
			System.out.println(line + "\nRESET ALL DAYS menu is chosen...\n");
			

			System.out.println(line + "\n"
					+ "This operation is A TECHNICAL ONE and it is allowed to be performed after ending of a month only!\n"
					+ "It resets all sickdays and vacations available to their default monthly values.\n"
					+ "Please, confirm previous month is over: Y / N\n"
					+ line + "\nYOUR CHOICE: " );
					
			inputString = scan.nextLine();
			
			if (inputString.isBlank()) {
				System.out.println("Answer can't be blank" + "\n"
						+ "Returning to the main menu...");
				return false;
				
			}
			System.out.println(line + "\nAnswer = " + inputString + " was chosen");
			
			if (inputString.toUpperCase().charAt(0) != 'Y') {
				System.out.println("Returning to the main menu...");
				return false;
			}
			
			// creating technical arraylist of class Worker just to use corresponding method in PERSON class
			ArrayList<Worker> entireWorkersList = new ArrayList<>();
			entireWorkersList.addAll(regWorkers);
			entireWorkersList.addAll(managers);
			
			Person.resetAllDays(entireWorkersList);
			System.out.println(line + "\nRESET ALL DAYS operation was performed successfully!");
			return true;
		}
		
}
