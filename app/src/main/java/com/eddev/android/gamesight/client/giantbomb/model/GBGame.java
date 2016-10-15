
package com.eddev.android.gamesight.client.giantbomb.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GBGame {

    @SerializedName("deck")
    @Expose
    private String deck;
    @SerializedName("expected_release_day")
    @Expose
    private Object expectedReleaseDay;
    @SerializedName("expected_release_month")
    @Expose
    private Object expectedReleaseMonth;
    @SerializedName("expected_release_year")
    @Expose
    private Object expectedReleaseYear;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private GBImage gBImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number_of_user_reviews")
    @Expose
    private int numberOfUserReviews;
    @SerializedName("original_release_date")
    @Expose
    private String originalReleaseDate;
    @SerializedName("gBPlatforms")
    @Expose
    private List<GBPlatform> gBPlatforms = new ArrayList<GBPlatform>();
    @SerializedName("gBVideos")
    @Expose
    private List<GBVideo> gBVideos = new ArrayList<GBVideo>();
    @SerializedName("genres")
    @Expose
    private List<GBGenre> genres = new ArrayList<GBGenre>();
    @SerializedName("GBPublishers")
    @Expose
    private List<GBPublisher> GBPublishers = new ArrayList<GBPublisher>();

    /**
     *
     * @return
     *     The deck
     */
    public String getDeck() {
        return deck;
    }

    /**
     *
     * @param deck
     *     The deck
     */
    public void setDeck(String deck) {
        this.deck = deck;
    }

    /**
     *
     * @return
     *     The expectedReleaseDay
     */
    public Object getExpectedReleaseDay() {
        return expectedReleaseDay;
    }

    /**
     *
     * @param expectedReleaseDay
     *     The expected_release_day
     */
    public void setExpectedReleaseDay(Object expectedReleaseDay) {
        this.expectedReleaseDay = expectedReleaseDay;
    }

    /**
     *
     * @return
     *     The expectedReleaseMonth
     */
    public Object getExpectedReleaseMonth() {
        return expectedReleaseMonth;
    }

    /**
     *
     * @param expectedReleaseMonth
     *     The expected_release_month
     */
    public void setExpectedReleaseMonth(Object expectedReleaseMonth) {
        this.expectedReleaseMonth = expectedReleaseMonth;
    }

    /**
     *
     * @return
     *     The expectedReleaseYear
     */
    public Object getExpectedReleaseYear() {
        return expectedReleaseYear;
    }

    /**
     *
     * @param expectedReleaseYear
     *     The expected_release_year
     */
    public void setExpectedReleaseYear(Object expectedReleaseYear) {
        this.expectedReleaseYear = expectedReleaseYear;
    }

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The gBImage
     */
    public GBImage getGBImage() {
        return gBImage;
    }

    /**
     *
     * @param GBImage
     *     The gBImage
     */
    public void setGBImage(GBImage GBImage) {
        this.gBImage = GBImage;
    }

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The numberOfUserReviews
     */
    public int getNumberOfUserReviews() {
        return numberOfUserReviews;
    }

    /**
     *
     * @param numberOfUserReviews
     *     The number_of_user_reviews
     */
    public void setNumberOfUserReviews(int numberOfUserReviews) {
        this.numberOfUserReviews = numberOfUserReviews;
    }

    /**
     *
     * @return
     *     The originalReleaseDate
     */
    public String getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    /**
     *
     * @param originalReleaseDate
     *     The original_release_date
     */
    public void setOriginalReleaseDate(String originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    /**
     *
     * @return
     *     The gBPlatforms
     */
    public List<GBPlatform> getGBPlatforms() {
        return gBPlatforms;
    }

    /**
     *
     * @param GBPlatforms
     *     The gBPlatforms
     */
    public void setGBPlatforms(List<GBPlatform> GBPlatforms) {
        this.gBPlatforms = GBPlatforms;
    }

    /**
     *
     * @return
     *     The gBVideos
     */
    public List<GBVideo> getGBVideos() {
        return gBVideos;
    }

    /**
     *
     * @param GBVideos
     *     The gBVideos
     */
    public void setGBVideos(List<GBVideo> GBVideos) {
        this.gBVideos = GBVideos;
    }

    /**
     *
     * @return
     *     The genres
     */
    public List<GBGenre> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     *     The genres
     */
    public void setGenres(List<GBGenre> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return
     *     The GBPublishers
     */
    public List<GBPublisher> getGBPublishers() {
        return GBPublishers;
    }

    /**
     *
     * @param GBPublishers
     *     The GBPublishers
     */
    public void setGBPublishers(List<GBPublisher> GBPublishers) {
        this.GBPublishers = GBPublishers;
    }

    public boolean hasThumb() {
        return this.gBImage != null && !TextUtils.isEmpty(this.gBImage.getThumbUrl());
    }

    public String getThumb() {
        return hasThumb() ? this.gBImage.getThumbUrl() : null;
    }
}
