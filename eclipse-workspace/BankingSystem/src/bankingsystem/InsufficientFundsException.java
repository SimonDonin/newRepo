package bankingsystem;

public class InsufficientFundsException extends Exception {
	
	String type;

	public InsufficientFundsException(String type, String msg) {
		super(msg);
		this.type = type;
	}

	public InsufficientFundsException() {
		super();
	}

	@Override
	public String toString() {
		return "Insufficient Funds Exception with operation " + type;
	}
	
	
	
	

}
