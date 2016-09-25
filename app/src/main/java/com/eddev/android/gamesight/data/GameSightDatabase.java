package com.eddev.android.gamesight.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Edison on 9/25/2016.
 */
@Database(version = GameSightDatabase.VERSION, packageName = "com.eddev.android.gamesight.provider")
public final class GameSightDatabase {
    public static final int VERSION = 1;

    @Table(GameColumns.class) public static final String GAMES = "games";
    @Table(ReviewColumns.class) public static final String REVIEWS = "reviews";
    @Table(VideoColumns.class) public static final String VIDEOS = "videos";
    @Table(ClassificationAttributeColumns.class) public static final String CLASSIFICATION_ATTRIBUTES = "classification_attributes";
    @Table(ClassificationByGameColumns.class) public static final String CLASSIFICATION_BY_GAME = "classification_by_game";

}
