package com.eddev.android.gamesight.service;

import android.content.Context;

import com.eddev.android.gamesight.Utility;
import com.eddev.android.gamesight.client.giantbomb.FindGBGameAsyncTask;
import com.eddev.android.gamesight.client.giantbomb.FindGBGameReviewsAsyncTask;
import com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Edison on 10/22/2016.
 */

public class GiantBombSearchService implements IGameSearchService {

    private final Context mContext;
    private HashMap<String, String> mParameters;

    public GiantBombSearchService(Context context) {
        this.mContext = context;
        this.mParameters = new HashMap<>();
    }

    public void fetchUpcomingGamesPreview(IGamesLoadedCallback callback){
        mParameters.clear();
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date inThreeMonths = cal.getTime();

        mParameters.put(SearchGBGamesAsyncTask.FILTER, "original_release_date:"+ Utility.dateTimeFormat.format(now)+"|"+Utility.dateTimeFormat.format(inThreeMonths));
        mParameters.put(SearchGBGamesAsyncTask.SORT, "original_release_date:asc");
        mParameters.put(SearchGBGamesAsyncTask.LIMIT, "3");
        mParameters.put(SearchGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(SearchGBGamesAsyncTask.FIELD_LIST, "id,name,image");

        SearchGBGamesAsyncTask searchGBGamesAsyncTask = new SearchGBGamesAsyncTask(mContext, callback);
        searchGBGamesAsyncTask.execute(mParameters);
    }

    public void searchGamesByName(String name, IGamesLoadedCallback callback){
        mParameters.clear();
        mParameters.put(SearchGBGamesAsyncTask.FILTER, "name:"+name);
        mParameters.put(SearchGBGamesAsyncTask.SORT, "number_of_user_reviews:desc");
        mParameters.put(SearchGBGamesAsyncTask.LIMIT, "20");
        mParameters.put(SearchGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(SearchGBGamesAsyncTask.FIELD_LIST, "id,name,image,deck");

        SearchGBGamesAsyncTask searchGBGamesAsyncTask = new SearchGBGamesAsyncTask(mContext, callback);
        searchGBGamesAsyncTask.execute(mParameters);
    }

    public void findGameById(int id, IGameLoadedCallback callback){
        mParameters.clear();
        mParameters.put(FindGBGameAsyncTask.GAME_ID, "game:"+id);
        mParameters.put(FindGBGameAsyncTask.FORMAT, "json");
        mParameters.put(FindGBGameAsyncTask.FIELD_LIST, "deck,expected_release_day,expected_release_month,expected_release_year,id,image,name,number_of_user_reviews,original_release_date,platforms,images,videos,genres,publishers,reviews");

        FindGBGameAsyncTask findGBGameAsyncTask = new FindGBGameAsyncTask(mContext, callback);
        findGBGameAsyncTask.execute(mParameters);
    }

    public void findReviewsByGameId(int id, IGameReviewsLoadedCallback callback){
        mParameters.clear();
        mParameters.put(SearchGBGamesAsyncTask.FILTER, "game:"+ id);
        mParameters.put(SearchGBGamesAsyncTask.SORT, "");
        mParameters.put(SearchGBGamesAsyncTask.LIMIT, "10");
        mParameters.put(SearchGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(SearchGBGamesAsyncTask.FIELD_LIST, "deck,publish_date,score,reviewer");

        FindGBGameReviewsAsyncTask findGBGameReviewsAsyncTask = new FindGBGameReviewsAsyncTask(mContext, callback);
        findGBGameReviewsAsyncTask.execute(mParameters);
    }
}
