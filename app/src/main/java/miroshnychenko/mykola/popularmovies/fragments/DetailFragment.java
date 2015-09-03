package miroshnychenko.mykola.popularmovies.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MovieAdapter;
import miroshnychenko.mykola.popularmovies.adapters.ReviewAdapter;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Movie;
import miroshnychenko.mykola.popularmovies.models.Review;

/**
 * Created by nsmirosh on 8/24/2015.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARGS_MOVIE_URI = "args.movie.uri";
    public static final String FRAGMENT_TAG = "DetailFragmentTag";
    public static final int DETAIL_LOADER = 1;
    public static final int REVIEW_LOADER = 2;
    public static final int TRAILER_LOADER = 3;

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

//    @Bind(R.id.fragment_detail_review_lv)
//    ListView mReviewLV;


    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_PATH = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_USER_RATING = 5;
    static final int COL_RELEASE_DATE = 6;

    Uri mMovieUri;

//    ReviewAdapter mReviewAdapter;
    Cursor mReviewCursor;
    Cursor mTrailerCursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();

        if (arguments != null) {
            mMovieUri = arguments.getParcelable(ARGS_MOVIE_URI);
        }

        getLoaderManager().initLoader(REVIEW_LOADER, null, this);

//        mReviewLV.setAdapter(mReviewAdapter);
        return rootView;
    }

    @OnClick(R.id.fragment_detail_show_reviews_btn)
    public void showReviews() {
        if (mReviewCursor.moveToFirst()) {
            List<Review> reviews = new ArrayList<>();

            for (int i = 0; i < mReviewCursor.getCount(); i ++) {
                String id = mReviewCursor.getString(1);
                String author = mReviewCursor.getString(2);
                String content = mReviewCursor.getString(3);
                Review review = new Review(id, author, content);
                reviews.add(review);
                mReviewCursor.moveToNext();
            }

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(ReviewsDialogFragment.FRAGMENT_TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment newFragment = ReviewsDialogFragment.newInstance(reviews);
            newFragment.show(ft, ReviewsDialogFragment.FRAGMENT_TAG);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

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
        switch(id) {
            case DETAIL_LOADER:
                if (null != mMovieUri) {
                    return new CursorLoader(
                            getActivity(),
                            mMovieUri,
                            null,
                            null,
                            null,
                            null);
                }
                break;
            case REVIEW_LOADER:
                return new CursorLoader(
                        getActivity(),
                        MovieContract.ReviewEntry.buildReviewsWithMovieIdUri(
                                MovieContract.MovieEntry.getMovieIdFromUri(mMovieUri)),
                        null,
                        null,
                        null,
                        null);
            case TRAILER_LOADER:
                return new CursorLoader(
                        getActivity(),
                        MovieContract.TrailerEntry.buildTrailersWithMovieIdUri(
                                MovieContract.MovieEntry.getMovieIdFromUri(mMovieUri)),
                        null,
                        null,
                        null,
                        null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            case DETAIL_LOADER:
                if (data != null && data.moveToFirst()) {
                    mTitleTV.setText(data.getString(COL_TITLE));

                    Picasso.with(getActivity())
                            .load(data.getString(COL_POSTER_PATH))
                            .into(mPosterIV);

                    mReleaseDateTV.setText(data.getString(COL_RELEASE_DATE));
                    mRatingTV.setText(getActivity().getString(R.string.format_user_rating, data.getString(COL_USER_RATING)));
                    mOverviewTV.setText(data.getString(COL_OVERVIEW));
                }
                break;
            case REVIEW_LOADER:
                mReviewCursor = data;
                break;

            case TRAILER_LOADER:
                mTrailerCursor = data;
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
