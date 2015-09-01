package miroshnychenko.mykola.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nsmirosh on 8/30/2015.
 */
@org.parceler.Parcel
public class Review {

    private String id;
    private String author;
    private String content;


    //required by the parceler framework
    public Review() {

    }

    public Review(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
