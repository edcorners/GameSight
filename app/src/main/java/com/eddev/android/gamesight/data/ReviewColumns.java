package com.eddev.android.gamesight.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Edison on 9/25/2016.
 */

public interface ReviewColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @Unique
    String REVIEW_ID = "review_id";

    @DataType(DataType.Type.INTEGER)
    @References(column = GameColumns.GAME_ID, table = GameSightDatabase.GAMES)
    String GAME_ID = "game_id";

    @DataType(DataType.Type.TEXT)
    String DESCRIPTION = "description";

    @DataType(DataType.Type.INTEGER)
    String DATE = "date";

    @DataType(DataType.Type.REAL)
    String SCORE = "score";

    @DataType(DataType.Type.TEXT)
    String REVIEWER = "reviewer";
}
