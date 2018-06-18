package eu.epfc.mypocketmovie.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 24/04/2018.
 */

public class PocketMovieManager {

    private MovieDatabaseHelper movieDatabaseHelper;

    private static final PocketMovieManager ourInstance = new PocketMovieManager();

    // Singleton instance
    public static PocketMovieManager getInstance() {
        return ourInstance;
    }

    private PocketMovieManager() {
    }

    public void initWithContext(Context context)
    {
        if (movieDatabaseHelper == null) {
            movieDatabaseHelper = new MovieDatabaseHelper(context);
        }
    }

    /***
     * Clear all the current movies in the database and insert new ones
     * @param movies list of movies to be inserted in the database
     */
    public void saveMovies(List<Movie> movies)
    {
        movieDatabaseHelper.clearMovies();

        for (Movie movie : movies )
        {
            movieDatabaseHelper.addMovie(movie.getId(), movie.getTitle(),movie.getRating(),movie.getImagePath());
        }
    }

    /***
     * Read all the movies stored in the database
     * @return a list of movies object
     */
    public List<Movie> getAllMovies(){

        Cursor cursor = movieDatabaseHelper.getReadableDatabase().query("MOVIES",
                new String[]{"_id","ID", "TITLE", "RATING", "IMAGEPATH"},
                null,null,null,null,null);

        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0 ; i < cursor.getCount(); i++)
        {

            if (cursor.moveToNext())
            {
                int id=cursor.getInt(1);
                String title = cursor.getString(2);
                double rating = cursor.getDouble(3);
                String imagepath = cursor.getString(4);
                Movie movie = new Movie(id,title,rating, imagepath);

                movies.add(movie);
            }
        }

        cursor.close();

        return movies;
    }
    public boolean isPresent(MovieDetail movie){
        return isPresent(movie.getId()) ;
    }
    public boolean isPresent(int movieID){
        Cursor cursor = movieDatabaseHelper.getReadableDatabase().query("MOVIES",
                null,
                "ID = ?",new String[]{String.valueOf(movieID)},null,null,null);


       if(cursor.getCount()>0){
           cursor.close();
           return true;
       }else{
           cursor.close();
           return false;
       }
    }
    public void addToDB(MovieDetail movie){
        movieDatabaseHelper.addMovie(movie.getId(),movie.getTitle(),movie.getRating(),movie.getImagepath());
    }
    public void suppressFromDB(MovieDetail movie){
        movieDatabaseHelper.clearMovie(movie.getId());
    }
    private class MovieDatabaseHelper extends SQLiteOpenHelper{

        private static final String DB_NAME = "TMDB";
        private static final int DB_VERSION = 1;

        MovieDatabaseHelper(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sqlInstruction = "CREATE TABLE MOVIES (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID INTEGER,"+
                    "TITLE TEXT," +
                    "RATING NUMBER,"+
                    "IMAGEPATH TEXT)";
            sqLiteDatabase.execSQL(sqlInstruction);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        /***
         *
         * @param title the title of the movie to store in the database
         * @param rating the rating of the movie to store in the database
         * @param imagePath the rating of the movie to store in the database
         */
        public void addMovie(int id, String title, double rating, String imagePath)
        {
            ContentValues movieValues = new ContentValues();
            movieValues.put("ID",Integer.toString(id));
            movieValues.put("TITLE",title);
            movieValues.put("RATING",rating);
            movieValues.put("IMAGEPATH",imagePath);
            getWritableDatabase().insert("MOVIES",null,movieValues);
        }

        /***
         * Delete all the raws in the table ARTICLES
         */
        public void clearMovies() {
            String clearDBQuery = "DELETE FROM MOVIES";
            getWritableDatabase().execSQL(clearDBQuery);
        }
        public void clearMovie(int movieID) {
            String clearDBQuery = "DELETE FROM MOVIES WHERE ID = "+String.valueOf(movieID);
            getWritableDatabase().execSQL(clearDBQuery);
        }
    }
}