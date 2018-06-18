package eu.epfc.mypocketmovie.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.epfc.mypocketmovie.R;

/**
 * Created by 0309oltserstevens on 18/06/2018.
 */

public class MainFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    protected String title;
    protected int page;
    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment;
        switch(page){
            case 1:
                fragment=new RecentMovieFragment();
                break;
            case 2:
                fragment=new PocketMovieFragment();
                break;
            case 3:
                fragment=new MovieDetailFragment();
                break;
            default:
                fragment=new MainFragment();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getInflaterView(inflater, container);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        return view;
    }
    protected void setBundle(int page, String title) {

        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        this.setArguments(args);

    }
    protected void setBundle(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        this.setArguments(args);
    }
    protected View getInflaterView(LayoutInflater inflater, ViewGroup container){
        return inflater.inflate(R.layout.main_fragment_page, container, false);
    }
}
