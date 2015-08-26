package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MovieAdapter extends CursorAdapter {



    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_PATH = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_USER_RATING = 5;
    static final int COL_RELEASE_DATE = 6;


    public static class ViewHolder {
        public final ImageView mMovieIV;

        public ViewHolder(View view) {
            mMovieIV = (ImageView) view.findViewById(R.id.adapter_movies_iv);
        }
    }

    public MovieAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_movies_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();


        if (cursor.getString(COL_POSTER_PATH) != null) {
            Picasso.with(mContext)
                    .load(cursor.getString(COL_POSTER_PATH))
                    .into(viewHolder.mMovieIV);
        }

    }


//    // Provide a suitable constructor (depends on the kind of dataset)
//    public MoviesAdapter(Context context, MoviePosterClicks listener) {
//        this.mContext = context;
//        mMovies = movies;
//        mListener = listener;
//    }
}
