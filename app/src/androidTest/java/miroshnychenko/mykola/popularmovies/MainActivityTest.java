package miroshnychenko.mykola.popularmovies;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.TextView;

import miroshnychenko.mykola.popularmovies.activities.MainActivity;
import miroshnychenko.mykola.popularmovies.fragments.MoviesFragment;

/**
 * Created by nsmirosh on 8/24/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {


    private MainActivity mMainActivity;
    private MoviesFragment mMoviesFragment;

    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        mMoviesFragment = (MoviesFragment) mMainActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
        assertNotNull("mMoviesFragment is null", mMoviesFragment);
    }

    @MediumTest
    public void testNoNetworkView() {
        String noNetworkText = getActivity().getString(R.string.fragment_movies_no_network);
        TextView noNetworkTV = (TextView) getActivity().findViewById(R.id.fragment_movies_no_network_tv);
        assertTrue(View.GONE == noNetworkTV.getVisibility());
        assertEquals(noNetworkText, noNetworkTV.getText());
    }

}
