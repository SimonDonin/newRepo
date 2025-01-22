package bankingsystem;

public class Account implements Transactable {

	private String name;
	private double balance = 0;
	private double loanDebt = 0;
	
	public Account(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}
	
	public double getLoanDebt() {
		return loanDebt;
	}

	@Override
	public boolean deposit(double amount) throws ValidationException {
		validateAmount(amount);
		balance += amount;
		System.out.println("Balance of account \"" + name + "\" increased by "
				+ round(amount) + " and it is " + round(balance) + " now");
		return true;
	}

	@Override
	public boolean withdraw(double amount) throws ValidationException, InsufficientFundsException {
		validateAmount(amount);
		isEnoughBalance(amount, balance);
		balance -= amount;
		System.out.println("Balance of account \"" + name + "\" decreased by "
				+ amount + " and it is " + round(balance) + " now");
		return true;
	}

	public static boolean validateAmount(double amount) throws ValidationException {
		if (amount < 0) {
			throw new ValidationException("amount", "Amount must be positive!");
		}
		else {
			return true;
		}
	}
	
	public static boolean isEnoughBalance(double amount, double balance) throws ValidationException, InsufficientFundsException {
		
		validateAmount(amount);
		validateAmount(balance);
		if (amount > balance) {
			throw new InsufficientFundsException("amount", "Balance can't be negative!");
		}
		return true;
	}
	
	public static boolean isEnoughLoan(double amount, double loan) throws ValidationException, InsufficientFundsException {
		
		validateAmount(amount);
		validateAmount(loan);
		if (amount > loan) {
			throw new InsufficientFundsException("Loan", "There is no enough loan left!");
		}
		return true;
	}
	
	
	
	public static boolean validateName(String accountName) throws ValidationException {
		if (accountName.isBlank()) {
			throw new ValidationException("name", "Account name can't be blank!");
		}
		else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Account \"" + name + "\" has balance " + round(balance) 
				+ " and loan debt " + round(loanDebt);
	}
	
	public boolean takeLoan(double amount) throws ValidationException {
		deposit(amount);
		loanDebt += amount;
		System.out.println("Account \"" + name + "\" got loan " + amount);
		return true;
	}
	
	public boolean returnLoan(double amount) throws ValidationException, InsufficientFundsException {
		isEnoughLoan(amount, loanDebt);
		withdraw(amount);
		loanDebt -= amount;
		System.out.println("Account \"" + name + "\" returned loan sum " + round(amount));
		return true;
	}
	
	public static double round(double num) {
		return (double) ((long) Math.round(num * 100)) / 100;
	}

}
