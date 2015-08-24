package miroshnychenko.mykola.popularmovies;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import miroshnychenko.mykola.popularmovies.activities.DetailActivity;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 8/15/2015.
 */
public class DetailActivityTest extends ActivityUnitTestCase<DetailActivity> {

    private DetailActivity mDetailActivity;
    private TextView mDetailTitleTV;
    private ImageView mPosterIV;
    private Intent mLaunchIntent;
    private Movie mMovie;


    public DetailActivityTest() {
        super(DetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
        mMovie = new Movie("Terminator", "http://img-9gag-fun.9cache.com/photo/am8PQE9_700b.jpg", "overview", 5.0, "27/07");
        mLaunchIntent = new Intent(context, DetailActivity.class);
        mLaunchIntent.putExtra(DetailActivity.EXTRA_MOVIE, mMovie);
        mDetailActivity = getActivity();

    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue(isFinishCalled());

        Movie movie =
                launchIntent.getParcelableExtra(DetailActivity.EXTRA_MOVIE);
        assertNotNull("passed movie is null", movie);
        assertEquals(mMovie.getOriginalTitle(), movie.getOriginalTitle());
    }


    public void testDetailTitleTV_labelText() {
        startActivity(mLaunchIntent, null, null);

        mDetailTitleTV =
                (TextView) mDetailActivity
                        .findViewById(R.id.activity_detail_title_tv);

        mPosterIV = (ImageView) mDetailActivity
                .findViewById(R.id.activity_detail_poster_iv);

        final String expected =
                "Terminator";
        final String actual = mDetailTitleTV.getText().toString();
        assertEquals(expected, actual);
    }

    public void testPreconditions() {
        assertNotNull("mDetailActivity is null", mDetailActivity);
    }
}
