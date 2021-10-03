package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.model.CatelogItem;
import io.javabrains.moviecatalogservice.model.MovieItem;
import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class CatlogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getCatlogFallback")
    @RequestMapping("/{userId}")
public List<CatelogItem> getCatalog(@PathVariable("userId")  String userId){
        //get all rated movies
        UserRating ratings= restTemplate.getForObject("http://movie-ratings-service/rating/users/"+userId, UserRating.class);

//        WebClient.Builder builder= WebClient.builder();//create object in main class
//        RestTemplate restTemplate=new RestTemplate(); // resttemplate is creating object evey time so create object while loading project

        //for each movie id get call movie info and get movie details
return ratings.getUserRating().stream().map(rating -> {
    MovieItem movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), MovieItem.class) ;

 /*   MovieItem movie= webClientBuilder.build()
            .get()
            .uri("http://localhost:8083/movies/"+rating.getMovieId())
            .retrieve()
            .bodyToMono(MovieItem.class)
            .block();*/

    //put them all together
    return new CatelogItem(movie.getMovieName(), movie.getMovieDesc(), rating.getRating());
}).collect(Collectors.toList());
}


    public List<CatelogItem> getCatlogFallback(@PathVariable("userId")  String userId) {
        return Arrays.asList(new CatelogItem("no movie found","",0));
    }
}
