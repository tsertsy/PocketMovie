package eu.epfc.mypocketmovie.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.epfc.mypocketmovie.R;
import eu.epfc.mypocketmovie.model.Movie;

/**
 * Created by Olivier on 12/05/2018.
 */

public class PocketMoviesAdapter extends MoviesAdapter {

    public PocketMoviesAdapter(ListItemClickListener listItemClickListener) {
        super(listItemClickListener);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get a layoutInflater from the context attached
        //to the parent view
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        // inflate the layout item_planet in a view
        View movieView =
                layoutInflater.inflate(R.layout.item_pocket_movie,parent,false);

        // create a new ViewHolder with the view planetView
        return new MovieViewHolder(movieView);
    }
    @Override
    public  void onBindViewHolder(MovieViewHolder holder, int position){
        Movie movie = movies.get(position);

        // get the planet name TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;

        TextView titleTextView = itemViewGroup.findViewById(R.id.pocket_item_title);
        titleTextView.setText(movie.getTitle());

        TextView ratingTextView = itemViewGroup.findViewById(R.id.pocket_item_rating);
        ratingTextView.setText(String.valueOf(movie.getRating()));

        if (!movie.getImagePath().isEmpty()) {
            ImageView thumbnailImageView = itemViewGroup.findViewById(R.id.pocket_item_image);
            Picasso.get().load(movie.getImagePath()).into(thumbnailImageView);
        }
    }

}
