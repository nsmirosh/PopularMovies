package miroshnychenko.mykola.popularmovies.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
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
public class DetailFragment extends Fragment {

    public static final String ARGS_MOVIE = "args.movie";
    public static final String FRAGMENT_TAG = "DetailFragmentTag";

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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARGS_MOVIE)) {
            Movie movie = arguments.getParcelable(ARGS_MOVIE);
            if(movie.getOriginalTitle() != null) {
                mTitleTV.setText(movie.getOriginalTitle());
            }

            if (movie.getMoviePosterPath() != null) {
                Picasso.with(getActivity())
                        .load(movie.getMoviePosterPath())
                        .into(mPosterIV);
            }

            if (movie.getReleaseDate() != null) {
                mReleaseDateTV.setText(movie.getReleaseDate());
            }

            if (movie.getUserRating() != 0) {
                //todo refactor with xliff attribute
                mRatingTV.setText(String.valueOf(movie.getUserRating()) + "/10");
            }
            if (movie.getOverview() != null) {
                mOverviewTV.setText(movie.getOverview());
            }
        }
        return rootView;
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
}
