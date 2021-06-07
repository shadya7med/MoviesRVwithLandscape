package com.iti.example.moviesrecyclerviewdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieDetailsFragment extends Fragment {

    private Movie movie;
    TextView movieNameTextView;
    TextView movieReleaseYearTextView;
    TextView movieGenreTextView ;
    RatingBar movieRatingBar ;
    ImageView movieImageView ;
    ViewGroup container ;
    public MovieDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container =container;
        // Inflate the layout for this fragment
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //do Nothing
        }else{
            container.removeAllViews();
        }

        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieNameTextView = view.findViewById(R.id.txtView_movieName_movieDetails);
        movieReleaseYearTextView = view.findViewById(R.id.txtView_movieReleaseYear_movieDetails);
        movieGenreTextView = view.findViewById(R.id.txtView_movieGenre_movieDetails);
        movieImageView = view.findViewById(R.id.imgView_movieImage_movieDetails);
        movieRatingBar = view.findViewById(R.id.rtBar_movieRating_movieDetails);

        if(movie != null)
        {
            movieNameTextView.setText(movie.movieTitle);
            movieReleaseYearTextView.setText(movie.movieReleaseYear);
            movieGenreTextView.setText(movie.movieGenre);
            ImageDownloader imageDownloader = new ImageDownloader(movieImageView);
            imageDownloader.execute(movie.movieImageLink);
            movieRatingBar.setRating(Float.parseFloat(movie.movieRating)/2);
        }else{
            if(container != null)
            {
                container.removeAllViews();
            }

        }

    }

    public void getMovie(Movie _movie){
        movie = _movie;
    }
}