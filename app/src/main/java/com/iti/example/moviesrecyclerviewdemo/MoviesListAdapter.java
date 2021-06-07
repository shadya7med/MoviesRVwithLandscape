package com.iti.example.moviesrecyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder> {


    ArrayList<Movie> moviesList ;
    Context context ;
    public static final String TAG = "msg" ;

    public MoviesListAdapter(Context _context,ArrayList<Movie> _moviesList){
        moviesList = _moviesList;
        context = _context;
    }

    //inflate
    //refer
    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.movie_row,parent,false);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(rowView);
        return moviesViewHolder;
    }

    //populate
    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.movieNameTextView.setText(moviesList.get(position).movieTitle);
        holder.movieReleaseYearTextView.setText(moviesList.get(position).movieReleaseYear);
        ImageDownloader imageDownloader = new ImageDownloader(holder.movieImageView);
        imageDownloader.execute(moviesList.get(position).movieImageLink);
        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.sendMovie(moviesList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImageView ;
        TextView movieNameTextView;
        TextView movieReleaseYearTextView ;
        View rowView ;

        public MoviesViewHolder(@NonNull View _rowView) {
            super(_rowView);
            rowView = _rowView;
            movieNameTextView = rowView.findViewById(R.id.txtView_movieNameValue_movieRow);
            movieReleaseYearTextView = rowView.findViewById(R.id.txtView_movieReleaseYearValue_movieRow);
            movieImageView = rowView.findViewById(R.id.imgView_movieImage_movieRow);

        }
    }
}
