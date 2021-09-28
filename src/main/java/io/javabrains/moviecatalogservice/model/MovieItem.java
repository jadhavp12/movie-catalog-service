package io.javabrains.moviecatalogservice.model;

public class MovieItem {

    private String movieId;
    private String movieDesc;

    public MovieItem(String movieId, String movieDesc) {
        this.movieId = movieId;
        this.movieDesc = movieDesc;
    }

    public MovieItem() {
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
