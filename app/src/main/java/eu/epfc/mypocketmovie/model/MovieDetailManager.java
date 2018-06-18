package eu.epfc.mypocketmovie.model;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import eu.epfc.mypocketmovie.controller.RecentMoviesAdapter;
import eu.epfc.mypocketmovie.view.MovieDetailFragment;
import eu.epfc.mypocketmovie.view.RecentMovieFragment;

/**
 * Created by Olivier on 11/06/2018.
 */

public class MovieDetailManager extends MovieManager{
    private MovieDetail selectedMovieDetail;
    static final String TAG = "MovieDetailManager";
    private static final MovieDetailManager ourInstance = new MovieDetailManager();

    public static MovieDetailManager getInstance() {
        return ourInstance;
    }

    private MovieDetailManager() {
    }

    public MovieDetail parseMovieDetailResponse(String jsonString){
        MovieDetail movieDetail=null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMovies = jsonObject.getJSONArray("results");
            for (int i = 0; i<jsonMovies.length(); ++i)
            {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                int id=jsonMovie.getInt("id");
                String title = jsonMovie.getString("original_title");
                String articleAbstract = jsonMovie.getString("abstract");
                double rating=jsonMovie.getDouble("rating");
                String imagepath=jsonMovie.getString("poster_path");
                Date issueDate=new Date(jsonMovie.getString("release_date"));
                String trailerLink="";

                eu.epfc.mypocketmovie.model.Movie newMovie = new  eu.epfc.mypocketmovie.model.Movie(id, title);

                movieDetail= new MovieDetail(id, title,  articleAbstract,  rating,  imagepath, issueDate, trailerLink);
            }
        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }
        return movieDetail;
    }
    @Override
    protected String getTAG(){
        return TAG;
    }

}
