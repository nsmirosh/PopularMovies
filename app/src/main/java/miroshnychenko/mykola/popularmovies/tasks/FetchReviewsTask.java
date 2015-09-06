package miroshnychenko.mykola.popularmovies.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import miroshnychenko.mykola.popularmovies.http.ReviewApi;
import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Review;
import miroshnychenko.mykola.popularmovies.models.ReviewsResponse;
import retrofit.RestAdapter;

/**
 * Created by nsmirosh on 8/30/2015.
 */
public class FetchReviewsTask extends AsyncTask<Long, Void, List<Review>> {

    public static final String TAG = FetchReviewsTask.class.getSimpleName();

    Context mContext;





    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);
    }

    //TODO REMOVE BEFORE UPLOADING TO GITHUB!
    public static final String API_KEY = "d5d716f0c3ba595706ba90ae3138a16a";

    public FetchReviewsTask(Context context) {
        mContext = context;


    }

    @Override
    protected List<Review> doInBackground(Long... params) {

        long movieId = params[0];

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        ReviewApi reviewApi = restAdapter.create(ReviewApi.class);

        ReviewsResponse reviewsResponse = reviewApi.getReviews(movieId, API_KEY);

        List<Review> reviews = new ArrayList<>();

        Vector<ContentValues> vReviewValues = new Vector<ContentValues>();

        if (reviewsResponse != null && reviewsResponse.getReviews() != null) {
            reviews = reviewsResponse.getReviews();
            Log.d(TAG, reviews.size() + " reviews fetched");

            for (Review review: reviews) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_KEY, movieId);
                contentValues.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID, review.getId());
                contentValues.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
                contentValues.put(MovieContract.ReviewEntry.COLUMN_CONTENT, review.getContent());
                vReviewValues.add(contentValues);
            }
        }

        if ( vReviewValues.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[vReviewValues.size()];
            vReviewValues.toArray(cvArray);
            int inserted = mContext.getContentResolver().bulkInsert(MovieContract.ReviewEntry.buildReviewsWithMovieIdUri(movieId), cvArray);
            Log.d(TAG, inserted + " reviews inserted");
        }

        return reviews;
    }





}
