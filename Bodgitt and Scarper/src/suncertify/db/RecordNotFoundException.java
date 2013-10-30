/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28 Oct 2013
 * RecordNotFoundException.java
 */
package suncertify.db;


/**
 * The Class RecordNotFoundException.
 */
public class RecordNotFoundException extends Exception {
			
	/**
 	 * Instantiates a new  zero argument constructor record not found exception.
 	 */
 	public RecordNotFoundException() {
		super();
	}

	/**
	 * Instantiates a new record not found exception.
	 *
	 * @param message the message tobe displayed
	 */
	public RecordNotFoundException(String message) {
		super(message);
	}
}

