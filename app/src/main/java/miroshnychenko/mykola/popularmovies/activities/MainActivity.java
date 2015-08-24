package miroshnychenko.mykola.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MoviesAdapter;
import miroshnychenko.mykola.popularmovies.fragments.DetailFragment;
import miroshnychenko.mykola.popularmovies.fragments.MoviesFragment;
import miroshnychenko.mykola.popularmovies.models.Movie;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviePosterClicks {

    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), DetailFragment.FRAGMENT_TAG)
                        .commit();
            }
        }
    }


    @Override
    public void onMoviePosterClicked(Movie movie) {

        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.ARGS_MOVIE, movie);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
            startActivity(intent);
        }
    }
}
