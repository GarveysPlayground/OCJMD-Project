/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28 Oct 2013
 * DuplicateKeyException.java
 */
package suncertify.db;


/**
 * The Class DuplicateKeyException is used to handle the exception of attempting
 * to create a new record with an already determined database Key value.
 * (For more information on this see choices.txt)
 */
public class DuplicateKeyException extends Exception {
	
	/**
	 * Instantiates a new  zero argument constructor duplicate key exception.
	 */
	public DuplicateKeyException() {
	     super();
	}

	/**
	 * Instantiates a new duplicate key exception.
	 *
	 * @param message the message tp be displayed
	 */
	public DuplicateKeyException(String message) {
	    super(message);
	}
}