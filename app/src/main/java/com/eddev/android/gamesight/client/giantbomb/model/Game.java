
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
    private int expectedReleaseDay;
    @SerializedName("expected_release_month")
    @Expose
    private int expectedReleaseMonth;
    @SerializedName("expected_release_year")
    @Expose
    private int expectedReleaseYear;
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
    private Object originalReleaseDate;
    @SerializedName("platforms")
    @Expose
    private List<Platform> platforms = new ArrayList<Platform>();

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
    public int getExpectedReleaseDay() {
        return expectedReleaseDay;
    }

    /**
     * 
     * @param expectedReleaseDay
     *     The expected_release_day
     */
    public void setExpectedReleaseDay(int expectedReleaseDay) {
        this.expectedReleaseDay = expectedReleaseDay;
    }

    /**
     * 
     * @return
     *     The expectedReleaseMonth
     */
    public int getExpectedReleaseMonth() {
        return expectedReleaseMonth;
    }

    /**
     * 
     * @param expectedReleaseMonth
     *     The expected_release_month
     */
    public void setExpectedReleaseMonth(int expectedReleaseMonth) {
        this.expectedReleaseMonth = expectedReleaseMonth;
    }

    /**
     * 
     * @return
     *     The expectedReleaseYear
     */
    public int getExpectedReleaseYear() {
        return expectedReleaseYear;
    }

    /**
     * 
     * @param expectedReleaseYear
     *     The expected_release_year
     */
    public void setExpectedReleaseYear(int expectedReleaseYear) {
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
    public Object getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    /**
     * 
     * @param originalReleaseDate
     *     The original_release_date
     */
    public void setOriginalReleaseDate(Object originalReleaseDate) {
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

    @Override
    public String toString() {
        return "Game{" +
                "deck='" + deck + '\'' +
                ", expectedReleaseDay=" + expectedReleaseDay +
                ", expectedReleaseMonth=" + expectedReleaseMonth +
                ", expectedReleaseYear=" + expectedReleaseYear +
                ", id=" + id +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", numberOfUserReviews=" + numberOfUserReviews +
                ", originalReleaseDate=" + originalReleaseDate +
                ", platforms=" + platforms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (expectedReleaseDay != game.expectedReleaseDay) return false;
        if (expectedReleaseMonth != game.expectedReleaseMonth) return false;
        if (expectedReleaseYear != game.expectedReleaseYear) return false;
        if (id != game.id) return false;
        if (numberOfUserReviews != game.numberOfUserReviews) return false;
        if (deck != null ? !deck.equals(game.deck) : game.deck != null) return false;
        if (image != null ? !image.equals(game.image) : game.image != null) return false;
        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        if (originalReleaseDate != null ? !originalReleaseDate.equals(game.originalReleaseDate) : game.originalReleaseDate != null)
            return false;
        return platforms != null ? platforms.equals(game.platforms) : game.platforms == null;

    }

    @Override
    public int hashCode() {
        int result = deck != null ? deck.hashCode() : 0;
        result = 31 * result + expectedReleaseDay;
        result = 31 * result + expectedReleaseMonth;
        result = 31 * result + expectedReleaseYear;
        result = 31 * result + id;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numberOfUserReviews;
        result = 31 * result + (originalReleaseDate != null ? originalReleaseDate.hashCode() : 0);
        result = 31 * result + (platforms != null ? platforms.hashCode() : 0);
        return result;
    }
}
