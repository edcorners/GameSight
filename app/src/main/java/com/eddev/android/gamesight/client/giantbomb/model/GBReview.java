package com.eddev.android.gamesight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ed on 11/5/16.
 */

public class GBReview {

    @SerializedName("deck")
    @Expose
    private String deck;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("reviewer")
    @Expose
    private String reviewer;
    @SerializedName("score")
    @Expose
    private Integer score;

    /**
     *
     * @return
     * The deck
     */

    public String getDeck() {
        return deck;
    }

    /**
     *
     * @param deck
     * The deck
     */

    public void setDeck(String deck) {
        this.deck = deck;
    }

    /**
     *
     * @return
     * The publishDate
     */

    public String getPublishDate() {
        return publishDate;
    }

    /**
     *
     * @param publishDate
     * The publish_date
     */

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     *
     * @return
     * The reviewer
     */

    public String getReviewer() {
        return reviewer;
    }

    /**
     *
     * @param reviewer
     * The reviewer
     */

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    /**
     *
     * @return
     * The score
     */

    public Integer getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */

    public void setScore(Integer score) {
        this.score = score;
    }

}
