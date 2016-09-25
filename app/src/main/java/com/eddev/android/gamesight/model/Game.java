
package com.eddev.android.gamesight.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Game {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({OWNED, TRACKING})
    public @interface CollectionName {}
    private static final String OWNED = "OWNED";
    private static final String TRACKING = "TRACKING";

    private long id;
    private String description;
    private Date expectedReleaseDate;
    private String imageUrl;
    private String thumbnailUrl;
    private String name;
    private List<ClassificationAttribute> classificationAttributes = new ArrayList<ClassificationAttribute>();
    private int numberOfUserReviews;
    private Date originalReleaseDate;
    private List<Review> reviews = new ArrayList<Review>();
    private List<Video> videos = new ArrayList<Video>();
    private double completion;
    private @CollectionName String collection;
    
}
