
package com.eddev.android.gamesight.client.igdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IGDBVideo {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("video_id")
    @Expose
    private String videoId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
