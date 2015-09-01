package miroshnychenko.mykola.popularmovies.models;

import java.util.List;

/**
 * Created by nsmirosh on 9/1/2015.
 */
public class TrailersResponse {

    private List<Trailer> results;

    public List<Trailer> getReviews() {
        return results;
    }

    public void setReviews(List<Trailer> reviews) {
        this.results = reviews;
    }
}
