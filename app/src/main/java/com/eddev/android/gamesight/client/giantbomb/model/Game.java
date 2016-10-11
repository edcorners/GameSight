
package com.eddev.android.gamesight.client.giantbomb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Game {

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
    private Image image;
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
    private List<Platform> platforms = new ArrayList<Platform>();
    @SerializedName("videos")
    @Expose
    private List<Video> videos = new ArrayList<Video>();
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();
    @SerializedName("publishers")
    @Expose
    private List<Publisher> publishers = new ArrayList<Publisher>();

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
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    public void setImage(Image image) {
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
    public List<Platform> getPlatforms() {
        return platforms;
    }

    /**
     *
     * @param platforms
     *     The platforms
     */
    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    /**
     *
     * @return
     *     The videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     *     The videos
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    /**
     *
     * @return
     *     The genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     *     The genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /**
     *
     * @return
     *     The publishers
     */
    public List<Publisher> getPublishers() {
        return publishers;
    }

    /**
     *
     * @param publishers
     *     The publishers
     */
    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }
}
