package miroshnychenko.mykola.popularmovies.fragments;

import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import miroshnychenko.mykola.popularmovies.tasks.FetchReviewsTask;
import miroshnychenko.mykola.popularmovies.tasks.FetchTrailersTask;
import miroshnychenko.mykola.popularmovies.utils.PreferenceUtils;


public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = MovieFragment.class.getSimpleName();
    public static final int MOVIE_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";

    String mSortCriteria;

    @Bind(R.id.fragment_movies_main_gv)
    GridView mMoviesGV;

    PreferenceUtils mPreferenceUtils;
//    @Bind(R.id.fragment_movies_no_network_tv)
//    TextView mNoNetworkTV;
//
//    @Bind(R.id.fragment_movies_progress_bar)
//    CircularProgressView mProgressBar;

    int mPosition;

    MovieAdapter mMovieAdapter;


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
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        mPreferenceUtils = new PreferenceUtils(getActivity());
        mSortCriteria = getString(R.string.fragment_movies_sort_popularity_desc_parameter);
        onSortCriteriaChanged();
        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);
        mMoviesGV.setAdapter(mMovieAdapter);

        mMoviesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {

                    long movieId  = cursor.getLong(COL_MOVIE_ID);
                    ((Callback) getActivity())
                            .onItemSelected(MovieContract.MovieEntry.buildMovieUri(movieId));

                    FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity());
                    fetchReviewsTask.execute(movieId);

                    FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity());
                    fetchTrailersTask.execute(movieId);
                }
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public void onSortCriteriaChanged() {
        updateMovies();
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask(getActivity());
        String sortCriteria = mPreferenceUtils.getSortCriteria();
        moviesTask.execute(sortCriteria);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_movies_fragment_sort_popularity_desc) {
            mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_popularity_desc_parameter));
            onSortCriteriaChanged();
            return true;
        }

        if (id == R.id.menu_movies_fragment_sort_rating_desc) {
            mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_rating_desc_parameter));
            onSortCriteriaChanged();
            return true;
        }

        if (id == R.id.menu_movies_fragment_sort_favorite) {
//            mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_rating_desc_parameter));
//            onSortCriteriaChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

        mMovieAdapter.swapCursor(data);
        if (mPosition != GridView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mMoviesGV.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }


    //    public void fetchMoviesTask() {
////        if (isNetworkAvailable()) {
//        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(getActivity());
//        fetchMoviesTask.execute(mSortCriteria);
////            mMoviesRV.setVisibility(View.GONE);
////            mProgressBar.setVisibility(View.VISIBLE);
//
////            setHasOptionsMenu(true);
////        } else {
////            mNoNetworkTV.setVisibility(View.VISIBLE);
////            mMoviesRV.setVisibility(View.GONE);
////        }
//    }


//    private boolean isNetworkAvailable() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
//
//    }

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

}
