package com.eddev.android.gamesight.data;

import android.net.Uri;

import com.eddev.android.gamesight.BuildConfig;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Edison on 9/25/2016.
 */
@ContentProvider(authority = GameSightProvider.AUTHORITY, database = GameSightDatabase.class, packageName = "com.eddev.android.gamesight.provider")
public class GameSightProvider {
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
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

        @InexactContentUri(
                name = "GAME_ID",
                path = Path.GAMES + "/#",
                type = "vnd.android.cursor.item/game",
                whereColumn = GameColumns.GAME_ID,
                pathSegment = 1
        )
        public static Uri withGameId(long id){
            return buildUri(Path.GAMES, String.valueOf(id));
        }

        @InexactContentUri(
                name = "GAME_BY_CLASSIFICATION_TYPE",
                path = Path.GAMES +"/"+ Path.CLASSIFICATION_BY_GAME + "/*",
                type = "vnd.android.cursor.dir/game",
                whereColumn = GameSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ClassificationAttributeColumns.TYPE,
                pathSegment = 2,
                join = "INNER JOIN "+GameSightDatabase.CLASSIFICATION_BY_GAME +" ON "+
                        GameSightDatabase.GAMES+"."+GameColumns.GAME_ID + " = " +
                        GameSightDatabase.CLASSIFICATION_BY_GAME+"."+ClassificationByGameColumns.GAME_ID+
                        " INNER JOIN "+GameSightDatabase.CLASSIFICATION_ATTRIBUTES+" ON "+
                        GameSightDatabase.CLASSIFICATION_BY_GAME+"."+ClassificationByGameColumns.CLASSIFICATION_ID + " = " +
                        GameSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ClassificationAttributeColumns.CLASSIFICATION_ID,
                defaultSort = GameSightDatabase.GAMES+"."+ GameColumns.EXPECTED_RELEASE_DATE + " ASC",
                groupBy = GameSightDatabase.GAMES+"."+GameColumns.GAME_ID
        )
        public static Uri withClassification(String type){
            return buildUri(Path.GAMES, Path.CLASSIFICATION_BY_GAME, type);
        }
    }
    @TableEndpoint(table = GameSightDatabase.REVIEWS) public static class Reviews {
        @ContentUri(
                path = Path.REVIEWS,
                type = "vnd.android.cursor.dir/review",
                defaultSort = ReviewColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.REVIEWS);

        @InexactContentUri(
                name = "BY_GAME_ID",
                path = Path.REVIEWS + "/*",
                type = "vnd.android.cursor.item/review",
                whereColumn = ReviewColumns.GAME_ID,
                pathSegment = 1
        )
        public static Uri withGameId(long id){
            return buildUri(Path.REVIEWS, String.valueOf(id));
        }
    }

    @TableEndpoint(table = GameSightDatabase.VIDEOS) public static class Videos {
        @ContentUri(
                path = Path.VIDEOS,
                type = "vnd.android.cursor.dir/video",
                defaultSort = VideoColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.VIDEOS);

        @InexactContentUri(
                name = "BY_GAME_ID",
                path = Path.VIDEOS + "/*",
                type = "vnd.android.cursor.item/video",
                whereColumn = VideoColumns.GAME_ID,
                pathSegment = 1
        )
        public static Uri withGameId(long id){
            return buildUri(Path.VIDEOS, String.valueOf(id));
        }
    }

    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_ATTRIBUTES) public static class ClassificationAttributes {
        @ContentUri(
                path = Path.CLASSIFICATION_ATTRIBUTES,
                type = "vnd.android.cursor.dir/classification_attribute",
                defaultSort = ClassificationAttributeColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.CLASSIFICATION_ATTRIBUTES);

        @InexactContentUri(
                name = "CLASSIFICATION_ATTRIBUTE_ID",
                path = Path.CLASSIFICATION_ATTRIBUTES + "/#",
                type = "vnd.android.cursor.item/classification_attribute",
                whereColumn = ClassificationAttributeColumns.CLASSIFICATION_ID,
                pathSegment = 1
        )
        public static Uri withClassificationAttributeId(long id){
            return buildUri(Path.CLASSIFICATION_ATTRIBUTES, String.valueOf(id));
        }

        @InexactContentUri(
                name = "CLASSIFICATION_ATTRIBUTES_BY_GAME",
                path = Path.GAMES +"/"+ Path.CLASSIFICATION_ATTRIBUTES + "/*",
                type = "vnd.android.cursor.dir/classification_attribute",
                whereColumn = GameSightDatabase.CLASSIFICATION_BY_GAME+"."+ClassificationByGameColumns.GAME_ID,
                pathSegment = 2,
                join = "INNER JOIN "+GameSightDatabase.CLASSIFICATION_BY_GAME+" ON "+
                        GameSightDatabase.CLASSIFICATION_BY_GAME+"."+ClassificationByGameColumns.CLASSIFICATION_ID + " = " +
                        GameSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ClassificationAttributeColumns.CLASSIFICATION_ID +
                        " INNER JOIN "+GameSightDatabase.GAMES+" ON "+
                        GameSightDatabase.GAMES+"."+GameColumns.GAME_ID + " = " +
                        GameSightDatabase.CLASSIFICATION_BY_GAME+"."+ClassificationByGameColumns.GAME_ID,
                defaultSort = GameSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ ClassificationAttributeColumns.TYPE + " ASC"
        )
        public static Uri withGameId(String gameId){
            return buildUri(Path.GAMES, Path.CLASSIFICATION_ATTRIBUTES, gameId);
        }
    }

    @TableEndpoint(table = GameSightDatabase.CLASSIFICATION_BY_GAME) public static class ClassificationByGame {
        @ContentUri(
                path = Path.CLASSIFICATION_BY_GAME,
                type = "vnd.android.cursor.dir/classification_by_game",
                defaultSort = ClassificationByGameColumns._ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.CLASSIFICATION_BY_GAME);

        @InexactContentUri(
                name = "BY_GAME_ID",
                path = Path.CLASSIFICATION_BY_GAME + "/*",
                type = "vnd.android.cursor.item/video",
                whereColumn = ClassificationByGameColumns.GAME_ID,
                pathSegment = 1
        )
        public static Uri withGameId(long id){
            return buildUri(Path.CLASSIFICATION_BY_GAME, String.valueOf(id));
        }

    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
}
