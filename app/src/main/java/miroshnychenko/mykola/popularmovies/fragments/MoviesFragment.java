package miroshnychenko.mykola.popularmovies.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.activities.DetailActivity;
import miroshnychenko.mykola.popularmovies.adapters.MoviesAdapter;
import miroshnychenko.mykola.popularmovies.models.Movie;
import miroshnychenko.mykola.popularmovies.tasks.FetchMoviesTask;


public class MoviesFragment extends Fragment implements FetchMoviesTask.OnMoviesDownloadedListener, MoviesAdapter.MoviePosterClicks {

    public static final String TAG = MoviesFragment.class.getSimpleName();

    @Bind(R.id.fragment_movies_main_rv)
    RecyclerView mMoviesRV;

    MoviesAdapter mMoviesAdapter;

    FetchMoviesTask mFetchMoviesTask;

    private RecyclerView.LayoutManager mLayoutManager;

    public MoviesFragment() {
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
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mMoviesRV.setLayoutManager(mLayoutManager);
        executeFetchMoviesTask(R.string.fragment_movies_sort_popularity_desc_parameter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onMoviesDownloaded(List<Movie> movies) {
        mMoviesAdapter = new MoviesAdapter(getActivity(), movies, this);
        mMoviesRV.setAdapter(mMoviesAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_movies_fragment_sort_popularity_desc) {
            executeFetchMoviesTask(R.string.fragment_movies_sort_popularity_desc_parameter);
            return true;
        }

        if (id == R.id.menu_movies_fragment_sort_rating_desc) {
            executeFetchMoviesTask(R.string.fragment_movies_sort_rating_desc_parameter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void executeFetchMoviesTask(int queryStringId) {
        mFetchMoviesTask = new FetchMoviesTask();
        mFetchMoviesTask.mCallback = this;
        mFetchMoviesTask.execute(getString(queryStringId));
    }

    @Override
    public void onMoviePosterClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);


    }
}
