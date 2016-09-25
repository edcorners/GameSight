package com.eddev.android.gamesight.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Edison on 9/25/2016.
 */

public interface ClassificationAttributeColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @Unique
    String CLASSIFICATION_ID = "classification_id";

    @DataType(DataType.Type.TEXT)
    String TYPE = "type";

    @DataType(DataType.Type.TEXT)
    String VALUE = "value";
}
