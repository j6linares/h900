package net.indra.hal9000.h9cp.ejb;

public class ContactoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContactoException() {

	}

	public ContactoException(String message) {
		super(message);
	}

	public ContactoException(Throwable cause) {
		super(cause);
	}

	public ContactoException(String message, Throwable cause) {
		super(message, cause);
	}
}
