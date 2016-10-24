package com.eddev.android.gamesight.service;

import android.content.Context;

import com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask.FIELD_LIST;
import static com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask.FILTER;
import static com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask.FORMAT;
import static com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask.LIMIT;
import static com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask.SORT;

/**
 * Created by Edison on 10/22/2016.
 */

public class GiantBombSearchService implements GameSearchService{

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Context mContext;
    private HashMap<String, String> mParameters;

    public GiantBombSearchService(Context context) {
        this.mContext = context;
        this.mParameters = new HashMap<>();
    }

    public void fetchUpcomingGamesPreview(GamesLoadedCallback callback){
        mParameters.clear();
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date inThreeMonths = cal.getTime();

        mParameters.put(FILTER, "original_release_date:"+mDateFormat.format(now)+"|"+mDateFormat.format(inThreeMonths));
        mParameters.put(SORT, "original_release_date:asc");
        mParameters.put(LIMIT, "3");
        mParameters.put(FORMAT, "json");
        mParameters.put(FIELD_LIST, "id,name,image");

        SearchGBGamesAsyncTask searchGBGamesAsyncTask = new SearchGBGamesAsyncTask(mContext, callback);
        searchGBGamesAsyncTask.execute(mParameters);
    }

    public void searchGamesByName(String name, GamesLoadedCallback callback){
        mParameters.clear();
        mParameters.put(FILTER, "name:"+name);
        mParameters.put(SORT, "number_of_user_reviews:desc");
        mParameters.put(LIMIT, "20");
        mParameters.put(FORMAT, "json");
        mParameters.put(FIELD_LIST, "id,name,image,deck");

        SearchGBGamesAsyncTask searchGBGamesAsyncTask = new SearchGBGamesAsyncTask(mContext, callback);
        searchGBGamesAsyncTask.execute(mParameters);
    }
}
