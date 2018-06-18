package eu.epfc.mypocketmovie.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.epfc.mypocketmovie.R;
import eu.epfc.mypocketmovie.controller.RecentMoviesAdapter;
import eu.epfc.mypocketmovie.model.HttpRequestService;
import eu.epfc.mypocketmovie.model.Movie;
import eu.epfc.mypocketmovie.model.MovieDetail;
import eu.epfc.mypocketmovie.model.MovieDetailManager;
import eu.epfc.mypocketmovie.model.PocketMovieManager;
import eu.epfc.mypocketmovie.model.RecentMovieManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentMovieFragment extends MovieFragment{

    static final String TAG = "RecentMovieFragment";

    public RecentMovieFragment() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setCurrentFragment((Fragment) this);
    //    return inflater.inflate(R.layout.fragment_recent_movie,container, false);
        return super.onCreateView(inflater,container,savedInstanceState);
//        View view=inflater.inflate(R.layout.fragment_recent_movie, container, false);
//        moviesRecyclerView =  view.findViewById(R.id.list_recent_movies);
//        MoviesAdapter adapter = new MoviesAdapter();
//        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        moviesRecyclerView.setAdapter(adapter);
//        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        movies = new ArrayList<>();
        moviesRecyclerView = getView().findViewById(R.id.list_recent_movies);
        moviesRecyclerView.setItemViewCacheSize(0);
        // set the adapter of the RecyclerView
        moviesAdapter    = new RecentMoviesAdapter(this);
        moviesRecyclerView.setAdapter(moviesAdapter);

        // set the layoutManager of the recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        moviesRecyclerView.setLayoutManager(layoutManager);


        loadMovieData();


    }


    public void onListItemClick(int clickedItemIndex) {

        eu.epfc.mypocketmovie.model.Movie selectedMovie = movies.get(clickedItemIndex);
        getDetail(selectedMovie);

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        // if orientation landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE /*&&
                (screenSize == SCREENLAYOUT_SIZE_LARGE || screenSize == SCREENLAYOUT_SIZE_XLARGE  )*/) {

            // send the selected planet to the detail fragment
            MovieDetailFragment detailFragment = (MovieDetailFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

            detailFragment.setMovie(selectedMovieDetail);
        }
        // else : orientation is portrait
        else
        {
            // launch detail activity
            Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
            detailIntent.putExtra("movieObject", selectedMovie);
            startActivity(detailIntent);
        }
    }
    public void onListItemClickVariant(int clickedItemIndex) {

        Movie selectedMovie = movies.get(clickedItemIndex);

        Intent detailIntent = new Intent(this.getActivity(), MainActivity.class);
        detailIntent.putExtra("movieObject", selectedMovie);
    }
    private void loadMovieData()
    {
        loadFromInternet();
    }

    private void loadFromInternet() {
        String json = null;
        HttpListReceiver httpReceiver = new HttpListReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        getActivity().registerReceiver(httpReceiver,intentFilter);
        String urlString = "https://api.themoviedb.org/3/movie/popular?page="+ String.valueOf(pageNb)+"&api_key=ea2dcee690e0af8bb04f37aa35b75075";
        //String urlString = "https://api.themoviedb.org/3/discover/movie?api_key=ea2dcee690e0af8bb04f37aa35b75075";
        HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);
    }
    protected void nextPage(View view){
        pageNb++;
        movies.clear();
        loadFromInternet();

    }
    protected void previousPage(View view){
        pageNb--;
        movies.clear();
        loadFromInternet();
    }





    public class HttpListReceiver  extends BroadcastReceiver {
        String TAG = "HttpReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("httpRequestComplete")) {
                String response = intent.getStringExtra("responseString");
                List<eu.epfc.mypocketmovie.model.Movie> movies = RecentMovieManager.getInstance().parseMovieListResponse(response);
                moviesAdapter.setMovieData(movies);
/*DEBUG_TEST_180607*///            Log.d(TAG,response);
            }else{
                List< eu.epfc.mypocketmovie.model.Movie> movies = PocketMovieManager.getInstance().getAllMovies();
                moviesAdapter.setMovieData(movies);
            }
        }
    }
    public class HttpDetailReceiver  extends BroadcastReceiver {
        String TAG = "HttpDetailReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("httpRequestComplete")) {
                String response = intent.getStringExtra("responseString");
                selectedMovieDetail = MovieDetailManager.getInstance(). parseMovieDetailResponse(response);
/*DEBUG_TEST_180607*///            Log.d(TAG,response);
            }else{
                selectedMovieDetail=null;
            }
        }
    }
    void getDetail(eu.epfc.mypocketmovie.model.Movie movie){
            String json = null;
            HttpDetailReceiver httpReceiver = new HttpDetailReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("httpRequestComplete");
            intentFilter.addAction("httpRequestFailed");
            getActivity().registerReceiver(httpReceiver, intentFilter);
            String urlString = "https://api.themoviedb.org/3/movie/" + String.valueOf(movie.getId()) + "?api_key=ea2dcee690e0af8bb04f37aa35b75075";
            //String urlString = "https://api.themoviedb.org/3/discover/movie?api_key=ea2dcee690e0af8bb04f37aa35b75075";
            HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);
    }
    public static RecentMovieFragment newInstance(int page, String title) {
        RecentMovieFragment movieFragment = new RecentMovieFragment();
        movieFragment.setBundle(page, title);
        return movieFragment;
    }
    public static RecentMovieFragment newInstance(int page) {

        RecentMovieFragment fragment = new RecentMovieFragment();
        fragment.setBundle(page);
        return fragment;
    }
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_recent_movie, container, false);
    }
}
