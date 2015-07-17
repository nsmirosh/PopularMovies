package miroshnychenko.mykola.popularmovies.models;

/**
 * Created by nsmirosh on 7/17/2015.
 */
public class Movie {


    private String originalTitle;
    private String moviePosterPath;
    private String overview;
    private double userRating;
    private String releaseDate;

    public Movie(String originalTitle, String moviePosterPath, String overview, double userRating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.moviePosterPath = moviePosterPath;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
