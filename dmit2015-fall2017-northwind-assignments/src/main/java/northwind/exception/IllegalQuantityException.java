package northwind.exception;

@SuppressWarnings("serial")
public class IllegalQuantityException extends Exception {

	public IllegalQuantityException(String message) {
		super(message);
	}
}
