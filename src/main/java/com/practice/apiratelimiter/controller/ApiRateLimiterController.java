package com.practice.apiratelimiter.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.practice.apiratelimiter.model.RateLimitResponse;
import com.practice.apiratelimiter.redis.service.RedisService;

@RestController
public class ApiRateLimiterController {
	
	@Autowired
	private RedisService redisService ;
		
	@GetMapping("/api/ratelimit/{userId}")
	public ResponseEntity<RateLimitResponse> checkRateLimit(@PathVariable String userId){
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowSecondStripped = now.minusSeconds(now.getSecond());
		DateTimeFormatter format =  
	    	      DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
		String date = nowSecondStripped.format(format);
		LocalDateTime secondStripped = LocalDateTime.parse(date,format);
		RateLimitResponse rateLimitResponse = new RateLimitResponse();
		ResponseEntity<RateLimitResponse> responseEntity ;
		if(redisService.limitRate(userId, secondStripped)) {
			rateLimitResponse.setForwared(true);
			rateLimitResponse.setMessage("Forward the request to the right API");
			responseEntity = new ResponseEntity<RateLimitResponse>(rateLimitResponse, HttpStatus.OK);
		}else {
			rateLimitResponse.setForwared(false);
			rateLimitResponse.setMessage("Throttle the request.Already crossed the limit");
			responseEntity = new ResponseEntity<RateLimitResponse>(rateLimitResponse, HttpStatus.TOO_MANY_REQUESTS);
		}
		
		return responseEntity;
	} 
	

}
