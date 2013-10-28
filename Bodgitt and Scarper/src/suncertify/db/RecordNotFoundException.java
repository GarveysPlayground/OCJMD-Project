package suncertify.db;

public class RecordNotFoundException extends Exception {
	 //member class for the RecordNotFoundException exception
	  //It contains a zero argument constructor and a second 
	  //constructor that takes a String that serves as the 
	  //exception's description
			
	public RecordNotFoundException() {
		super();
	}

	public RecordNotFoundException(String message) {
		super(message);
	}
}

