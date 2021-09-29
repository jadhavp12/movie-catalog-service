package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.model.CatelogItem;
import io.javabrains.moviecatalogservice.model.MovieItem;
import io.javabrains.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
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

    @RequestMapping("/{userId}")
public List<CatelogItem> getCatalog(@PathVariable("userId")  String userId){


        //get all rated movies
        List<Rating> ratings= Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",3)
        );

        //webclient call
//        WebClient.Builder builder= WebClient.builder();//create object in main class

//        RestTemplate restTemplate=new RestTemplate(); // resttemplate is creating object evey time so create object while loading project
//        MovieItem movie= restTemplate.getForObject("http://localhost:8083/movies/"+ratings., MovieItem.class);
//        restTemplate.getForObject("http://localhost:8082/rating/Stree",);


        //for each movie id get call movie info and get movie details
return ratings.stream().map(rating -> {
//    MovieItem movie = restTemplate.getForObject("http://localhost:8083/movies/"+rating.getMovieId(), MovieItem.class) ;

    MovieItem movie= webClientBuilder.build()
            .get()
            .uri("http://localhost:8083/movies/"+rating.getMovieId())
            .retrieve()
            .bodyToMono(MovieItem.class)
            .block();



    return new CatelogItem(movie.getMovieId(), movie.getMovieDesc(), rating.getRating());
}).collect(Collectors.toList());
        //put them all together

//return Collections.singletonList(
//        new CatelogItem("Stree","Horror and Comedy",4)
//);

}
}
