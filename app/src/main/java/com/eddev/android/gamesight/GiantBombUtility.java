package com.eddev.android.gamesight;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.eddev.android.gamesight.model.Game;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by ed on 10/31/16.
 */

public class GiantBombUtility {
    public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final static DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

    private final static Integer[] nintendoPlatformIds = new Integer[]{3,4,9,21,23,36,43,52,57,79,87,101,106,117,138};
    private final static Integer[] playstationPlatformIds = new Integer[]{18,19,22,35,88,129,143,146};
    private final static Integer[] xboxPlatformIds = new Integer[]{20,32,86,145};
    private final static Integer[] pcPlatformIds = new Integer[]{94};

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

        if(Arrays.asList(nintendoPlatformIds).contains(platformId)){
            resource = R.mipmap.ic_nintendo;
        }else if(Arrays.asList(playstationPlatformIds).contains(platformId)){
            resource = R.mipmap.ic_playstation;
        }else if(Arrays.asList(xboxPlatformIds).contains(platformId)){
            resource = R.mipmap.ic_xbox;
        }else if(Arrays.asList(pcPlatformIds).contains(platformId)){
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

    public static String[] getConsolesArrayResourceAsStringArray(Context context){
        String[] stringArray = context.getResources().getStringArray(R.array.console_names);
        Arrays.sort(stringArray);
        return stringArray;
    }

    public static String getConsoleByPlatformId(Context context, Integer platformId) {
        String console = "";
        if(Arrays.binarySearch(nintendoPlatformIds, platformId) >= 0){
            console = context.getResources().getString(R.string.platform_nintendo);
        }else if(Arrays.binarySearch(playstationPlatformIds, platformId) >= 0) {
            console = context.getResources().getString(R.string.platform_playstation);
        }else if(Arrays.binarySearch(xboxPlatformIds, platformId) >= 0) {
            console = context.getResources().getString(R.string.platform_xbox);
        }else if(Arrays.binarySearch(pcPlatformIds, platformId) >= 0) {
            console = context.getResources().getString(R.string.platform_pc);
        }else{
            console = context.getResources().getString(R.string.platform_other);
        }
        return console;
    }
}
