package com.eddev.android.gamesight.service;

import android.content.Context;

import com.eddev.android.gamesight.Utility;
import com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask;

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

public class GiantBombSearchServiceI implements IGameSearchService {

    private final Context mContext;
    private HashMap<String, String> mParameters;

    public GiantBombSearchServiceI(Context context) {
        this.mContext = context;
        this.mParameters = new HashMap<>();
    }

    public void fetchUpcomingGamesPreview(IGamesLoadedCallback callback){
        mParameters.clear();
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date inThreeMonths = cal.getTime();

        mParameters.put(FILTER, "original_release_date:"+ Utility.dateTimeFormat.format(now)+"|"+Utility.dateTimeFormat.format(inThreeMonths));
        mParameters.put(SORT, "original_release_date:asc");
        mParameters.put(LIMIT, "3");
        mParameters.put(FORMAT, "json");
        mParameters.put(FIELD_LIST, "id,name,image");

        SearchGBGamesAsyncTask searchGBGamesAsyncTask = new SearchGBGamesAsyncTask(mContext, callback);
        searchGBGamesAsyncTask.execute(mParameters);
    }

    public void searchGamesByName(String name, IGamesLoadedCallback callback){
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
