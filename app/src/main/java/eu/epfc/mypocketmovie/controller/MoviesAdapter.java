package eu.epfc.mypocketmovie.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by 0309oltserstevens on 15/06/2018.
 */

public abstract class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    protected List< Movie> movies;
    final protected ListItemClickListener listItemClickListener;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    protected MoviesAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public int  getItemCount(){
        int result;
        if(movies!=null)
        {
            Log.d("MoviesAdapter", String.valueOf(movies.size()));
            result = movies.size();
        }else{
            Log.d("MoviesAdapter","list null");
            result = 0;
        }
        return result;
    }



    public void setMovieData(List<Movie> movies) {

        this.movies = movies;

        //notify the adapter that the data have changed
        this.notifyDataSetChanged();
    }


    protected class MovieViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        protected MovieViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
           MoviesAdapter.this.listItemClickListener.onListItemClick(clickedPosition);
        }
    }
}
