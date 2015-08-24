package miroshnychenko.mykola.popularmovies.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.fragments.DetailFragment;
import miroshnychenko.mykola.popularmovies.helpers.MovieDateHelper;
import miroshnychenko.mykola.popularmovies.models.Movie;

public class DetailActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE = "extra.movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.ARGS_MOVIE, movie);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_detail_fragment_container, fragment)
                    .commit();
        }
    }
}
