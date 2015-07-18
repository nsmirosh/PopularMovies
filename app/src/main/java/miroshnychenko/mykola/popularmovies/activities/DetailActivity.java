package miroshnychenko.mykola.popularmovies.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

public class DetailActivity extends ActionBarActivity {

    public static final String EXTRA_MOVIE = "extra.movie";

    @Bind(R.id.activity_detail_title_tv)
    TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getIntent() != null) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            titleTV.setText(movie.getOriginalTitle());
        }
    }
}
