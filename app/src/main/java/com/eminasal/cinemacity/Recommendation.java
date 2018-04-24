package com.eminasal.cinemacity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Recommendation extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    StringBuilder sb = new StringBuilder();

    String [] movieImages ;
    String [] movieLinks ;
    String [] movieNames ;
    Integer [] movieIDs;
    int  movieID;


    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public Integer getMovieID() {
        return movieID;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        connection();
        extractFromPage(sb);
        try {
            initViews();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList prepareData() throws IOException {

        ArrayList movieR = new ArrayList<>();
        for(int i=0;i<movieNames.length;i++){
            Movie movie = new Movie();
            movie.setMovieName(movieNames[i]);
            movie.setMovieLink(movieLinks[i]);
            movie.setMovieImage(movieImages[i],i);
            movie.setCounter();
            movieR.add(movie);
        }
        return movieR;
    }

    private void initViews() throws IOException {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList m = prepareData();
        Adapter adapter = new Adapter(m);
        recyclerView.setAdapter(adapter);
    }


    private void connection()
    {
        if(Build.VERSION.SDK_INT>9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = null;
            try {
                url = new URL("http://www.cinemacity.ba");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                displayExceptionMessage(e.getMessage());
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                displayExceptionMessage(e.getMessage());
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            } catch (IOException e) {
            e.printStackTrace();
            displayExceptionMessage(e.getMessage());
        }
        finally {
                sb.toString();
                urlConnection.disconnect();
            }
        }
    }



    private void extractFromPage(StringBuilder text) {
        String splitText = text.toString();
        Integer counter=0;

        int num = StringUtils.countMatches(splitText,"fotka");

        movieImages = new String [num];
        movieLinks = new String [num];
        movieNames = new String [num];
        movieIDs = new Integer [num];

        if (splitText.contains("dani")) {
            int first = splitText.indexOf("dani");
            int last =splitText.indexOf("right desno");
            String program = splitText.substring(first,last);

            do
            {
                int firstIndex= program.indexOf("http://cinemacity.ba/upload/");
                int lastIndex= program.indexOf(".jpg")+4;
                String name = program.substring(firstIndex,lastIndex);
                movieImages[counter] = name ;
                program=program.substring(lastIndex);
                int firstLink = program.indexOf("filmovi.php");
                int lastLink = program.indexOf("title=")-2;
                String link="http://www.cinemacity.ba/"+program.substring(firstLink,lastLink);
                movieLinks[counter]=link;
                int firstName = program.indexOf("naslov:</strong>")+16;
                int lastName= program.indexOf("<strong>Redatelj:")-6;
                movieNames[counter]=program.substring(firstName,lastName);
                movieIDs[counter]=counter;
                counter=counter+1;

            }
            while (program.contains("filmic"));
        }
    }


    private String readStream(InputStream is) throws IOException {
        //StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }


    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
