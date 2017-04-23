package com.eddev.android.gamersight.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.eddev.android.gamersight.data.ClassificationAttributeColumns;
import com.eddev.android.gamersight.data.GamerSightDatabase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Edison on 9/24/2016.
 */
public class ClassificationAttribute implements Parcelable {

    public static final String[] CLASSIFICATION_ATTR_PROJECTION =
            {GamerSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ ClassificationAttributeColumns.CLASSIFICATION_ID,
             GamerSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ClassificationAttributeColumns.TYPE,
             GamerSightDatabase.CLASSIFICATION_ATTRIBUTES+"."+ClassificationAttributeColumns.VALUE};

    private static final int INDEX_CLASSIFICATION_ID = 0;
    private static final int INDEX_TYPE = 1;
    private static final int INDEX_VALUE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GENRE, PLATFORM, PUBLISHER})
    public @interface GameAttribType {}
    public static final String GENRE = "GENRE";
    public static final String PLATFORM = "PLATFORM";
    public static final String PUBLISHER = "PUBLISHER";

    private int id;
    private @GameAttribType
    String type;
    private String value;

    public ClassificationAttribute(int id, @GameAttribType String type, String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public ClassificationAttribute(Cursor cursor) {
        this.id = cursor.getInt(INDEX_CLASSIFICATION_ID);
        if(cursor.getString(INDEX_TYPE) != null) {
            switch (cursor.getString(INDEX_TYPE)) {
                case GENRE:
                    type = GENRE;
                    break;
                case PLATFORM:
                    type = PLATFORM;
                    break;
                case PUBLISHER:
                    type = PUBLISHER;
                    break;
            }
        }
        this.value = cursor.getString(INDEX_VALUE);
    }



    public ContentValues toContentValues(int classificationId) {
        ContentValues cv = new ContentValues();
        cv.put(ClassificationAttributeColumns.CLASSIFICATION_ID, id);
        cv.put(ClassificationAttributeColumns.TYPE, type);
        cv.put(ClassificationAttributeColumns.VALUE, value);
        return cv;
    }

    protected ClassificationAttribute(Parcel in) {
        id = in.readInt();
        String valueString = in.readString();
        if(valueString != null) {
            switch (valueString) {
                case GENRE:
                    type = GENRE;
                    break;
                case PLATFORM:
                    type = PLATFORM;
                    break;
                case PUBLISHER:
                    type = PUBLISHER;
                    break;
            }
        }
        value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ClassificationAttribute> CREATOR = new Parcelable.Creator<ClassificationAttribute>() {
        @Override
        public ClassificationAttribute createFromParcel(Parcel in) {
            return new ClassificationAttribute(in);
        }

        @Override
        public ClassificationAttribute[] newArray(int size) {
            return new ClassificationAttribute[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean typeEquals(@GameAttribType String classificationAttribute) {
        return this.type.equals(classificationAttribute);
    }

}