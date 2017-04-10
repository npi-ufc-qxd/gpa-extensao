package ufc.quixada.npi.gpa.exception;

public class GpaExtensaoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public GpaExtensaoException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
