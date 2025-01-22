package bankingsystem;

public class ValidationException extends Exception {
	private String field;

	public ValidationException() {
		super("Validation Exception!");
	}

	public ValidationException(String field, String msg) {
		super(msg);
		this.field = field;
	}

	@Override
	public String toString() {
		return "Validation Exception! Field " + field + " is invaid";
	}
}
