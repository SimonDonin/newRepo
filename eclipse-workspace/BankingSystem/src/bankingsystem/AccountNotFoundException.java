package bankingsystem;


public class AccountNotFoundException extends Exception {
	private String field;

	public AccountNotFoundException() {
		super("AccountNotFound Exception!");
	}

	public AccountNotFoundException(String field, String msg) {
		super(msg);
		this.field = field;
	}

	@Override
	public String toString() {
		return "Account Not Found Exception!";
	}
}
