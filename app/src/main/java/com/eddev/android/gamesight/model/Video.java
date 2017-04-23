package com.eddev.android.gamesight.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.eddev.android.gamesight.data.GamerSightDatabase;
import com.eddev.android.gamesight.data.VideoColumns;

/**
 * Created by Edison on 9/24/2016.
 */
public class Video implements Parcelable {
    public static final String[] VIDEO_PROJECTION = {GamerSightDatabase.VIDEOS+"."+ VideoColumns.VIDEO_ID,
            GamerSightDatabase.VIDEOS+"."+VideoColumns.NAME,
            GamerSightDatabase.VIDEOS+"."+VideoColumns.URL};

    private static final int INDEX_VIDEO_ID = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_URL = 2;

    private int id;
    private String name;
    private String url;

    public Video(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Video(Cursor cursor) {
        this.id = cursor.getInt(INDEX_VIDEO_ID);
        this.name = cursor.getString(INDEX_NAME);
        this.url = cursor.getString(INDEX_URL);
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