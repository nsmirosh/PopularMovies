package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.fragments.MoviesFragment;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


    public List<Movie> mMovies;
    private Context mContext;
    public MoviePosterClicks mListener;

    public static interface MoviePosterClicks {
        public void onMoviePosterClicked(Movie movie);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMovieIV;
        MoviePosterClicks listener;
        List<Movie> movies;

        public ViewHolder(View v) {
            super(v);
            mMovieIV = (ImageView) v.findViewById(R.id.adapter_movies_iv);

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MoviesAdapter(Context context, List<Movie> movies, MoviePosterClicks listener) {
        this.mContext = context;
        mMovies = movies;
        mListener = listener;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movies_row, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int viewPosition = position;
        holder.mMovieIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMoviePosterClicked(mMovies.get(viewPosition));
            }
        });

        Movie movie = mMovies.get(position);
        if (movie.getMoviePosterPath() != null) {
            Picasso.with(mContext)
                    .load(movie.getMoviePosterPath())
                    .into(holder.mMovieIV);

        }

        holder.mMovieIV.setTag(holder);

    }
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
