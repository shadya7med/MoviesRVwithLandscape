package com.iti.example.moviesrecyclerviewdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String movieDetailsTag = "MOVIEDETAILS";
    public static final String movieKey = "MOVIE";

    FragmentManager fragmentManager;
    MovieDetailsFragment movieDetailsFragment;

    private int orientation ;
    private Movie movie ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        orientation = getResources().getConfiguration().orientation ;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            fragmentManager = getSupportFragmentManager();
            if(savedInstanceState == null )
            {
                movieDetailsFragment = new MovieDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.fc_movieDetails,movieDetailsFragment,movieDetailsTag).commit();
            }else{
                movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentByTag(movieDetailsTag);
            }
            movie = (Movie)getIntent().getSerializableExtra(movieKey);
            movieDetailsFragment.getMovie(movie);
        }else{
            movie = (Movie)getIntent().getSerializableExtra(movieKey);
            Intent data = new Intent(MovieDetailsActivity.this,MainActivity.class);
            data.putExtra(MainActivity.movieKey,movie);
            setResult(RESULT_OK,data);
            finish();
        }

    }
}