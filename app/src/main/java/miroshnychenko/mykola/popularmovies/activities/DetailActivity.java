package miroshnychenko.mykola.popularmovies.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

public class DetailActivity extends ActionBarActivity {

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

            mTitleTV.setText(movie.getOriginalTitle());

            Picasso.with(getApplicationContext())
                    .load(movie.getMoviePosterPath())
                    .into(mPosterIV);

            mReleaseDateTV.setText(movie.getReleaseDate());
            mRatingTV.setText(String.valueOf(movie.getUserRating()));
            mOverviewTV.setText(movie.getOverview());

        }
    }
}
