package com.eddev.android.gamesight.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by Edison on 9/25/2016.
 */

public interface VideoColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String VIDEO_ID = "video_id";

    @DataType(DataType.Type.INTEGER)
    @References(column = GameColumns.GAME_ID, table = GamerSightDatabase.GAMES)
    String GAME_ID = "game_id";

    @DataType(DataType.Type.TEXT)
    String NAME = "name";

    @DataType(DataType.Type.TEXT)
    String URL = "url";
}
