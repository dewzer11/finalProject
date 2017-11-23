package northwind.exception;

@SuppressWarnings("serial")
public class NoInvoiceLinesException extends Exception {

	public NoInvoiceLinesException(String message) {
		super(message);
	}
}
