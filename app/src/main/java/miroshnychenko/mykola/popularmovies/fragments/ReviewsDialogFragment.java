package miroshnychenko.mykola.popularmovies.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.ReviewAdapter;
import miroshnychenko.mykola.popularmovies.models.Review;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public class ReviewsDialogFragment extends DialogFragment {

    public static final String FRAGMENT_TAG = ReviewsDialogFragment.class.getName();
    public static final String ARGS_REVIEWS = "args.reviews";

    @Bind(R.id.fragment_dialog_reviews_lv)
    ListView mReviewLV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_reviews, container);
        ButterKnife.bind(this, view);
        getDialog().setTitle(R.string.fragment_dialog_reviews_title);
        Bundle args = getArguments();
        List<Review> reviews = Parcels.unwrap(args.getParcelable(ARGS_REVIEWS));
        ReviewAdapter adapter = new ReviewAdapter(getActivity());
        adapter.addAll(reviews);
        mReviewLV.setAdapter(adapter);
        return view;
    }


    public static ReviewsDialogFragment newInstance(List<Review> reviews) {
        ReviewsDialogFragment fragment = new ReviewsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_REVIEWS, Parcels.wrap(reviews));
        fragment.setArguments(bundle);
        return fragment;
    }
}
