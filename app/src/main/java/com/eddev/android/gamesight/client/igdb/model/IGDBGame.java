
package com.eddev.android.gamesight.client.igdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IGDBGame {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("publishers")
    @Expose
    private List<Integer> publishers = null;
    @SerializedName("first_release_date")
    @Expose
    private Integer firstReleaseDate;
    @SerializedName("release_dates")
    @Expose
    private List<IGDBReleaseDate> releaseDates = null;
    @SerializedName("videos")
    @Expose
    private List<IGDBVideo> videos = null;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("genres")
    @Expose
    private List<Integer> genres = null;
    @SerializedName("cover")
    @Expose
    private IDGBCover cover;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Integer> publishers) {
        this.publishers = publishers;
    }

    public Integer getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(Integer firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public List<IGDBReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<IGDBReleaseDate> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<IGDBVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<IGDBVideo> videos) {
        this.videos = videos;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public IDGBCover getCover() {
        return cover;
    }

    public void setCover(IDGBCover cover) {
        this.cover = cover;
    }

}
