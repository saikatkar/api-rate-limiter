package com.practice.apiratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.practice.apiratelimiter.model.RateLimit;

@Configuration
public class SpringRedisConfig {
	
	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	 
	@Bean
	public RedisTemplate<String, RateLimit> redisTemplate() {
		RedisTemplate<String, RateLimit> redisTemplate = new RedisTemplate<String, RateLimit>();
		redisTemplate.setConnectionFactory(connectionFactory());
		        redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;

	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}