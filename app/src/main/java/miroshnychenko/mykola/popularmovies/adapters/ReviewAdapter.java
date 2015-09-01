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

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Review;

/**
 * Created by nsmirosh on 8/31/2015.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {

    Context mContext;

    public ReviewAdapter(Context context) {
        super(context, R.layout.adapter_review_row);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review = getItem(position);
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.adapter_review_row, parent, false);
            holder = new ViewHolder();
            holder.mAuthorTV = (TextView) convertView.findViewById(R.id.adapter_review_row_author_tv);
            holder.mContentTV = (TextView) convertView.findViewById(R.id.adapter_review_row_content_tv);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mAuthorTV.setText(review.getAuthor());
        holder.mContentTV.setText(review.getContent());
        return convertView;
    }
    public static class ViewHolder {
        public TextView mAuthorTV;
        public TextView mContentTV;

    }
}
