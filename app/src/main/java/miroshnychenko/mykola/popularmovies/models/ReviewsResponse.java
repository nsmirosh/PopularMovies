package miroshnychenko.mykola.popularmovies.models;

import java.util.List;

/**
 * Created by nsmirosh on 8/30/2015.
 */
public class ReviewsResponse {

    private List<Review> results;

    public List<Review> getReviews() {
        return results;
    }

    public void setReviews(List<Review> reviews) {
        this.results = reviews;
    }
}
