package com.eddev.android.gamesight.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.eddev.android.gamesight.data.ReviewColumns;

import java.util.Date;

/**
 * Created by Edison on 9/24/2016.
 */
public class Review implements Parcelable {
    private int id;
    private String description;
    private Date date;
    private double score;
    private String reviewer;


    public Review(int id, String description, Date date, double score, String reviewer) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.score = score;
        this.reviewer = reviewer;
    }

    protected Review(Parcel in) {
        id = in.readInt();
        description = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        score = in.readDouble();
        reviewer = in.readString();
    }

    public ContentValues toContentValues(int gameId) {
        ContentValues cv = new ContentValues();
        cv.put(ReviewColumns.REVIEW_ID, id);
        cv.put(ReviewColumns.GAME_ID, gameId);
        cv.put(ReviewColumns.DESCRIPTION, description);
        cv.put(ReviewColumns.DATE, date.getTime());
        cv.put(ReviewColumns.SCORE, score);
        cv.put(ReviewColumns.REVIEWER, reviewer);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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