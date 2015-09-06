package miroshnychenko.mykola.popularmovies.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MovieAdapter;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Movie;
import miroshnychenko.mykola.popularmovies.tasks.FetchMoviesTask;
import miroshnychenko.mykola.popularmovies.tasks.FetchReviewsTask;
import miroshnychenko.mykola.popularmovies.tasks.FetchTrailersTask;
import miroshnychenko.mykola.popularmovies.utils.PreferenceUtils;


public class MovieFragment extends Fragment implements FetchMoviesTask.onMoviesDownLoadedListener {

    public static final String TAG = MovieFragment.class.getSimpleName();
    public static final int MOVIE_LOADER = 0;
    private static final String SELECTED_KEY = "selected_position";

    @Bind(R.id.fragment_movies_main_gv)
    GridView mMoviesGV;

    @Bind(R.id.fragment_movies_no_favorite_tv)
    TextView mNoFavoriteTV;

    @Bind(R.id.fragment_movies_progress_bar)
    CircularProgressView mProgressView;

    @Bind(R.id.fragment_movies_no_network_tv)
    TextView mNoNetworkTV;

    PreferenceUtils mPreferenceUtils;

    int mPosition;

    MovieAdapter mMovieAdapter;

    private static final String sMovieIdFavoriteSelection =
            MovieContract.MovieEntry.TABLE_NAME +
                    "." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " IN ";


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
        mMovieAdapter = new MovieAdapter(getActivity());
        mMoviesGV.setAdapter(mMovieAdapter);
        mMoviesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                long movieId = movie.getId();
                ((Callback) getActivity())
                        .onItemSelected(MovieContract.MovieEntry.buildMovieUri(movieId));

                FetchReviewsTask fetchReviewsTask = new FetchReviewsTask(getActivity());
                fetchReviewsTask.execute(movieId);

                FetchTrailersTask fetchTrailersTask = new FetchTrailersTask(getActivity());
                fetchTrailersTask.execute(movieId);

                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onSortCriteriaChanged();
    }

    public void onSortCriteriaChanged() {
        if (mPreferenceUtils.getSortCriteria().equals(getString(R.string.fragment_movies_sort_favorite))) {
            getFavoriteMovies();
        } else {
            updateMovies();
        }
    }

    private void updateMovies() {
        mMoviesGV.setVisibility(View.GONE);
        if (isNetworkAvailable()) {
            FetchMoviesTask moviesTask = new FetchMoviesTask(getActivity(), this);
            String sortCriteria = mPreferenceUtils.getSortCriteria();
            moviesTask.execute(sortCriteria);
            mProgressView.setVisibility(View.VISIBLE);

        }
        else {
            mNoNetworkTV.setVisibility(View.VISIBLE);
        }
    }

    private void getFavoriteMovies() {
        Set<String> movieIds = mPreferenceUtils.getFavoriteMovies();

        if (movieIds.size() > 0 ) {
            StringBuilder sb = new StringBuilder(movieIds.size() * 2 - 1);
            sb.append("?");
            for (int i = 1; i < movieIds.size(); i++) {
                sb.append(", ?");
            }

            String[] movieIdArgs = new String[movieIds.size()];
            movieIds.toArray(movieIdArgs);

            String selection = sMovieIdFavoriteSelection + "(" + sb.toString() + ")";

            Cursor c = getActivity().getContentResolver().query(
                    MovieContract.MovieEntry.CONTENT_URI,
                    null,
                    selection,
                    movieIdArgs,
                    null);

            List<Movie> movies = new ArrayList<>();

            if (c.moveToFirst()) {
               do {
                    Movie movie = new Movie();
                    movie.setId(c.getLong(COL_MOVIE_ID));
                    movie.setMoviePosterPath(c.getString(COL_POSTER_PATH));
                    movies.add(movie);

                } while (c.moveToNext());
                mMovieAdapter.clear();
                mMovieAdapter.addAll(movies);
                mMovieAdapter.notifyDataSetChanged();
            }

            c.close();
        }
        else {
            mMoviesGV.setVisibility(View.GONE);
            mNoFavoriteTV.setVisibility(View.VISIBLE);
            mNoNetworkTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        mNoFavoriteTV.setVisibility(View.GONE);
        mMoviesGV.setVisibility(View.VISIBLE);

        switch (id) {
            case R.id.menu_movies_fragment_sort_popularity_desc:
                mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_popularity_desc_parameter));
                onSortCriteriaChanged();
                return true;
            case R.id.menu_movies_fragment_sort_rating_desc:
                mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_rating_desc_parameter));
                onSortCriteriaChanged();
                return true;
            case R.id.menu_movies_fragment_sort_favorite:
                mPreferenceUtils.saveSortCriteria(getString(R.string.fragment_movies_sort_favorite));
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
    public void onMoviesDownloaded(List<Movie> movies) {
        mMovieAdapter.clear();
        mMovieAdapter.addAll(movies);
        mMovieAdapter.notifyDataSetChanged();
        mProgressView.setVisibility(View.GONE);
        mMoviesGV.setVisibility(View.VISIBLE);

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    @OnClick(R.id.fragment_movies_no_network_tv)
    void tryToDownload() {
        if (isNetworkAvailable()) {
            mNoNetworkTV.setVisibility(View.GONE);
            updateMovies();
        }
    }
}
