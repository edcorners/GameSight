package com.eddev.android.gamesight;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ed on 10/31/16.
 */

public class Utility {
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDateTime(String date) {
        Date originalReleaseDate = null;
        try {
            if(!TextUtils.isEmpty(date)) {
                originalReleaseDate = dateTimeFormat.parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return originalReleaseDate;
    }

    public static Date getDate(String date) {
        Date originalReleaseDate = null;
        try {
            if(!TextUtils.isEmpty(date)) {
                originalReleaseDate = dateFormat.parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return originalReleaseDate;
    }
}
