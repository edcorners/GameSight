package com.eddev.android.gamesight.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Edison on 9/25/2016.
 */

public interface GameColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @Unique
    String GAME_ID = "game_id";

    @DataType(DataType.Type.TEXT)
    String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    String EXPECTED_RELEASE_DATE = "expectedReleaseDate";

    @DataType(DataType.Type.TEXT)
    String IMAGE_URL = "imageUrl";

    @DataType(DataType.Type.TEXT)
    String THUMBNAIL_URL = "thumbnailUrl";

    @DataType(DataType.Type.TEXT)
    String NAME = "name";

    //private List<ClassificationAttribute> classificationAttributes = new ArrayList<ClassificationAttribute>();

    @DataType(DataType.Type.INTEGER)
    String NUMBER_OF_USER_REVIEWS = "numberOfUserReviews";

    @DataType(DataType.Type.TEXT)
    String ORIGINAL_RELEASE_DATE = "originalReleaseDate";

    //private List<ReviewColumns> reviews = new ArrayList<ReviewColumns>();
    //private List<Video> videos = new ArrayList<Video>();

    @DataType(DataType.Type.REAL)
    String COMPLETION = "completion";

    @DataType(DataType.Type.TEXT)
    String COLLECTION = "collection";
}
