
package com.eddev.android.gamersight.client.igdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IGDBReleaseDate {

    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("platform")
    @Expose
    private Integer platform;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("region")
    @Expose
    private Integer region;
    @SerializedName("human")
    @Expose
    private String human;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("m")
    @Expose
    private Integer m;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public String getHuman() {
        return human;
    }

    public void setHuman(String human) {
        this.human = human;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

}
