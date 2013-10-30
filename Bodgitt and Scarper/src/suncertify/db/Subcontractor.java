/* Project: Bodgitt and Scarper Version 2.3.3
 * @author: Patrick Garvey
 * Last Modified: 28 Oct 2013
 * Subcontractor.java
 */
package suncertify.db;

// TODO: Auto-generated Javadoc
/**
 * The Class Subcontractor.
 */
public class Subcontractor {
	
	 /** The Constant name_Length. */
 	static final int NAME_LENGTH = 32;
	 
	 /** The Constant location_Length. */
 	static final int LOCATION_LENGTH = 64;
	 
	 /** The Constant specialties_Length. */
 	static final int SPECIALTIES_LENGTH = 64;
	 
	 /** The Constant size_Length. */
 	static final int SIZE_LENGTH = 6;
	 
	 /** The Constant rate_Length. */
 	static final int RATE_LENGTH = 8;
	 
	 /** The Constant owner_Length. */
 	static final int OWNER_LENGTH = 8;
	 
	 /** The Constant number_Of_Fields. */
 	static final int NUMBER_OF_FIELDS = 6;
	 
	 /** The Constant entry_Length. */
 	static final int ENTRY_LENGTH = NAME_LENGTH + LOCATION_LENGTH
			 						 + SPECIALTIES_LENGTH + SIZE_LENGTH
			 						 + RATE_LENGTH + OWNER_LENGTH;
	 
	 /** The name. */
 	private String name = "";
	 
 	/** The location. */
 	private String location = "";
	 
 	/** The specialties. */
 	private String specialties = "";
	 
 	/** The size. */
 	private String size = "";
	 
 	/** The rate. */
 	private String rate = "";
	 
 	/** The owner. */
 	private String owner = "";
	 
	 /**
 	 * Instantiates a new subcontractor.
 	 *
 	 * @param data the data
 	 */
 	public Subcontractor(final String[] data) {
		 this.name = data[0];
		 this.location = data[1];
		 this.specialties = data[2];
		 this.size = data[3];
		 this.rate = data[4];
		 this.owner = data[5];
	 }
	 
	 /**
 	 * Instantiates a new subcontractor.
 	 */
 	public Subcontractor() {
		
	 }
	 

	 /**
 	 * Instantiates a new subcontractor.
 	 *
 	 * @param name the name
 	 * @param location the location
 	 * @param specialties the specialties
 	 * @param size the size
 	 * @param rate the rate
 	 * @param owner the owner
 	 */
 	public Subcontractor(String name, String location, String specialties,
			 			String size, String rate, String owner) {
		 this.name = name;
		 this.location = location;
		 this.specialties = specialties;
		 this.size = size;
		 this.rate = rate;
		 this.owner = owner;

	 }
	 
	 /**
 	 * Gets the name.
 	 *
 	 * @return the name
 	 */
 	public final String getName() {
		 return this.name;
	 }
	 
	 /**
 	 * Sets the name.
 	 *
 	 * @param name the new name
 	 */
 	public final void setName(final String name) {
		 this.name = name;
	 }
	 
	 /**
 	 * Gets the location.
 	 *
 	 * @return the location
 	 */
 	public final String getLocation() {
		 return this.location;
	 }
	 
	 /**
 	 * Sets the location.
 	 *
 	 * @param location the new location
 	 */
 	public final void setLocation(final String location) {
		 this.location = location;
	 }
	 
	 /**
 	 * Gets the specialties.
 	 *
 	 * @return the specialties
 	 */
 	public final String getSpecialties() {
		 return this.specialties;
	 }
	 
	 /**
 	 * Sets the specialties.
 	 *
 	 * @param specialties the new specialties
 	 */
 	public final void setSpecialties(final String specialties) {
		 this.specialties = specialties;
	 }
	 
	 /**
 	 * Gets the size.
 	 *
 	 * @return the size
 	 */
 	public final String getSize() {
		 return this.size;
	 }
	 
 	/**
 	 * Sets the size.
 	 *
 	 * @param size the new size
 	 */
 	public final void setSize(final String size) {
		 this.size = size;
	 }
	 
	 /**
 	 * Gets the rate.
 	 *
 	 * @return the rate
 	 */
 	public final String getRate() {
		 return this.rate;
	 }
	 
	 /**
 	 * Sets the rate.
 	 *
 	 * @param rate the new rate
 	 */
 	public final void setRate(final String rate) {
		 this.rate = rate;
	 }
	 
	 /**
 	 * Gets the owner.
 	 *
 	 * @return the owner
 	 */
 	public final String getOwner() {
		 return this.owner;
	 }
	 
	 /**
 	 * Sets the owner.
 	 *
 	 * @param owner the new owner
 	 */
 	public final void setOwner(final String owner) {
		 this.owner = owner;
	 }
}
