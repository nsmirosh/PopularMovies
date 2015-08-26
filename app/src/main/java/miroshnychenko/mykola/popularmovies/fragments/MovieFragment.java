package miroshnychenko.mykola.popularmovies.fragments;

import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MovieAdapter;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.tasks.FetchMoviesTask;


public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MovieFragment.class.getSimpleName();
    public static final int MOVIE_LOADER = 0;

    String mSortCriteria;

    @Bind(R.id.fragment_movies_main_gv)
    GridView mMoviesGV;
//    @Bind(R.id.fragment_movies_no_network_tv)
//    TextView mNoNetworkTV;
//
//    @Bind(R.id.fragment_movies_progress_bar)
//    CircularProgressView mProgressBar;

    int mPosition;

    MovieAdapter mMovieAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_PATH = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_USER_RATING = 5;
    static final int COL_RELEASE_DATE = 6;


    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri movieUri);
    }


    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        mSortCriteria = getString(R.string.fragment_movies_sort_popularity_desc_parameter);
        fetchMoviesTask();
        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);
        mMoviesGV.setAdapter(mMovieAdapter);

        mMoviesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    ((Callback) getActivity())
                            .onItemSelected(MovieContract.MovieEntry.buildMovieUri(
                                    cursor.getLong(COL_MOVIE_ID)
                            ));
                }
                mPosition = position;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    @OnClick(R.id.fragment_movies_no_network_tv)
//    void tryToDownload() {
//        if (isNetworkAvailable()) {
//            mNoNetworkTV.setVisibility(View.GONE);
//            mMoviesRV.setVisibility(View.VISIBLE);
//            fetchMoviesTask();
//            setHasOptionsMenu(true);
//        }
//    }


//    @Override
//    public void onMoviesDownloaded(List<Movie> movies) {
//        MainActivity mActivity = (MainActivity) getActivity();
//        mMovieAdapter = new MovieAdapter(getActivity(), mActivity);
//        mMoviesRV.setAdapter(mMovieAdapter);
//        mProgressBar.setVisibility(View.GONE);
//        mMoviesRV.setVisibility(View.VISIBLE);
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_movies_fragment_sort_popularity_desc) {
            mSortCriteria = getString(R.string.fragment_movies_sort_popularity_desc_parameter);
            fetchMoviesTask();
            return true;
        }

        if (id == R.id.menu_movies_fragment_sort_rating_desc) {
            mSortCriteria = getString(R.string.fragment_movies_sort_rating_desc_parameter);
            fetchMoviesTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchMoviesTask() {
//        if (isNetworkAvailable()) {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(getActivity());
        fetchMoviesTask.execute(mSortCriteria);
//            mMoviesRV.setVisibility(View.GONE);
//            mProgressBar.setVisibility(View.VISIBLE);

//            setHasOptionsMenu(true);
//        } else {
//            mNoNetworkTV.setVisibility(View.VISIBLE);
//            mMoviesRV.setVisibility(View.GONE);
//        }
    }


//    private boolean isNetworkAvailable() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mLayoutManager = new GridLayoutManager(getActivity(), 4);
//            mMoviesRV.setLayoutManager(mLayoutManager);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mLayoutManager = new GridLayoutManager(getActivity(), 2);
//            mMoviesRV.setLayoutManager(mLayoutManager);
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        data.moveToFirst();
//        Log.d(TAG, data.getString(COL_TITLE));
        mMovieAdapter.swapCursor(data);
//        if (mPosition != ListView.INVALID_POSITION) {
//            // If we don't need to restart the loader, and there's a desired position to restore
//            // to, do so now.
//            mListView.smoothScrollToPosition(mPosition);
//        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }
}
