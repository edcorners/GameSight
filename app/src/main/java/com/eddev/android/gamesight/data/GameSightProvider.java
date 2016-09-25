package com.eddev.android.gamesight.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Edison on 9/25/2016.
 */
@ContentProvider(authority = GameSightProvider.AUTHORITY, database = GameSightDatabase.class, packageName = "com.eddev.android.gamesight.provider")
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

    @TableEndpoint(table = GameSightDatabase.GAMES) public static class Games {
        @ContentUri(
                path = Path.GAMES,
                type = "vnd.android.cursor.dir/game",
                defaultSort = GameColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.GAMES);
    }
    @TableEndpoint(table = GameSightDatabase.REVIEWS) public static class Reviews {}
    @TableEndpoint(table = GameSightDatabase.VIDEOS) public static class Videos {}
    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_ATTRIBUTES) public static class ClassificationAttributes {}
    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_BY_GAME) public static class ClassificationByGame {}

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
}
