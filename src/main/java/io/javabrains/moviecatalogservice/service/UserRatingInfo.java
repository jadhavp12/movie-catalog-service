package io.javabrains.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getUserRatingFallback",
    commandProperties = {
           @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "5"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "5000")
    }
    )
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://movie-ratings-service/rating/users/"+ userId, UserRating.class);
    }

    public UserRating getUserRatingFallback(String userId) {
        UserRating userRating=new UserRating();
        userRating.setUserRating(Arrays.asList(new Rating("0",0)));
        return userRating;
    }
}
