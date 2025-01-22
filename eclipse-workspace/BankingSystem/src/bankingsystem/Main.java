package bankingsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static String inputString = "";
	static double inputDouble = 0.0;
	static int inputInt = -1;
	static boolean isMenuExit = false;
	static String line = "-".repeat(50);
	
	public static void main(String[] args) throws ValidationException {
		
		System.out.println("Welcome to Banking System Menu!");
		Bank leumi = new Bank("Leumi");
		/*Account myAcc = new Account("Simon's account");
		leumi.getAccounts().add(myAcc);
		*/
		
		// main menu cycle
		while (!isMenuExit) {
			printUserMenu();
			try {
				inputInt = scan.nextInt();
				System.out.println(line + "\n"
						+ inputInt + " was chosen\n");
				switch (inputInt) {
				case 1 -> System.out.println(leumi);
				case 2 -> increaseBankBalance(leumi,scan);
				case 3 -> decreaseBankBalance(leumi,scan);
				case 4 -> depositAccount(leumi,scan);
				case 5 -> withdrawAccount(leumi,scan);
				case 6 -> addAccount(leumi,scan);
				case 7 -> issueLoan(leumi,scan);
				case 8 -> returnLoan(leumi,scan);
				case 9 -> {
					isMenuExit = true;
					System.out.println("Exiting the menu...");
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
			scan.nextLine();		
		} 
		
	// TODO: handle TypeMismatchException!!!!
		
		
	 /* try { Account myAcc = new Account("Simon's account");
	 * 
	 * Account myAcc2 = new Account("Acc2"); Account myAcc3 = new Account("Acc3");
	 * 
	 * System.out.println(myAcc);
	 * 
	 * myAcc.deposit(333.35); System.out.println(myAcc); myAcc.withdraw(100);
	 * System.out.println(myAcc);
	 * 
	 * myAcc.takeLoan(400); myAcc.withdraw(300); System.out.println(myAcc);
	 * 
	 * myAcc.returnLoan(290); System.out.println(myAcc);
	 * 
	 * myAcc.takeLoan(70);
	 * 
	 * // Bank testing part
	 * 
	 * Bank leumi = new Bank("Leumi");
	 * 
	 * leumi.getAccounts().add(myAcc); leumi.getAccounts().add(myAcc2);
	 * 
	 * 
	 * System.out.println(leumi);
	 * 
	 * System.out.println("just test...");
	 * 
	 * leumi.deposit(666.66); leumi.issueLoan("Simon's account", 555.55);
	 * System.out.println(myAcc); leumi.returnLoan("Simon's account", 333.33);
	 * System.out.println(myAcc); leumi.returnLoan("Simon's account", 335.0);
	 * System.out.println(myAcc); System.out.println(leumi);
	 * 
	 * leumi.issueLoan("Acc2", 779); System.out.println(leumi);
	 * 
	 * myAcc3.deposit(777); leumi.getAccounts().add(myAcc3);
	 * leumi.returnLoan("Acc3", 500);
	 * 
	 * 
	 * } catch (ValidationException e) { System.err.println(e + ". " +
	 * e.getMessage()); System.out.println("Change operation sum"); } catch
	 * (InsufficientFundsException e) { System.err.println(e + ". " +
	 * e.getMessage()); }
	 * 
	 * 
	 * 
	 */
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

	public static void printUserMenu() {
		System.out.println(line + "\nPlease, choose one of the options bellow:\n"
				+ "\t\"1\" - to PRINT Bank's information\n"
				+ "\t\"2\" - to INCREASE Bank's balance\n"
				+ "\t\"3\" - to DECREASE Bank's balance\n"
				+ "\t\"4\" - to INCREASE balance of account\n"
				+ "\t\"5\" - to DECREASE balance of account\n"
				+ "\t\"6\" - to ADD NEW ACCOUNT \n"
				+ "\t\"7\" - to ISSUE NEW LOAN\n"
				+ "\t\"8\" - to RETURN LOAN\n"
				+ "\t\"9\" - to EXIT");
	}

}
