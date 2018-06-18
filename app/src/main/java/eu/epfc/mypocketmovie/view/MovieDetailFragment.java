package eu.epfc.mypocketmovie.view;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.Date;

import eu.epfc.mypocketmovie.R;
import eu.epfc.mypocketmovie.model.Movie;
import eu.epfc.mypocketmovie.model.MovieDetail;
import eu.epfc.mypocketmovie.model.PocketMovieManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends MainFragment {
    final static String TAG="DetailFragment";
    private MovieDetail selectedMovie;

    public MovieDetailFragment() {

        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 2);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setCurrentFragment((Fragment) this);
 //       return inflater.inflate(R.layout.fragment_detail, container, false);
 //       return super.onCreateView(inflater,container,savedInstanceState);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    public void reloadUI()
    {
        if (selectedMovie != null) {

            showUI(true);

            // set movie title TextView
            TextView movieTitleView = getView().findViewById(R.id.detail_movie_title);
            movieTitleView.setText(selectedMovie.getTitle());



            String movieImageName = selectedMovie.getTitle().toLowerCase();
            Resources resources = getResources();
            // get the resource ID of the image file from its name
            int resourceId = resources.getIdentifier(movieImageName, "drawable", getActivity().getPackageName());
            // if there is no image for that movie
            if (resourceId == 0) {
                // get the resource from the generic_planet image
                resourceId = resources.getIdentifier("generic_planet", "drawable", getActivity().getPackageName());
            }
            ImageView imageView = getView().findViewById(R.id.detail_movie_image);
            imageView.setImageResource(resourceId);


            // set movie issue date TextView
            TextView movieIssueDateTextView = getView().findViewById(R.id.detail_movie_issue_date);
            movieIssueDateTextView.setText(selectedMovie.getIssueDate().toString());

            // set movie rating TextView
            TextView movieRatingTextView = getView().findViewById(R.id.detail_movie_rating);
            movieRatingTextView.setText(Double.toString(selectedMovie.getRating()));

            // set movie overview TextView
            TextView movieSummaryTextView = getView().findViewById(R.id.detail_movie_sumamry);
            movieSummaryTextView.setText(selectedMovie.getOverview());

            // set movie trailer link TextView
            TextView movieTrailerLinkTextView = getView().findViewById(R.id.detail_movie_trailer_link);
            movieTrailerLinkTextView.setText(selectedMovie.getTrailerLink());

            CheckBox LocalDBChBx=getView().findViewById(R.id.detail_local_db_checkBox);
            if(PocketMovieManager.getInstance().isPresent(selectedMovie.getId())){
                LocalDBChBx.setChecked(true);
            }else{
                LocalDBChBx.setChecked(false);
            };


        }
        // if there is no planet
        else
        {
            // hide the UI
            showUI(false);
        }
    }

    private void showUI(Boolean show)
    {
        int visibility = show ? View.VISIBLE : View.INVISIBLE;

        TextView movieTitleTextView = getView().findViewById(R.id.detail_movie_title);
        movieTitleTextView.setVisibility(visibility);

        ImageView imageView = getView().findViewById(R.id.detail_movie_image);
        imageView.setVisibility(visibility);

        TextView movieIssueDateTextView = getView().findViewById(R.id.detail_movie_issue_date);
        movieIssueDateTextView.setVisibility(visibility);

        TextView movieRatingTextView = getView().findViewById(R.id.detail_movie_rating);
        movieRatingTextView.setVisibility(visibility);

        TextView movieSummaryTextView = getView().findViewById(R.id.detail_movie_sumamry);
        movieSummaryTextView.setVisibility(visibility);

        TextView movieTrailerLinkTextView = getView().findViewById(R.id.detail_movie_trailer_link);
        movieTrailerLinkTextView.setVisibility(visibility);

    }

    public void setMovie(MovieDetail movie)
    {
        this.selectedMovie = movie;
        reloadUI();
    }
    protected void trailerVideo(View view){
        String videoID=Integer.toString(selectedMovie.getId());
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoID ));

    }
    protected void onCheckBoxClicked(View view){
        CheckBox localDBChBx=getView().findViewById(R.id.detail_local_db_checkBox);
        if(localDBChBx.isChecked()){
            localDBChBx.setChecked(false);
            PocketMovieManager.getInstance().suppressFromDB(selectedMovie);
        }else{
            localDBChBx.setChecked(true);
            PocketMovieManager.getInstance().addToDB(selectedMovie);
        }
    }

    public static MovieDetailFragment newInstance(int page, String title) {
        MovieDetailFragment movieFragment = new MovieDetailFragment();
        movieFragment.setBundle(page, title);
        return movieFragment;
    }
    public static MovieDetailFragment newInstance(int page) {

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setBundle(page);
        return fragment;
    }
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}
