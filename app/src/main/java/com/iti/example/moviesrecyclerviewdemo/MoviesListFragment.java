package com.iti.example.moviesrecyclerviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MoviesListFragment extends Fragment {

    ArrayList<Movie> moviesList ;
    RecyclerView moviesListRecyclerView ;
    MovieDelegate movieDelegate ;
    Context context ;
    public MoviesListFragment() {
        // Required empty public constructor
    }
    public MoviesListFragment(Context _context){
        context = _context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = (MainActivity)getActivity();
        moviesList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        moviesListRecyclerView = view.findViewById(R.id.rcView_movies_MoviesListFragment);
        moviesListRecyclerView.setLayoutManager(linearLayoutManager);
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(context,moviesList);
        moviesListRecyclerView.setAdapter(moviesListAdapter);

        //fetch data
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                moviesList.addAll((ArrayList<Movie>) msg.obj);
                moviesListAdapter.notifyDataSetChanged();
            }
        };

        new Thread(){
            @Override
            public void run() {
                try {
                    URL moviesURL = new URL("https://api.androidhive.info/json/movies.json");
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) moviesURL.openConnection();
                    httpsURLConnection.connect();
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String jsonStr = "";
                    String line = "";
                    ArrayList<Movie> moviesList = new ArrayList<>();
                    while((line = bufferedReader.readLine()) != null)
                    {
                        jsonStr += line ;
                    }
                    //parse JSON
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for(int index = 0;index < jsonArray.length();index ++)
                    {
                        moviesList.add(new Movie(jsonArray.getJSONObject(index).getString("title"),jsonArray.getJSONObject(index).getString("image"),jsonArray.getJSONObject(index).getString("rating"),jsonArray.getJSONObject(index).getString("releaseYear"),jsonArray.getJSONObject(index).getJSONArray("genre").getString(0)));
                    }
                    Message msg = new Message();
                    msg.obj = moviesList ;
                    handler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}