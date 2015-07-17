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
    public static final String API_KEY = "d5d716f0c3ba595706ba90ae3138a16a";
    @Override
    protected List<Movie> doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
            // If the code didn't successfully get the movies data, there's no point in attemping
            // to parse it.
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

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    private List<Movie> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        final String OWM_RESULTS = "results";
        final String OWM_ORIGINAL_TITLE = "original_title";
        final String OWM_POSTER_PATH = "poster_path";
        final String OWM_OVERVIEW = "overview";
        final String OWM_USER_RATING = "vote_average";
        final String OWM_RELEASE_DATE= "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(OWM_RESULTS);
        ArrayList<Movie> moviesArrayList = new ArrayList<Movie>();

        for(int i = 0; i < moviesArray.length(); i++) {

            String originalTitle;
            String moviePosterPath;
            String overview;
            double userRating;
            String releaseDate;

            // Get the JSON object representing the day
            JSONObject movieJSON = moviesArray.getJSONObject(i);
            originalTitle = movieJSON.getString(OWM_ORIGINAL_TITLE);
            moviePosterPath = movieJSON.getString(OWM_POSTER_PATH);
            overview = movieJSON.getString(OWM_OVERVIEW);
            userRating = movieJSON.getDouble(OWM_USER_RATING);
            releaseDate = movieJSON.getString(OWM_RELEASE_DATE);
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
