package miroshnychenko.mykola.popularmovies.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.helpers.MovieDateHelper;
import miroshnychenko.mykola.popularmovies.models.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra.movie";

    @Bind(R.id.activity_detail_title_tv)
    TextView mTitleTV;
    @Bind(R.id.activity_detail_poster_iv)
    ImageView mPosterIV;
    @Bind(R.id.activity_detail_release_date_tv)
    TextView mReleaseDateTV;
    @Bind(R.id.activity_detail_user_rating_tv)
    TextView mRatingTV;
    @Bind(R.id.activity_detail_overview_tv)
    TextView mOverviewTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        if (getIntent() != null) {

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            if(movie.getOriginalTitle() != null) {
                mTitleTV.setText(movie.getOriginalTitle());
            }

            if (movie.getMoviePosterPath() != null) {
                Picasso.with(getApplicationContext())
                        .load(movie.getMoviePosterPath())
                        .into(mPosterIV);
            }

            if (movie.getReleaseDate() != null) {
                mReleaseDateTV.setText(movie.getReleaseDate());
            }

            if (movie.getUserRating() != 0) {
                mRatingTV.setText(String.valueOf(movie.getUserRating()) + "/10");
            }
            if (movie.getOverview() != null) {
                mOverviewTV.setText(movie.getOverview());
            }

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
