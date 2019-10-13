package com.practice.apiratelimiter.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

public class RateLimit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<LocalDateTime,Integer> countMinuteMap;
	
	private int hourlyCount;

	public Map<LocalDateTime,Integer> getCountMinuteMap() {
		return countMinuteMap;
	}

	public void setCountMinuteMap(Map<LocalDateTime,Integer> countMinuteMap) {
		this.countMinuteMap = countMinuteMap;
	}

	public int getHourlyCount() {
		return hourlyCount;
	}

	public void setHourlyCount(int hourlyCount) {
		this.hourlyCount = hourlyCount;
	}
	
	
	
}
