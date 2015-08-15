package miroshnychenko.mykola.popularmovies;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import miroshnychenko.mykola.popularmovies.activities.DetailActivity;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 8/15/2015.
 */
public class DetailActivityTest extends ActivityInstrumentationTestCase2<DetailActivity> {

    private DetailActivity mDetailActivity;
    private TextView mDetailTitleTV;

    public DetailActivityTest() {
        super(DetailActivity.class);
    }


//    public DetailActivityTest(Class<DetailActivity> activityClass, DetailActivity mDetailActivity) {
//        super(activityClass);
//        this.mDetailActivity = mDetailActivity;
//    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Movie movie = new Movie("Terminator", "http://img-9gag-fun.9cache.com/photo/am8PQE9_700b.jpg", "overview", 5.0, "27/07");

        Intent intent = new Intent();
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        setActivityIntent(intent);

        mDetailActivity = getActivity();
        mDetailTitleTV =
                (TextView) mDetailActivity
                        .findViewById(R.id.activity_detail_title_tv);

    }

    public void testPreconditions() {
        assertNotNull("mDetailActivity is null", mDetailActivity);
        assertNotNull("mDetailTitleTV is null", mDetailTitleTV);

    }


    public void testDetailTitleTV_labelText() {
        final String expected =
                "Terminator";
        final String actual = mDetailTitleTV.getText().toString();
        assertEquals(expected, actual);
    }
}
