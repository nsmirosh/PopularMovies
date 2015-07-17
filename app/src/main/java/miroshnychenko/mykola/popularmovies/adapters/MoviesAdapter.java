package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MoviesAdapter extends BaseAdapter {

    public static final String moviePosterBasePath = " http://image.tmdb.org/t/p/w185";

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movies_list, parent, false);
        }

        ImageView movieImage =  (ImageView) convertView.findViewById(R.id.movies_list_image_iv);
        Picasso.with(context)
                .load(moviePosterBasePath + ((Movie) getItem(position)).getMoviePosterPath())
                .into(movieImage);

        return convertView;
    }
}
