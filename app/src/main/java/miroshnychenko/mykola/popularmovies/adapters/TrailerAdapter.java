package miroshnychenko.mykola.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.models.Trailer;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {

    Context mContext;

    public TrailerAdapter(Context context) {
        super(context, R.layout.adapter_review_row);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Trailer trailer = getItem(position);
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.adapter_trailer_row, parent, false);
            holder = new ViewHolder();
            holder.mNameTV = (TextView) convertView.findViewById(R.id.adapter_trailer_row_name_tv);
            holder.mPlayIV = (ImageView) convertView.findViewById(R.id.adapter_trailer_row_play_btn);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mNameTV.setText(trailer.getName());
        return convertView;
    }
    public static class ViewHolder {
        public TextView mNameTV;
        public ImageView mPlayIV;
    }
}