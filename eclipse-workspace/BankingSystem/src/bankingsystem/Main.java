package bankingsystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	static String inputString = "";
	static double inputDouble = 0.0;
	static int inputInt = -1;
	static boolean isSecondMenuExit = true; // defines if we need to perform a bank choosing part of a cycle (first one)
	static boolean isBasicMenuExit = false; // defines if we need to perform the main bank system menu part of a cycle (second one)
	static String line = "-".repeat(50);
	static ArrayList<Bank> banks = new ArrayList<>();
	static Bank bankChosen;
	
	public static void main(String[] args) throws ValidationException {
		
		// adding 1 bank to our banks list
		banks.add(new Bank("Leumi"));
		System.out.println("Welcome to Banking System!");
		
		// printing banks list before choosing the bank to work with
		printBanks();
		
		// working with scanner with auto closing
		try (Scanner scan = new Scanner(System.in);) {
			
			// cycle for all menus
			while (!isBasicMenuExit || !isSecondMenuExit) {
				
				// first part: choose bank
				if (!isBasicMenuExit) {
					printBasicMenu();
					
					// part for catching exceptions
					try {
						inputInt = scan.nextInt();
						System.out.println(line + "\n"
								+ inputInt + " was chosen\n");
						switch (inputInt) {
						case 1 -> printBanks();
						case 2 -> addBank(scan);
						case 3 -> {
							chooseBank(scan);
							isBasicMenuExit = true;
							isSecondMenuExit = false;
							}
						case 4 -> {
							System.out.println("Quiting the program...");
							System.exit(0);
							}
						default -> {
							System.out.println("No such option... Please, try again");
							continue;
							}
						}
						} catch (ValidationException e) {
							System.out.println(e.getMessage() + ". Try Again!");
						} catch (InputMismatchException e) {
							System.out.println("Incorrect input! Try Again!");
						} catch (AccountNotFoundException e) {
							System.out.println(e.getMessage() + ". Try Again!");
						}
					// cleaning the string for following scanner input
					scan.nextLine();		
					}
				
				// second part: banking system menu for bank chosen in the first part
				if (!isSecondMenuExit) {
					printUserMenu(bankChosen);
					try {
						inputInt = scan.nextInt();
						System.out.println(line + "\n"
								+ inputInt + " was chosen\n");
						switch (inputInt) {
						case 1 -> System.out.println(bankChosen);
						case 2 -> increaseBankBalance(bankChosen,scan);
						case 3 -> decreaseBankBalance(bankChosen,scan);
						case 4 -> depositAccount(bankChosen,scan);
						case 5 -> withdrawAccount(bankChosen,scan);
						case 6 -> addAccount(bankChosen,scan);
						case 7 -> issueLoan(bankChosen,scan);
						case 8 -> returnLoan(bankChosen,scan);
						case 9 -> {
							// back to first part
							isSecondMenuExit = true;
							isBasicMenuExit = false;
							System.out.println("Returning to the bank choosing menu...");
							}
						case 10 -> {
							System.out.println("Exiting the program...");
							System.exit(0);
							}
						default -> {
							System.out.println("No such option... Please, try again");
							continue;
							}
						}
						} catch (ValidationException e) {
							System.out.println(e.getMessage() + ". Try Again!");
						} catch (InputMismatchException e) {
							System.out.println("Incorrect input! Try Again!");
						} catch (InsufficientFundsException e) {
							System.out.println(e.getMessage() + ". Try Again!");
						} catch (AccountNotFoundException e) {
							System.out.println(e.getMessage() + ". Try Again!");
						}
					// cleaning the string for following scanner input
					scan.nextLine();		
					}
				}
			}
}
	
	private static void increaseBankBalance(Bank bank, Scanner scan) throws ValidationException, InputMismatchException {
		double inputDouble;
		System.out.println(line + "\nINCREASE Bank's balance menu is chosen...\n"
				+ bank.getName()+ "'s balance is " + bank.getBalance() + "\n"
				+ "Please, choose amount for bank's balance deposit: \n"
				+ line + "\nYOUR CHOICE: " );
				inputDouble = Account.round(scan.nextDouble());
		bank.deposit(inputDouble);
		System.out.println(bank.getName()+ "'s balance was successfully increased by "
				+ inputDouble + " and now equals " + bank.getBalance());
	}
	
	private static void decreaseBankBalance(Bank bank, Scanner scan) throws ValidationException, InsufficientFundsException, InputMismatchException {
		double inputDouble;
		System.out.println(line + "\nDECREASE Bank's balance menu is chosen...\n"
				+ bank.getName()+ "'s balance is " + bank.getBalance() + "\n"
				+ "Please, choose amount for bank's balance withdrawal: \n"
				+ line + "\nYOUR CHOICE: " );
				inputDouble = Account.round(scan.nextDouble());
		bank.withdraw(inputDouble);
		System.out.println(bank.getName()+ "'s balance was successfully decreased by "
				+ inputDouble + " and now equals " + bank.getBalance());
	}

	private static void depositAccount(Bank bank, Scanner scan) throws ValidationException, InsufficientFundsException, AccountNotFoundException, InputMismatchException {
	double inputDouble;
	Account accFound;
	System.out.println(line + "\nINCREASE account's balance menu is chosen...\n"
			+ "Please, enter account's name for crediting: \n"
			+ line + "\nYOUR CHOICE: " );
	accFound = bank.findAccount(scan.next());
	System.out.println(line + "\nPlease, enter amount for crediting: \n"
			+ line + "\nYOUR CHOICE: " );
	inputDouble = Account.round(scan.nextDouble());
	accFound.deposit(inputDouble);
	System.out.println(accFound.getName()+ "'s balance was successfully increased by "
			+ inputDouble + " and now equals " + accFound.getBalance());
	}

	private static void withdrawAccount(Bank bank, Scanner scan) throws ValidationException, 
	InsufficientFundsException, AccountNotFoundException, InputMismatchException {
	double inputDouble;
	Account accFound;
	System.out.println(line + "\nDECREASE account's balance menu is chosen...\n"
			+ "Please, enter account's name for withdrawal: \n"
			+ line + "\nYOUR CHOICE: " );
	accFound = bank.findAccount(scan.next());
	System.out.println(line + "\nPlease, enter amount for withdrawal: \n"
			+ line + "\nYOUR CHOICE: " );
	inputDouble = Account.round(scan.nextDouble());
	accFound.withdraw(inputDouble);
	System.out.println(accFound.getName()+ "'s balance was successfully decreased by "
			+ inputDouble + " and now equals " + accFound.getBalance());
	}
	
	private static void addAccount(Bank bank, Scanner scan) throws ValidationException {
		Account accFound;
		String inputStr;
		System.out.println(line + "\nCREATE ACCOUNT menu is chosen...\n"
				+ "Please, enter name of account to create: \n"
				+ line + "\nYOUR CHOICE: " );
		inputStr = scan.next();
		try {
			accFound = bank.findAccount(inputStr);
			System.out.println("Account with name " + inputStr + " already exists!");
			return;
			} catch (AccountNotFoundException e) {
				bank.getAccounts().add(new Account(inputStr));
				System.out.println("Account \"" + inputStr + "\" was created succesfully");
			}
		
	}
	
	private static void issueLoan(Bank bank, Scanner scan) throws ValidationException, InsufficientFundsException, AccountNotFoundException, InputMismatchException {
		double inputDouble;
		Account accFound;
		String inputStr;
		System.out.println(line + "\nISSUE LOAN menu is chosen...\n"
				+ "Please, enter account's name for getting loan: \n"
				+ line + "\nYOUR CHOICE: " );
		inputStr = scan.next();
		accFound = bank.findAccount(inputStr);
		System.out.println(line + "\nPlease, enter amount for loan to be issued: \n"
				+ line + "\nYOUR CHOICE: " );
		inputDouble = Account.round(scan.nextDouble());
		bank.issueLoan(inputStr, inputDouble);
		}
	
	private static void returnLoan(Bank bank, Scanner scan) throws ValidationException, InsufficientFundsException, AccountNotFoundException, InputMismatchException {
		double inputDouble;
		Account accFound;
		String inputStr;
		System.out.println(line + "\nRETURN LOAN menu is chosen...\n"
				+ "Please, enter account's name for returning loan: \n"
				+ line + "\nYOUR CHOICE: " );
		inputStr = scan.next();
		accFound = bank.findAccount(inputStr);
		System.out.println(line + "\nPlease, enter amount for loan to be returned: \n"
				+ line + "\nYOUR CHOICE: " );
		inputDouble = Account.round(scan.nextDouble());
		bank.returnLoan(inputStr, inputDouble);
		}

	public static void printUserMenu(Bank bank) {
		System.out.println(line + "\nWELCOME TO " + bank.getName().toUpperCase() + "\'S OPTIONS MENU!\n"
				+ "Please, choose one of the options bellow:\n"
				+ "\t\"1\" - to PRINT Bank's information\n"
				+ "\t\"2\" - to INCREASE Bank's balance\n"
				+ "\t\"3\" - to DECREASE Bank's balance\n"
				+ "\t\"4\" - to INCREASE balance of account\n"
				+ "\t\"5\" - to DECREASE balance of account\n"
				+ "\t\"6\" - to ADD NEW ACCOUNT \n"
				+ "\t\"7\" - to ISSUE NEW LOAN\n"
				+ "\t\"8\" - to RETURN LOAN\n"
				+ "\t\"9\" - BACK TO BANK CHOICE / BACK TO EXIT");
	}
	
	public static Bank findBank(String bankName) throws ValidationException, AccountNotFoundException {
		Account.validateName(bankName);
		for (Bank bank : banks) {
			if (bank.getName().equalsIgnoreCase(bankName)) {
				return bank;
			}
		}
		throw new AccountNotFoundException("bankName","There is no such bank with name " + bankName);
	}
	
	private static void addBank(Scanner scan) throws ValidationException {
		
		String inputStr;
		Bank bankFound;
		System.out.println(line + "\nCREATE BANK menu is chosen...\n"
				+ "Please, enter name of bank to create: \n"
				+ line + "\nYOUR CHOICE: " );
		scan.nextLine();
		inputStr = scan.nextLine();
		try {
			bankFound = findBank(inputStr);
			System.out.println("Bank with name " + inputStr + " already exists!");
			return;
			} catch (AccountNotFoundException e) {
				banks.add(new Bank(inputStr));
				System.out.println("Bank \"" + inputStr + "\" was created succesfully");
			}
		
	}
	
	public static void printBanks() {
		System.out.println("Banks list is following:");
		for (Bank bank : banks) {
			System.out.println(bank);
			}
	}
	
	
	public static void printBasicMenu() {
		System.out.println(line + "\nWelcome to CHOOSE BANK menu!\n"
				+ "Please, choose one of the options bellow:\n"
				+ "\t\"1\" - to PRINT Banks list\n"
				+ "\t\"2\" - to Create new bank\n"
				+ "\t\"3\" - to CHOOSE BANK to work with\n"
				+ "\t\"4\" - to QUIT THE PROGRAM");
	}
	
	private static void chooseBank(Scanner scan) throws ValidationException, AccountNotFoundException {
		String inputStr;
		System.out.println(line + "\nCHOOSE BANK menu is chosen...\n"
				+ "Please, enter name of BANK to WORK with: \n"
				+ line + "\nYOUR CHOICE: " );
		scan.nextLine();
		inputStr = scan.nextLine();
			bankChosen = findBank(inputStr);
			System.out.println("Bank with name " + inputStr + " was chosen");
		
	}

}
