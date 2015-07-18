package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public static final String moviePosterBasePath = "http://image.tmdb.org/t/p/w500";
    private List<Movie> mMovies;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mMovieIV;

        public ViewHolder(View v) {
            super(v);
            mMovieIV = (ImageView) v.findViewById(R.id.adapter_movies_iv);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MoviesAdapter(Context context, List<Movie> movies) {
        this.mContext = context;
        mMovies = movies;
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

        Movie movie = mMovies.get(position);
        if (movie.getMoviePosterPath() != null) {
            Picasso.with(mContext)
                    .load(moviePosterBasePath + movie.getMoviePosterPath())
                    .into(holder.mMovieIV);

        }

        holder.mMovieIV.setTag(holder);

    }
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
