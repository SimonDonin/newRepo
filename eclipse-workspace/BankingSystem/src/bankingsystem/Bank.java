package bankingsystem;

import java.util.ArrayList;

public class Bank implements Transactable {
	
	private String name;
	private double balance = 0;
	private ArrayList<Account> accounts = new ArrayList<>();


	
	public Bank(String name) throws ValidationException {
		Account.validateName(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	@Override
	public boolean deposit(double amount) throws ValidationException {
		Account.validateAmount(amount);
		balance += amount;
		return true;
	}

	@Override
	public boolean withdraw(double amount) throws ValidationException, InsufficientFundsException {
		Account.validateAmount(amount);
		Account.isEnoughBalance(amount, balance);
		balance -= amount;
		return true;
	}
	
	// issue loan from bank to account
	public boolean issueLoan(String accountName, double amount) throws ValidationException, InsufficientFundsException, AccountNotFoundException {
		Account accFound;
		// name validation
		Account.validateName(accountName);
		// search for account from accounts
		accFound = findAccount(accountName);
		// check if bank has enough balance and withdraw money from bank's balance
		withdraw(amount);
		// deposit loan into account
		accFound.takeLoan(amount);
		return true;
	}
	
	// return loan from account to bank (either partially or fully)
		public boolean returnLoan(String accountName, double amount) throws ValidationException, InsufficientFundsException, AccountNotFoundException {
			Account accFound;
			// name validation
			Account.validateName(accountName);
			// search for account from accounts
			accFound = findAccount(accountName);
			//if account has enough loan to return
			Account.isEnoughLoan(amount, accFound.getLoanDebt());
			// withdraw money from account
			accFound.returnLoan(amount);
			deposit(amount);
			return true;
		}
	
	public Account findAccount(String accountName) throws ValidationException, AccountNotFoundException {
		Account.validateName(accountName);
		for (Account acc : accounts) {
			if (acc.getName().equalsIgnoreCase(accountName)) {
				return acc;
			}
		}
		throw new AccountNotFoundException("accountName","There is no such account with name " + accountName);
	}

	@Override
	public String toString() {
		if (accounts.isEmpty()) {
			return "\nBank " + "\"" + name + "\" has balance " + Account.round(balance)
			+ " and has no accounts so far";
		}
		
		StringBuilder str = new StringBuilder();
		for (Account acc : accounts) {
			str.append("\t- " + acc.toString() + "\n");
		}
		return "\nBank " + "\"" + name + "\" has balance " + Account.round(balance)
				+ " and following list of accounts:\n" + str;
	}
	
	

}
