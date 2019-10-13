package com.practice.apiratelimiter.redis.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.practice.apiratelimiter.model.RateLimit;

/**
 * @author saikatkar
 *
 */
@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String, RateLimit> redisTemplate;
	
	public boolean limitRate(String userId,LocalDateTime now) {
		
		RateLimit rateLimit = redisTemplate.opsForValue().get(userId);
		if(rateLimit == null) {
			rateLimit = new RateLimit();
			Map<LocalDateTime,Integer> countMinuteMap = new TreeMap<LocalDateTime, Integer>();
			countMinuteMap.put(now, 1);
			rateLimit.setCountMinuteMap(countMinuteMap);
		}else {
			
			//Remove the old time stamps which are older than one hour
			Iterator<LocalDateTime> iter = rateLimit.getCountMinuteMap().keySet().iterator();
			LocalDateTime oneHourBefore = now.minusHours(1);

			while (iter.hasNext()) {
			    if (iter.next().isBefore(oneHourBefore)) {
			        iter.remove();
			    }
			}
			//Throttle the incoming request if it has crossed the hourly limit
			if(getHourlyCount(rateLimit) > 500) {
				return false;
			}
			Integer countRequestPerMinute = rateLimit.getCountMinuteMap().get(now);
			if(countRequestPerMinute!=null && countRequestPerMinute > 3) {
				//Throttle the request because it has already crossed the count.
				return false;
			}
			//Put the request count per minute in the map.
			rateLimit.getCountMinuteMap().compute(now, (k,v)-> (v == null) ? 1 : v.intValue()+1);
			
			
			
		}
		this.redisTemplate.opsForValue().set(userId, rateLimit);
		return true;
		
	}
	
	private int getHourlyCount(RateLimit rateLimit) {
		int hourlyCount = 0;
		if(rateLimit.getCountMinuteMap().values()!=null) {
			
			hourlyCount = rateLimit.getCountMinuteMap().values().stream().reduce(0,Integer::sum);
		}
		return hourlyCount;
	}

}
