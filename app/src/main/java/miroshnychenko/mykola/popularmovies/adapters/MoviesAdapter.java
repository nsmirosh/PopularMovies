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

import miroshnychenko.mykola.popularmovies.R;

/**
 * Created by nsmirosh on 7/15/2015.
 */
public class MoviesAdapter extends ArrayAdapter<Uri> {

    Context context;

    public MoviesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movies_list, parent, false);
        }

        ImageView movieImage =  (ImageView) convertView.findViewById(R.id.movies_list_image_iv);
        Picasso.with(context)
                .load(getItem(position))
                .into(movieImage);

        return convertView;
    }
}
