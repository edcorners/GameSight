
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
    private String expectedReleaseDay;
    @SerializedName("expected_release_month")
    @Expose
    private String expectedReleaseMonth;
    @SerializedName("expected_release_year")
    @Expose
    private String expectedReleaseYear;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private GBImage image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number_of_user_reviews")
    @Expose
    private int numberOfUserReviews;
    @SerializedName("original_release_date")
    @Expose
    private String originalReleaseDate;
    @SerializedName("platforms")
    @Expose
    private List<GBPlatform> platforms = new ArrayList<GBPlatform>();
    @SerializedName("videos")
    @Expose
    private List<GBVideo> videos = new ArrayList<GBVideo>();
    @SerializedName("genres")
    @Expose
    private List<GBGenre> genres = new ArrayList<GBGenre>();
    @SerializedName("publishers")
    @Expose
    private List<GBPublisher> publishers = new ArrayList<GBPublisher>();

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
    public void setExpectedReleaseDay(String expectedReleaseDay) {
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
    public void setExpectedReleaseMonth(String expectedReleaseMonth) {
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
    public void setExpectedReleaseYear(String expectedReleaseYear) {
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
     *     The image
     */
    public GBImage getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    public void setImage(GBImage image) {
        this.image = image;
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
     *     The platforms
     */
    public List<GBPlatform> getPlatforms() {
        return platforms;
    }

    /**
     *
     * @param platforms
     *     The platforms
     */
    public void setPlatforms(List<GBPlatform> platforms) {
        this.platforms = platforms;
    }

    /**
     *
     * @return
     *     The videos
     */
    public List<GBVideo> getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     *     The videos
     */
    public void setVideos(List<GBVideo> videos) {
        this.videos = videos;
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
     *     The publishers
     */
    public List<GBPublisher> getPublishers() {
        return publishers;
    }

    /**
     *
     * @param publishers
     *     The publishers
     */
    public void setPublishers(List<GBPublisher> publishers) {
        this.publishers = publishers;
    }

    public boolean hasThumb() {
        return this.image != null && !TextUtils.isEmpty(this.image.getThumbUrl());
    }

    public String getThumb() {
        return hasThumb() ? this.image.getThumbUrl() : null;
    }

    public boolean hasCover() {
        return this.image != null && !TextUtils.isEmpty(this.image.getMediumUrl());
    }

    public String getCover() {
        return  hasCover() ? this.image.getMediumUrl() : null;
    }

    public String getExpectedReleaseDate(){
        StringBuilder expectedReleaseDate = new StringBuilder();
        if (TextUtils.isEmpty(expectedReleaseYear)){
            expectedReleaseDate.append("");
        }else{
            expectedReleaseDate.append(expectedReleaseYear+"-");
            expectedReleaseDate.append(!TextUtils.isEmpty(expectedReleaseMonth) ? expectedReleaseMonth+"-" : "01");
            expectedReleaseDate.append(!TextUtils.isEmpty(expectedReleaseDay) ? expectedReleaseDay : "01");
        }
        return expectedReleaseDate.toString();
    }
}
