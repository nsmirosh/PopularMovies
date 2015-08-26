package miroshnychenko.mykola.popularmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.fragments.DetailFragment;
import miroshnychenko.mykola.popularmovies.fragments.MovieFragment;


public class MainActivity extends AppCompatActivity implements MovieFragment.Callback {

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
    public void onItemSelected(Uri movieUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.ARGS_MOVIE_URI, movieUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.setData(movieUri);
            startActivity(intent);
        }
    }
}
