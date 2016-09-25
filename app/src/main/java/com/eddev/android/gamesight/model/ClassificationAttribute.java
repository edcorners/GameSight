package com.eddev.android.gamesight.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Edison on 9/24/2016.
 */
public class ClassificationAttribute {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GENRE, PLATFORM, PUBLISHER})
    public @interface GameAttribType {}
    private static final String GENRE = "GENRE";
    private static final String PLATFORM = "PLATFORM";
    private static final String PUBLISHER = "PUBLISHER";

    private long id;
    private @GameAttribType
    String type;
    private String value;
}
