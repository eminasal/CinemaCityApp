package com.eminasal.cinemacity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import java.util.ArrayList;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private ArrayList<Movie> movies;
    public int position;


    public Adapter( ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_recommendation, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        position = viewHolder.getAdapterPosition();
        String MovieName=movies.get(position).getMovieName();
        viewHolder.text_android.setText(movies.get(i).getMovieName());
        if(movies.get(i).getMovieImage()!= null && movies.get(i).getMovieImage().length()>0)
            Picasso.get().load(movies.get(i).getMovieImage()).resize(150, 150).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
                    return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public View view;




        ImageView img_android;
        TextView text_android;
        String MovieName="Avengers";

        public ViewHolder(View v) {
            super(v);
            view=v;


            img_android = (ImageView)v.findViewById(R.id.img_android);
            text_android = (TextView)v.findViewById(R.id.text_android);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(view.getContext(), Reservation.class);
                    MovieName=movies.get(position).getMovieName();
                    intent.putExtra("MovieName", MovieName);
                    v.getContext().startActivity(intent);
                }
            });
        }


    }


}
