package miroshnychenko.mykola.popularmovies.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.adapters.MoviesAdapter;
import miroshnychenko.mykola.popularmovies.models.Movie;
import miroshnychenko.mykola.popularmovies.tasks.FetchMoviesTask;


public class MoviesFragment extends Fragment implements FetchMoviesTask.OnMoviesDownloadedListener {

    public static final String ApiKey = "API Key: d5d716f0c3ba595706ba90ae3138a16a";


    @Bind(R.id.fragment_movies_images_gv)
    GridView imagesGV;

    MoviesAdapter mMoviesAdapter;

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
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.mCallback = this;
        fetchMoviesTask.execute("popularity.desc");

        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMoviesDownloaded(List<Movie> movies) {
        mMoviesAdapter = new MoviesAdapter(getActivity(), movies);
        imagesGV.setAdapter(mMoviesAdapter);
    }
}
