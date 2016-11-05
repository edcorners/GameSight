package com.eddev.android.gamesight.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edison on 9/24/2016.
 */
public class Video implements Parcelable {
    private long id;
    private String name;
    private String url;

    public Video(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    protected Video(Parcel in) {
        id = in.readLong();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}