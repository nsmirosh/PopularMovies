package miroshnychenko.mykola.popularmovies.http;

import java.util.List;

import miroshnychenko.mykola.popularmovies.models.Review;
import miroshnychenko.mykola.popularmovies.models.ReviewsResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by nsmirosh on 8/30/2015.
 */
public interface ReviewApi {

    @GET("/movie/{id}/reviews")
    ReviewsResponse getReviews(@Path("id") long movieId, @Query("api_key") String apiKey);
}
