package com.eddev.android.gamersight.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by Edison on 9/25/2016.
 */

public interface ClassificationByGameColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @References(column = GameColumns.GAME_ID, table = GamerSightDatabase.GAMES)
    String GAME_ID = "game_id";

    @DataType(DataType.Type.INTEGER)
    @References(column = ClassificationAttributeColumns.CLASSIFICATION_ID, table = GamerSightDatabase.GAMES)
    String CLASSIFICATION_ID = "classification_id";
}
