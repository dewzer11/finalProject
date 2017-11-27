package northwind.exception;

@SuppressWarnings("serial")
public class AlreadyShippedException extends Exception {
	
	public AlreadyShippedException(String message) {
		super(message);
	}

}
