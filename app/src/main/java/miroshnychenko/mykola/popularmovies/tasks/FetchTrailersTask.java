package miroshnychenko.mykola.popularmovies.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.http.TrailerApi;
import miroshnychenko.mykola.popularmovies.models.Trailer;
import miroshnychenko.mykola.popularmovies.models.TrailersResponse;
import retrofit.RestAdapter;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public class FetchTrailersTask extends AsyncTask<Long, Void, List<Trailer>> {

    public static final String TAG = FetchTrailersTask.class.getSimpleName();

    Context mContext;

    //TODO REMOVE BEFORE UPLOADING TO GITHUB!
    public static final String API_KEY = "";

    public FetchTrailersTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<Trailer> doInBackground(Long... params) {

        long movieId = params[0];

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .build();

        TrailerApi trailerApi = restAdapter.create(TrailerApi.class);

        TrailersResponse trailersResponse = trailerApi.getTrailers(movieId, API_KEY);

        List<Trailer> trailers = new ArrayList<>();

        Vector<ContentValues> vTrailerValues = new Vector<ContentValues>();

        if (trailersResponse != null && trailersResponse.getReviews() != null) {
            trailers = trailersResponse.getReviews();
            Log.d(TAG, trailers.size() + " trailers fetched");

            for (Trailer trailer: trailers) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_KEY, movieId);
                contentValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, trailer.getId());
                contentValues.put(MovieContract.TrailerEntry.COLUMN_YOUTUBE_KEY, trailer.getKey());
                contentValues.put(MovieContract.TrailerEntry.COLUMN_NAME, trailer.getName());
                vTrailerValues.add(contentValues);
            }
        }

        if ( vTrailerValues.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[vTrailerValues.size()];
            vTrailerValues.toArray(cvArray);
            int inserted = mContext.getContentResolver().bulkInsert(MovieContract.TrailerEntry.buildTrailersWithMovieIdUri(movieId), cvArray);
            Log.d(TAG, inserted + " trailers inserted");
        }

        return trailers;
    }
}
