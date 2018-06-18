package eu.epfc.mypocketmovie.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0309oltserstevens on 15/06/2018.
 */

public class RecentMovieManager extends MovieManager {
    final static String TAG = "RecentMovieManager";
    private static final RecentMovieManager ourInstance = new RecentMovieManager();

    public static RecentMovieManager getInstance() {
        return ourInstance;
    }

    private RecentMovieManager() {
    }
    public List<Movie> parseMovieListResponse(String jsonString){
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMovies = jsonObject.getJSONArray("results");
            for (int i = 0; i<jsonMovies.length(); ++i)
            {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                int id=jsonMovie.getInt("id");
                String title = jsonMovie.getString("title");
                double rating =extractDoubleValueFromJsonMovie(jsonMovie,"popularity");
                String imagepath = jsonMovie.getString("poster_path");

                eu.epfc.mypocketmovie.model.Movie newMovie = new  eu.epfc.mypocketmovie.model.Movie(id, title, rating, imagepath);

                movies.add(newMovie);
            }
        } catch (JSONException e) {
            Log.e(getTAG(),"can't parse json string correctly");
            e.printStackTrace();
        }
        return movies;
    }
    @Override
    protected String getTAG(){
        return TAG;
    }
}
