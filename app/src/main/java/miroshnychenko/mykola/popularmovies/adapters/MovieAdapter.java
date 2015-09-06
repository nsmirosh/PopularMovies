package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    Context mContext;

    public MovieAdapter(Context context) {
        super(context, R.layout.adapter_movies_row);
        mContext = context;
    }

    public static class ViewHolder {
        public ImageView mMovieIV;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        Movie movie = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            convertView  = inflater.inflate(R.layout.adapter_movies_row, parent, false);
            holder.mMovieIV = (ImageView) convertView.findViewById(R.id.adapter_movies_iv);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (movie.getMoviePosterPath() != null) {
            Picasso.with(mContext)
                    .load(movie.getMoviePosterPath())
                    .into(holder.mMovieIV);
        }

        return convertView;
    }
}
