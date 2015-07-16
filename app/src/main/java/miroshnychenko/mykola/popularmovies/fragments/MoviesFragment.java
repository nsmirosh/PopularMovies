package miroshnychenko.mykola.popularmovies.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MoviesAdapter;


public class MoviesFragment extends Fragment {

    public static final String ApiKey = "API Key: d5d716f0c3ba595706ba90ae3138a16a";


    @Bind(R.id.fragment_movies_images_gv)
    GridView imagesGV;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        MoviesAdapter moviesAdapter = new MoviesAdapter(getActivity(), R.layout.movies_list);
        Uri uri = Uri.parse("http://www.dube.com/balls/images/ManipulationBall_Red.jpg");
        Uri uri2 = Uri.parse("http://i.stack.imgur.com/xR5Ag.png?s=32&g=1");
        moviesAdapter.add(uri);
        moviesAdapter.add(uri2);
        imagesGV.setAdapter(moviesAdapter);

        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
