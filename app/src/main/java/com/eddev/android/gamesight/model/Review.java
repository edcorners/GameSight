package com.eddev.android.gamesight.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import static android.R.attr.id;

/**
 * Created by Edison on 9/24/2016.
 */
public class Review implements Parcelable {
    private long id;
    private String description;
    private Date date;
    private double score;
    private String reviewer;

    protected Review(Parcel in) {
        id = in.readLong();
        description = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        score = in.readDouble();
        reviewer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeDouble(score);
        dest.writeString(reviewer);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}