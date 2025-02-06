package journalmanager;

public class NullValidationException extends Exception {
	private String type;
	private String message;
	
	public NullValidationException(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Null ValidationException: " + type + " can't be null!";
	}

	
	
}
