package miroshnychenko.mykola.popularmovies.http;

import miroshnychenko.mykola.popularmovies.models.ReviewsResponse;
import miroshnychenko.mykola.popularmovies.models.TrailersResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public interface TrailerApi {

    @GET("/movie/{id}/videos")
    TrailersResponse getTrailers(@Path("id") long movieId, @Query("api_key") String apiKey);
}
