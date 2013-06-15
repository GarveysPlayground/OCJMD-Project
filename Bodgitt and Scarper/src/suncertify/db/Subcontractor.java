package suncertify.db;

public class Subcontractor {
	
	 static final int name_Length = 32;
	 
	 static final int location_Length = 64;
	 
	 static final int specialties_Length = 64;
	 
	 static final int size_Length = 6;
	 
	 static final int rate_Length = 8;
	 
	 static final int owner_Length = 8;
	 
	 static final int entry_Length = name_Length + location_Length + 
			 						 specialties_Length + size_Length + 
			 						 rate_Length + owner_Length;
	 
	 private String name = "";
	 private String location = "";
	 private String specialties = "";
	 private int size;
	 private float rate;
	 private int owner;
	 
	 public Subcontractor(String name, String location, String specialities,
			 			  int size, float rate, int owner){
		 
	 }
	 
	 public String getName(){
		 return this.name;
	 }
	 
	 public String getLocation(){
		 return this.location;
	 }
	 
	 public String getSpecialties(){
		 return this.specialties;
	 }
	 
	 public int getSize(){
		 return this.size;
	 }
	 
	 public float getRate(){
		 return this.rate;
	 }
	 
	 public float getOwner(){
		 return this.owner;
	 }
	 
	 public void setOwner(){
		 this.owner = owner;
	 }
}
