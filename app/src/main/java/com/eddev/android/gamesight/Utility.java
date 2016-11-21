package com.eddev.android.gamesight;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.eddev.android.gamesight.model.Game;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ed on 10/31/16.
 */

public class Utility {
    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

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

    public static @DrawableRes int getIconResourceForConsole(int platformId){
        int resource = -1;
        if(platformId == 3 ||
                platformId == 4 ||
                platformId == 9 ||
                platformId == 21 ||
                platformId == 23 ||
                platformId == 36 ||
                platformId == 43 ||
                platformId == 52 ||
                platformId == 57 ||
                platformId == 79 ||
                platformId == 87 ||
                platformId == 101 ||
                platformId == 106 ||
                platformId == 117 ||
                platformId == 138){
            resource = R.mipmap.ic_nintendo;
        }else if(platformId == 18 ||
                platformId == 19 ||
                platformId == 22 ||
                platformId == 35 ||
                platformId == 88 ||
                platformId == 129 ||
                platformId == 143 ||
                platformId == 146){
            resource = R.mipmap.ic_playstation;
        }else if(platformId == 20 ||
                platformId == 32 ||
                platformId == 86 ||
                platformId == 145){
            resource = R.mipmap.ic_xbox;
        }else if(platformId == 94){
            resource = R.mipmap.ic_pc ;
        }
        return resource;
    }

    public static @StringRes int getTitle(String collection){
        int resource = -1;
        switch (collection){
            case Game.DISCOVER:
                resource = R.string.discover_title;
                break;
            case Game.TRACKING:
                resource = R.string.tracking_title;
                break;
            case Game.OWNED:
                resource = R.string.owned_title;
                break;
        }
        return resource;
    }
}
