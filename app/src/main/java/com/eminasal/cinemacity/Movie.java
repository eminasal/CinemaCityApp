package com.eminasal.cinemacity;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Movie {

    private String movieLink;
    private String movieImage;
    private String movieName;
    private int imageID;
    private int counter = 0;

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage, int counter) throws IOException {
        this.movieImage=movieImage;
        imageID=counter;
    }

    public Movie() throws IOException {
        this.setMovieName(movieName);
        this.setMovieImage(movieImage, counter);
        this.setMovieLink(movieLink);
        setCounter();
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public void setCounter()
    {
        counter++;
    }
}