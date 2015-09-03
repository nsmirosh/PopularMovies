package miroshnychenko.mykola.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by nsmirosh on 9/1/2015.
 */

public class Trailer implements Parcelable {

    private String id;
    private String key;
    private String name;

    public Trailer(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public Trailer(Parcel parcel) {
        id = parcel.readString();
        key = parcel.readString();
        name = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(android.os.Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
