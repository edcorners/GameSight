package com.eddev.android.gamesight.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Edison on 9/24/2016.
 */
public class ClassificationAttribute implements Parcelable {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({GENRE, PLATFORM, PUBLISHER})
    public @interface GameAttribType {}
    private static final String GENRE = "GENRE";
    private static final String PLATFORM = "PLATFORM";
    private static final String PUBLISHER = "PUBLISHER";

    private long id;
    private @GameAttribType
    String type;
    private String value;

    protected ClassificationAttribute(Parcel in) {
        id = in.readLong();
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
        dest.writeLong(id);
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
}