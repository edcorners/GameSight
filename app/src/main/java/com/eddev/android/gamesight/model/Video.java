package com.eddev.android.gamesight.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.eddev.android.gamesight.data.VideoColumns;

/**
 * Created by Edison on 9/24/2016.
 */
public class Video implements Parcelable {
    private int id;
    private String name;
    private String url;

    public Video(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public ContentValues toContentValues(int gameId) {
        ContentValues cv = new ContentValues();
        cv.put(VideoColumns.VIDEO_ID, id);
        cv.put(VideoColumns.NAME, name);
        cv.put(VideoColumns.URL, url);
        cv.put(VideoColumns.GAME_ID, gameId);
        return cv;
    }

    protected Video(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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