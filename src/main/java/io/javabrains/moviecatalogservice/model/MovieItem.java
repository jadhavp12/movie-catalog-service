package io.javabrains.moviecatalogservice.model;

public class MovieItem {

    private String movieId;
    private String movieDesc;
    private String movieName;

    public MovieItem(){}

    public MovieItem(String movieId, String movieDesc, String movieName) {
        this.movieId = movieId;
        this.movieDesc = movieDesc;
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieDesc() {
        return movieDesc;
    }
}
