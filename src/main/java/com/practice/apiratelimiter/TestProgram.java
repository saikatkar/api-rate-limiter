package com.practice.apiratelimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestProgram {
	
	public static void main(String [] args) {
		
		
	    LocalDateTime current = LocalDateTime.now(); 
	    DateTimeFormatter format =  
	    	      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
	    	    
	    String formatedDateTime = current.format(format); 
	    System.out.println(formatedDateTime);
	    int second = current.getSecond();
	    LocalDateTime current1 = current.minusSeconds(second);
	    
	    DateTimeFormatter format1 =  
	    	      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
	    	    
	    String formatedDateTime1 = current1.format(format1);   
	    System.out.println(formatedDateTime1);
		
	}

}
