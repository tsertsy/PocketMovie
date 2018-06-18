package eu.epfc.mypocketmovie.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.mypocketmovie.R;
import eu.epfc.mypocketmovie.controller.PocketMoviesAdapter;
import eu.epfc.mypocketmovie.controller.RecentMoviesAdapter;
import eu.epfc.mypocketmovie.model.Movie;
import eu.epfc.mypocketmovie.model.MovieDetail;
import eu.epfc.mypocketmovie.model.PocketMovieManager;



public class PocketMovieFragment extends MovieFragment {

    static final String TAG = "PocketMovieFragment";

    public PocketMovieFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 1);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_pocket_movie, container, false);
          return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        movies = new ArrayList<>();
        moviesRecyclerView = getView().findViewById(R.id.list_pocket_movies);
        moviesRecyclerView.setItemViewCacheSize(0);
        // set the adapter of the RecyclerView
        moviesAdapter = new PocketMoviesAdapter(this);
        moviesRecyclerView.setAdapter(moviesAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        moviesRecyclerView.setLayoutManager(layoutManager);


        loadMovieData();
    }

    void loadMovieData(){
        PocketMovieManager movieManager= PocketMovieManager.getInstance();
        movies=movieManager.getAllMovies();
        PocketMoviesAdapter moviesAdapter = new PocketMoviesAdapter(this);
        moviesAdapter.setMovieData(movies);
    }
    public static PocketMovieFragment newInstance(int page, String title) {
        PocketMovieFragment movieFragment = new PocketMovieFragment();
        movieFragment.setBundle(page,title);

        return movieFragment;
    }
    public static PocketMovieFragment newInstance(int page) {

        PocketMovieFragment fragment = new PocketMovieFragment();
        fragment.setBundle(page);
        return fragment;
    }
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_pocket_movie, container, false);
    }
}
