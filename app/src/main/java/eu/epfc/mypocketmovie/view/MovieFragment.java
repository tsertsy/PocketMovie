package eu.epfc.mypocketmovie.view;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import eu.epfc.mypocketmovie.controller.MoviesAdapter;
import eu.epfc.mypocketmovie.controller.RecentMoviesAdapter;
import eu.epfc.mypocketmovie.model.Movie;
import eu.epfc.mypocketmovie.model.MovieDetail;

/**
 * Created by 0309oltserstevens on 15/06/2018.
 */

public class MovieFragment  extends MainFragment implements MoviesAdapter.ListItemClickListener{
    protected MoviesAdapter moviesAdapter;
    protected RecyclerView moviesRecyclerView;
    protected List< Movie> movies;
    protected int pageNb=1;
    protected MovieDetail selectedMovieDetail;

    public MovieFragment() {
        // Required empty public constructor
    }

    public void onListItemClick(int clickedItemIndex){

    }

}
