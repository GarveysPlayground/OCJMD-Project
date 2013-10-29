package suncertify.db;

public class Subcontractor {
	
	 static final int name_Length = 32;
	 
	 static final int location_Length = 64;
	 
	 static final int specialties_Length = 64;
	 
	 static final int size_Length = 6;
	 
	 static final int rate_Length = 8;
	 
	 static final int owner_Length = 8;
	 
	 static final int number_Of_Fields = 6;
	 
	 static final int entry_Length = name_Length + location_Length
			 						 + specialties_Length + size_Length
			 						 + rate_Length + owner_Length;
	 
	 private String name = "";
	 private String location = "";
	 private String specialties = "";
	 private String size = "";
	 private String rate = "";
	 private String owner = "";
	 
	 public Subcontractor(String[] data) {
		 this.name = data[0];
		 this.location = data[1];
		 this.specialties = data[2];
		 this.size = data[3];
		 this.rate = data[4];
		 this.owner = data[5];
	 }
	 
	 public Subcontractor() {
		
	 }
	 

	 public Subcontractor(String name, String location, String specialities,
			 			String size, String rate, String owner) {
		 this.name = name;
		 this.location = location;
		 this.specialties = specialities;
		 this.size = size;
		 this.rate = rate;
		 this.owner = owner;

	 }
	 
	 public String getName() {
		 return this.name;
	 }
	 
	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public String getLocation() {
		 return this.location;
	 }
	 
	 public void setLocation(String location) {
		 this.location = location;
	 }
	 
	 public String getSpecialties() {
		 return this.specialties;
	 }
	 
	 public void setSpecialties(String specialties) {
		 this.specialties = specialties;
	 }
	 
	 public String getSize() {
		 return this.size;
	 }
	 public void setSize( String size ) {
		 this.size = size;
	 }
	 
	 public String getRate() {
		 return this.rate;
	 }
	 
	 public void setRate(String rate) {
		 this.rate = rate;
	 }
	 
	 public String getOwner() {
		 return this.owner;
	 }
	 
	 public void setOwner(String owner) {
		 this.owner = owner;
	 }
}
