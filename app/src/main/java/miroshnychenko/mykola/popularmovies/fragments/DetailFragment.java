package miroshnychenko.mykola.popularmovies.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 8/24/2015.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ARGS_MOVIE_URI = "args.movie.uri";
    public static final String FRAGMENT_TAG = "DetailFragmentTag";
    public static final int DETAIL_LOADER = 0;

    @Bind(R.id.fragment_detail_title_tv)
    TextView mTitleTV;
    @Bind(R.id.fragment_detail_poster_iv)
    ImageView mPosterIV;
    @Bind(R.id.fragment_detail_release_date_tv)
    TextView mReleaseDateTV;
    @Bind(R.id.fragment_detail_user_rating_tv)
    TextView mRatingTV;
    @Bind(R.id.fragment_detail_overview_tv)
    TextView mOverviewTV;


    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_PATH = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_USER_RATING = 5;
    static final int COL_RELEASE_DATE = 6;

    Uri mMovieUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();

        if (arguments != null) {
            mMovieUri = arguments.getParcelable(ARGS_MOVIE_URI);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public Intent getParentActivityIntent() {
//        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mMovieUri) {
            return new CursorLoader(
                    getActivity(),
                    mMovieUri,
                    null,
                    null,
                    null,
                    null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            mTitleTV.setText(data.getString(COL_TITLE));

            Picasso.with(getActivity())
                    .load(data.getString(COL_POSTER_PATH))
                    .into(mPosterIV);

            mReleaseDateTV.setText(data.getString(COL_RELEASE_DATE));
            mRatingTV.setText(getActivity().getString(R.string.format_user_rating, data.getString(COL_USER_RATING)));
            mOverviewTV.setText(data.getString(COL_OVERVIEW));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
