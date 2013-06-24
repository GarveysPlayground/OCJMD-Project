package suncertify.db;

class DuplicateKeyException extends Exception {
	
	public DuplicateKeyException() {
	     super();
	}

	public DuplicateKeyException(String message) {
	    super(message);
	}
}