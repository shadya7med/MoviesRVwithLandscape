package com.iti.example.moviesrecyclerviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ImageDownloader extends AsyncTask<String,Void, Bitmap> {
    ImageView imageView ;

    public ImageDownloader(ImageView imageView){
        this.imageView  = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap image = null ;
        try {
            URL imageUrl = new URL(urls[0]);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) imageUrl.openConnection();
            httpsURLConnection.connect();
            InputStream inputStream = httpsURLConnection.getInputStream();
            image = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
