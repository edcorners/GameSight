package com.eddev.android.gamesight.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.eddev.android.gamesight.Utility;
import com.eddev.android.gamesight.client.giantbomb.FindGBGameAsyncTask;
import com.eddev.android.gamesight.client.giantbomb.FindGBReviewsAsyncTask;
import com.eddev.android.gamesight.client.giantbomb.FindGBGamesAsyncTask;
import com.eddev.android.gamesight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGameReviewsLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;

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

    public void fetchUpcomingGamesPreview(@NonNull IGamesLoadedCallback callback){
        mParameters = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date inThreeMonths = cal.getTime();

        mParameters.put(FindGBGamesAsyncTask.FILTER, "original_release_date:"+ Utility.dateTimeFormat.format(now)+"|"+Utility.dateTimeFormat.format(inThreeMonths));
        mParameters.put(FindGBGamesAsyncTask.SORT, "original_release_date:asc");
        mParameters.put(FindGBGamesAsyncTask.LIMIT, "5");
        mParameters.put(FindGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(FindGBGamesAsyncTask.FIELD_LIST, "id,name,image,deck,expected_release_day,expected_release_month,expected_release_year,original_release_date");

        FindGBGamesAsyncTask findGBGamesAsyncTask = new FindGBGamesAsyncTask(mContext, callback);
        findGBGamesAsyncTask.execute(mParameters);
    }

    public void searchGamesByName(String name, @NonNull IGamesLoadedCallback callback){
        mParameters = new HashMap<>();
        mParameters.put(FindGBGamesAsyncTask.FILTER, "name:"+name);
        mParameters.put(FindGBGamesAsyncTask.SORT, "number_of_user_reviews:desc");
        mParameters.put(FindGBGamesAsyncTask.LIMIT, "20");
        mParameters.put(FindGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(FindGBGamesAsyncTask.FIELD_LIST, "id,name,image,deck,expected_release_day,expected_release_month,expected_release_year,original_release_date");

        FindGBGamesAsyncTask findGBGamesAsyncTask = new FindGBGamesAsyncTask(mContext, callback);
        findGBGamesAsyncTask.execute(mParameters);
    }

    public void findGameById(int id, @NonNull IGameLoadedCallback callback){
        mParameters = new HashMap<>();
        mParameters.put(FindGBGameAsyncTask.GAME_ID, String.valueOf(id));
        mParameters.put(FindGBGameAsyncTask.FORMAT, "json");
        mParameters.put(FindGBGameAsyncTask.FIELD_LIST, "deck,expected_release_day,expected_release_month,expected_release_year,id,image,name,number_of_user_reviews,original_release_date,platforms,images,videos,genres,publishers,reviews");

        FindGBGameAsyncTask findGBGameAsyncTask = new FindGBGameAsyncTask(mContext, callback);
        findGBGameAsyncTask.execute(mParameters);
    }

    public void findReviewsByGameId(int id, IGameReviewsLoadedCallback callback){
        mParameters = new HashMap<>();
        mParameters.put(FindGBGamesAsyncTask.FILTER, "game:"+ id);
        mParameters.put(FindGBGamesAsyncTask.SORT, "");
        mParameters.put(FindGBGamesAsyncTask.LIMIT, "10");
        mParameters.put(FindGBGamesAsyncTask.FORMAT, "json");
        mParameters.put(FindGBGamesAsyncTask.FIELD_LIST, "deck,publish_date,score,reviewer");

        FindGBReviewsAsyncTask findGBReviewsAsyncTask = new FindGBReviewsAsyncTask(mContext, callback);
        findGBReviewsAsyncTask.execute(mParameters);
    }
}
