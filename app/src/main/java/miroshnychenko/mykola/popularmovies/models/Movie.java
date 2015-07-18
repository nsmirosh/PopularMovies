package miroshnychenko.mykola.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nsmirosh on 7/17/2015.
 */
public class Movie implements Parcelable {

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

    public Movie(Parcel parcel) {
        originalTitle = parcel.readString();
        moviePosterPath = parcel.readString();
        overview = parcel.readString();
        userRating = parcel.readDouble();
        releaseDate = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(originalTitle);
        dest.writeString(moviePosterPath);
        dest.writeString(overview);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
