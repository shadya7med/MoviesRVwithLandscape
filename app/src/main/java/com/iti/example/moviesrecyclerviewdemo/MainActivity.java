package com.iti.example.moviesrecyclerviewdemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements MovieDelegate{

    public static final String moviesListTag = "MOVIESLIST";
    public static final String movieDetailsTag = "MOVIEDETAILS";
    public static final String movieKey = "MOVIE";
    public static final int REQ_CODE = 50;

    MovieDetailsFragment movieDetailsFragment ;
    MoviesListFragment moviesListFragment ;
    FragmentManager fragmentManager ;
    int orientation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager  = getSupportFragmentManager();
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            if(savedInstanceState == null)
            {
                moviesListFragment = new MoviesListFragment();
                //movieDetailsFragment =  new MovieDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.fc_moviesList_main,moviesListFragment,moviesListTag).commit();
                //fragmentManager.beginTransaction().add(R.id.fc_movieDetails_main,movieDetailsFragment,movieDetailsTag).commit();
            }else{
                moviesListFragment = (MoviesListFragment) fragmentManager.findFragmentByTag(moviesListTag);
                //movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentByTag(movieDetailsTag);
            }
        } else {
            // In portrait
            if(savedInstanceState == null)
            {
                moviesListFragment = new MoviesListFragment();
                fragmentManager.beginTransaction().add(R.id.fc_moviesList_main,moviesListFragment,moviesListTag).commit();
            }else{
                moviesListFragment = (MoviesListFragment) fragmentManager.findFragmentByTag(moviesListTag);
            }

        }





    }

    @Override
    protected void onStart() {
        super.onStart();
        if(moviesListFragment != null)
        {
            moviesListFragment.movieDelegate = this ;
        }
    }

    @Override
    public void sendMovie(Movie movie) {
        /*if(movieDetailsFragment == null)
        {
            movieDetailsFragment = new MovieDetailsFragment();
            fragmentManager.beginTransaction().add(R.id.fc_main,movieDetailsFragment,movieDetailsTag).commit();
        }else{
            movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentByTag(movieDetailsTag);
        }
       movieDetailsFragment.getMovie(movie) ;*/
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            //in Portrait
            Intent openMovieDetailsAct = new Intent(this,MovieDetailsActivity.class);
            openMovieDetailsAct.putExtra(movieKey,movie);
            startActivityForResult(openMovieDetailsAct,REQ_CODE);
        }else{
            //in landscape
            movieDetailsFragment = new MovieDetailsFragment();
            fragmentManager.beginTransaction().add(R.id.fc_movieDetails_main,movieDetailsFragment,movieDetailsTag).commit();
            movieDetailsFragment.getMovie(movie) ;
            /*if(movieDetailsFragment == null)
            {
                movieDetailsFragment = new MovieDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.fc_movieDetails_main,movieDetailsFragment,movieDetailsTag).commit();
            }else{
                movieDetailsFragment = (MovieDetailsFragment) fragmentManager.findFragmentByTag(movieDetailsTag);
            }
            movieDetailsFragment.getMovie(movie) ;*/

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE && data != null){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE){
                Movie movie = (Movie) data.getSerializableExtra(movieKey);
                movieDetailsFragment = new MovieDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.fc_movieDetails_main,movieDetailsFragment,movieDetailsTag).commit();
                movieDetailsFragment.getMovie(movie) ;

            }
        }
    }
}