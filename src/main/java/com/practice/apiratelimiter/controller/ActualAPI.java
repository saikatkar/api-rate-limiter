package com.practice.apiratelimiter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.practice.apiratelimiter.model.RateLimitResponse;

@RestController
public class ActualAPI {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/do/something/{userId}")
	public ResponseEntity<String> doSomething(@PathVariable String userId){
		
		ResponseEntity<RateLimitResponse> response = restTemplate.getForEntity("http://localhost:8080/api/ratelimit/saikat", RateLimitResponse.class);
		System.out.println(response.getBody().getMessage());
		return new ResponseEntity<String>(response.getBody().getMessage(),response.getStatusCode());
	}

}
