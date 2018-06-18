package eu.epfc.mypocketmovie.view;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.mypocketmovie.R;
import eu.epfc.mypocketmovie.controller.MovieFragmentPagerAdapter;
import eu.epfc.mypocketmovie.controller.RecentMoviesAdapter;
import eu.epfc.mypocketmovie.model.HttpRequestService;
import eu.epfc.mypocketmovie.model.Movie;
import eu.epfc.mypocketmovie.model.PocketMovieManager;

public class MainActivity extends AppCompatActivity {
    private Fragment currentFragment;
    MoviePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAdapterViewPagerVariant();

    }

    public void setCurrentFragment(Fragment currentFragment){
        this.currentFragment=currentFragment;
    }
    protected void trailerVideo(View view){
        ((MovieDetailFragment) currentFragment).trailerVideo(currentFragment.getView());
    }
    protected void onCheckBoxClicked(View view){
        ((MovieDetailFragment) currentFragment).onCheckBoxClicked(currentFragment.getView());
    }


    protected void nextPage(View view){
        ((RecentMovieFragment) ((MovieFragment) currentFragment)).nextPage(view);
    }
    protected void previousPage(View view){
        ((RecentMovieFragment) currentFragment).previousPage(view);
    }
    public static class MoviePagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MoviePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return RecentMovieFragment.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return PocketMovieFragment.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return MovieDetailFragment.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
    void setAdapterViewPager(){
        ViewPager viewPager = findViewById(R.id.movie_view_pager);
        adapterViewPager = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(0);
    }
    void setAdapterViewPagerVariant() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.movie_view_pager);
        viewPager.setAdapter(new MovieFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
