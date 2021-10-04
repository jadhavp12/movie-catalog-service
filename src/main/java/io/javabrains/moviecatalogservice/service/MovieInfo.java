package io.javabrains.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.model.CatelogItem;
import io.javabrains.moviecatalogservice.model.MovieItem;
import io.javabrains.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getCatelogItemFallback",
    threadPoolKey = "movieInfoPool",
    threadPoolProperties = {
            @HystrixProperty(name="coreSize",value = "20"),
            @HystrixProperty(name="maxQueueSize",value = "10")
    })
    public CatelogItem getCatelogItem(Rating rating) {
        MovieItem movie = restTemplate.getForObject("http://movie-info-service/movies/"+ rating.getMovieId(), MovieItem.class) ;

 /*   MovieItem movie= webClientBuilder.build()
            .get()
            .uri("http://localhost:8083/movies/"+rating.getMovieId())
            .retrieve()
            .bodyToMono(MovieItem.class)
            .block();*/

        //put them all together
        return new CatelogItem(movie.getMovieName(), movie.getMovieDesc(), rating.getRating());
    }

    public CatelogItem getCatelogItemFallback(Rating rating) {
        return new CatelogItem("Movie Not Found","",0);
    }

}
