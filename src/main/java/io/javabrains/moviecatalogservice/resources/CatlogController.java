package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.model.CatelogItem;
import io.javabrains.moviecatalogservice.model.MovieItem;
import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;
import io.javabrains.moviecatalogservice.service.MovieInfo;
import io.javabrains.moviecatalogservice.service.UserRatingInfo;
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
import java.util.ArrayList;
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

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;


    @RequestMapping("/{userId}")
public List<CatelogItem> getCatalog(@PathVariable("userId")  String userId){
        //get all rated movies
        UserRating ratings= userRatingInfo.getUserRating(userId);

//        WebClient.Builder builder= WebClient.builder();//create object in main class
//        RestTemplate restTemplate=new RestTemplate(); // resttemplate is creating object evey time so create object while loading project

        //for each movie id get call movie info and get movie details
return ratings.getUserRating().stream().map(rating -> movieInfo.getCatelogItem(rating)).collect(Collectors.toList());
}



}
