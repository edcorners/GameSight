package com.eddev.android.gamesight.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Edison on 9/25/2016.
 */
@ContentProvider(authority = GameSightProvider.AUTHORITY, database = GameSightProvider.class)
public class GameSightProvider {
    public static final String AUTHORITY = "com.eddev.android.gamesight";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String GAMES = "games";
        String REVIEWS = "reviews";
        String VIDEOS = "videos";
        String CLASSIFICATION_ATTRIBUTES = "classification_attributes";
        String CLASSIFICATION_BY_GAME = "classification_by_game";
    }

    @TableEndpoint(table = GameSightDatabase.GAMES) public static class Games {}
    @TableEndpoint(table = GameSightDatabase.REVIEWS) public static class Reviews {}
    @TableEndpoint(table = GameSightDatabase.VIDEOS) public static class Videos {}
    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_ATTRIBUTES) public static class ClassificationAttributes {}
    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_BY_GAME) public static class ClassificationByGame {}
}
