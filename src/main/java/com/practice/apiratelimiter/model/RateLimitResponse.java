package com.practice.apiratelimiter.model;

/**
 * @author saikatkar
 *
 */
public class RateLimitResponse {
	
	private String message;
	private boolean isForwared;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isForwared() {
		return isForwared;
	}
	public void setForwared(boolean isForwared) {
		this.isForwared = isForwared;
	}

}
