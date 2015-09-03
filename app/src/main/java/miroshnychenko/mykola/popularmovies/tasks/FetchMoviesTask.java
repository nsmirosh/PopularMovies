package miroshnychenko.mykola.popularmovies.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import miroshnychenko.mykola.popularmovies.data.MovieContract;
import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/16/2015.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    public static final String TAG = FetchMoviesTask.class.getSimpleName();

    private final Context mContext;

    public FetchMoviesTask(Context context) {
        mContext = context;
    }


    //TODO REMOVE BEFORE UPLOADING TO GITHUB!
    public static final String API_KEY = "d5d716f0c3ba595706ba90ae3138a16a";
    public static final String moviePosterBasePath = "https://image.tmdb.org/t/p/w185";
    @Override
    protected List<Movie> doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null;

        try {
            final String MOVIE_BASE_URL =
                    "https://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error " + e, e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private List<Movie> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String RESULTS = "results";
        final String MOVIE_ID = "id";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE= "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
        ArrayList<Movie> moviesArrayList = new ArrayList<>();

        Vector<ContentValues> vMovieValues = new Vector<ContentValues>(moviesArray.length());

        for(int i = 0; i < moviesArray.length(); i++) {

            String originalTitle;
            String moviePosterPath;
            String overview;
            double userRating;
            String releaseDate;

            JSONObject movieJSON = moviesArray.getJSONObject(i);
            long id = movieJSON.getLong(MOVIE_ID);
            originalTitle = movieJSON.getString(ORIGINAL_TITLE);
            moviePosterPath = moviePosterBasePath + movieJSON.getString(POSTER_PATH);
            overview = movieJSON.getString(OVERVIEW);
            userRating = movieJSON.getDouble(USER_RATING);
            releaseDate = movieJSON.getString(RELEASE_DATE);

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, id);
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, originalTitle);
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, moviePosterPath);
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, overview);
            contentValues.put(MovieContract.MovieEntry.COLUMN_USER_RATING, userRating);
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);
            vMovieValues.add(contentValues);
        }

        int inserted = 0;
        // add to database
        if ( vMovieValues.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[vMovieValues.size()];
            vMovieValues.toArray(cvArray);
            inserted = mContext.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, cvArray);
        }

        Log.d(TAG, "FetchMoviesTask Complete. " + inserted + " Inserted");
        return moviesArrayList;

    }

}
