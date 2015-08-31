package miroshnychenko.mykola.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import miroshnychenko.mykola.popularmovies.R;
import miroshnychenko.mykola.popularmovies.data.MovieContract;

/**
 * Created by nsmirosh on 8/31/2015.
 */
public class ReviewAdapter extends CursorAdapter {

    static final int COL_ID = 0;
    static final int COLUMN_REVIEW_ID = 1;
    static final int COLUMN_CONTENT = 2;
    static final int COLUMN_AUTHOR = 3;
    static final int COLUMN_MOVIE_KEY = 4;


    public static class ViewHolder {
        public final TextView mAuthorTV;
        public final TextView mContentTV;

        public ViewHolder(View view) {
            mAuthorTV = (TextView) view.findViewById(R.id.adapter_review_row_author_tv);
            mContentTV = (TextView) view.findViewById(R.id.adapter_review_row_content_tv);
        }
    }

    public ReviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_review_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (cursor.getString(COLUMN_AUTHOR) != null) {
            viewHolder.mAuthorTV.setText(cursor.getString(COLUMN_AUTHOR));
        }

        if (cursor.getString(COLUMN_CONTENT) != null) {
            viewHolder.mContentTV.setText(cursor.getString(COLUMN_CONTENT));
        }
    }
}
