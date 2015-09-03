package miroshnychenko.mykola.popularmovies.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.ReviewAdapter;
import miroshnychenko.mykola.popularmovies.adapters.TrailerAdapter;
import miroshnychenko.mykola.popularmovies.models.Review;
import miroshnychenko.mykola.popularmovies.models.Trailer;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public class TrailersDialogFragment extends DialogFragment {

    public static final String FRAGMENT_TAG = ReviewsDialogFragment.class.getName();
    public static final String ARGS_TRAILERS = "args.trailers";

    @Bind(R.id.fragment_dialog_trailers_lv)
    ListView mTrailerLV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_trailers, container);
        ButterKnife.bind(this, view);
        getDialog().setTitle(R.string.fragment_dialog_trailers_title);
        Bundle args = getArguments();
        List<Trailer> trailers = args.getParcelableArrayList(ARGS_TRAILERS);
        final TrailerAdapter adapter = new TrailerAdapter(getActivity());
        adapter.addAll(trailers);
        mTrailerLV.setAdapter(adapter);
        mTrailerLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer trailer = adapter.getItem(position);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                            getString(R.string.fragment_dialog_trailers_youtube_app_dir) + trailer.getKey()));

                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.fragment_dialog_trailers_youtube_website_dir) + trailer.getKey()));
                    startActivity(intent);
                }
            }
        });
        return view;
    }


    public static TrailersDialogFragment newInstance(ArrayList<Trailer> trailers) {
        TrailersDialogFragment fragment = new TrailersDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGS_TRAILERS, trailers);
        fragment.setArguments(bundle);
        return fragment;
    }
}
