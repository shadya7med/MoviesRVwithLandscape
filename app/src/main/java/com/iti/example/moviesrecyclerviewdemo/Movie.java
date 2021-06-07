package com.iti.example.moviesrecyclerviewdemo;

import java.io.Serializable;

public class Movie implements Serializable {
    String movieTitle;
    String movieReleaseYear ;
    String movieRating;
    String movieImageLink;
    String movieGenre ;

    public Movie(String _movieTitle,String _movieImageLink,String _movieRating,String _movieReleaseYear,String _movieGenre)
    {
        movieTitle = _movieTitle;
        movieRating= _movieRating;
        movieReleaseYear = _movieReleaseYear;
        movieGenre = _movieGenre;
        movieImageLink = _movieImageLink;
    }

}
