package miroshnychenko.mykola.popularmovies.tasks;

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

import miroshnychenko.mykola.popularmovies.models.Movie;

/**
 * Created by nsmirosh on 7/16/2015.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    public static final String TAG = FetchMoviesTask.class.getSimpleName();

    public OnMoviesDownloadedListener mCallback;


    //TODO REMOVE BEFORE UPLOADING TO GITHUB!
    public static final String API_KEY = "";
    public static final String moviePosterBasePath = "http://image.tmdb.org/t/p/w500";
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
                    "http://api.themoviedb.org/3/discover/movie?";
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
            Log.e(TAG, "Error ", e);
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
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE= "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
        ArrayList<Movie> moviesArrayList = new ArrayList<Movie>();

        for(int i = 0; i < moviesArray.length(); i++) {

            String originalTitle;
            String moviePosterPath;
            String overview;
            double userRating;
            String releaseDate;

            JSONObject movieJSON = moviesArray.getJSONObject(i);
            originalTitle = movieJSON.getString(ORIGINAL_TITLE);
            moviePosterPath = moviePosterBasePath + movieJSON.getString(POSTER_PATH);
            overview = movieJSON.getString(OVERVIEW);
            userRating = movieJSON.getDouble(USER_RATING);
            releaseDate = movieJSON.getString(RELEASE_DATE);
            Movie movie = new Movie(originalTitle, moviePosterPath, overview, userRating, releaseDate);
            moviesArrayList.add(movie);
        }
        return moviesArrayList;

    }

    @Override
    protected void onPostExecute(List<Movie> data) {
        mCallback.onMoviesDownloaded(data);
    }


    public static interface OnMoviesDownloadedListener {
        void onMoviesDownloaded(List<Movie> movies);
    }

}
