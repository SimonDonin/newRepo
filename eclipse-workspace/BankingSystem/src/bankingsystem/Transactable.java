package bankingsystem;

public interface Transactable {
	
	boolean deposit(double amount) throws ValidationException;
	
	boolean withdraw(double amount) throws ValidationException, InsufficientFundsException;

}
