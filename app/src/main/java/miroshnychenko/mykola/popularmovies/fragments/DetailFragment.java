package miroshnychenko.mykola.popularmovies.fragments;

import android.content.Intent;
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
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Review;
import miroshnychenko.mykola.popularmovies.models.Trailer;
import miroshnychenko.mykola.popularmovies.utils.PreferenceUtils;

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
    @Bind(R.id.fragment_detail_favorite_iv)
    ImageView mFavoriteIV;

    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_PATH = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_USER_RATING = 5;
    static final int COL_RELEASE_DATE = 6;

    Uri mMovieUri;
    Cursor mMovieCursor;

    List<Review> mReviews;
    ArrayList<Trailer> mTrailers;

    String mTrailerUrl;

    boolean isFavorite;

    PreferenceUtils mPreferenceUtils;

    private ShareActionProvider mShareActionProvider;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        mPreferenceUtils = new PreferenceUtils(getActivity());
        Bundle arguments = getArguments();

        if (arguments != null) {
            mMovieUri = arguments.getParcelable(ARGS_MOVIE_URI);
        }

        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);

        return rootView;
    }


    @OnClick(R.id.fragment_detail_favorite_iv)
    public void markAsFavorite() {

        isFavorite = !isFavorite;

        String movieId = String.valueOf(mMovieCursor.getLong(COL_MOVIE_ID));

        if (isFavorite) {
            mPreferenceUtils.saveFavoriteMovie(movieId);
            mFavoriteIV.setImageResource(R.drawable.favorite);
        } else {
            mPreferenceUtils.deleteFavoriteMovie(movieId);
            mFavoriteIV.setImageResource(R.drawable.not_favorite);
        }
    }

    @OnClick(R.id.fragment_detail_view_reviews_btn)
    public void showReviews() {
        if (mReviews != null) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(ReviewsDialogFragment.FRAGMENT_TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment newFragment = ReviewsDialogFragment.newInstance(mReviews);
            newFragment.show(ft, ReviewsDialogFragment.FRAGMENT_TAG);
        } else {
            Toast.makeText(getActivity(), getString(R.string.activity_details_no_reviews), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.fragment_detail_view_trailers_btn)
    public void showTrailers() {
        if (mTrailers != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(TrailersDialogFragment.FRAGMENT_TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment newFragment = TrailersDialogFragment.newInstance(mTrailers);
            newFragment.show(ft, TrailersDialogFragment.FRAGMENT_TAG);
        } else {
            Toast.makeText(getActivity(), getString(R.string.activity_details_no_trailers), Toast.LENGTH_SHORT).show();
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.detailfragment, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mTrailerUrl != null) {
            mShareActionProvider.setShareIntent(createShareMovieUriIntent());
        }
    }


    private Intent createShareMovieUriIntent() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mTrailerUrl);
        return shareIntent;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mMovieUri) {
            switch (id) {
                case DETAIL_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            mMovieUri,
                            null,
                            null,
                            null,
                            null);
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
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case DETAIL_LOADER:
                mMovieCursor = data;
                if (data != null && data.moveToFirst()) {
                    mTitleTV.setText(data.getString(COL_TITLE));

                    Picasso.with(getActivity())
                            .load(data.getString(COL_POSTER_PATH))

                            .into(mPosterIV);

                    mReleaseDateTV.setText(getActivity().getString(R.string.fragment_detail_release_date, data.getString(COL_RELEASE_DATE)));
                    mRatingTV.setText(getActivity().getString(R.string.fragment_detail_user_rating, data.getString(COL_USER_RATING)));
                    mOverviewTV.setText(data.getString(COL_OVERVIEW));
                    isFavorite = mPreferenceUtils.isFavorite(data.getString(COL_MOVIE_ID));

                    if (isFavorite) {
                        mFavoriteIV.setImageResource(R.drawable.favorite);
                    } else {
                        mFavoriteIV.setImageResource(R.drawable.not_favorite);
                    }

                }
                break;
            case REVIEW_LOADER:
                if (data.moveToFirst()) {
                    mReviews = new ArrayList<>();
                    do {
                        String id = data.getString(1);
                        String author = data.getString(2);
                        String content = data.getString(3);
                        Review review = new Review(id, author, content);
                        mReviews.add(review);
                    } while (data.moveToNext());
                }
                break;
            case TRAILER_LOADER:

                if (data.moveToFirst()) {
                    mTrailers = new ArrayList<>();
                    do {
                        String id = data.getString(1);
                        String key = data.getString(2);
                        String name = data.getString(3);
                        Trailer trailer = new Trailer(id, key, name);
                        mTrailers.add(trailer);
                    } while (data.moveToNext());

                    mTrailerUrl = getString(R.string.fragment_dialog_trailers_youtube_website_dir) + mTrailers.get(0).getKey();

                    if (mShareActionProvider != null) {
                        mShareActionProvider.setShareIntent(createShareMovieUriIntent());
                    }

                }
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
