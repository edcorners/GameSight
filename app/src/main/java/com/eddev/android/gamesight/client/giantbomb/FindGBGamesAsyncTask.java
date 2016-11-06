package com.eddev.android.gamesight.client.giantbomb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.eddev.android.gamesight.BuildConfig;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBGamesResponse;
import com.eddev.android.gamesight.service.factory.GameFactoryFromGB;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Edison on 10/10/2016.
 */

public class FindGBGamesAsyncTask extends AsyncTask<HashMap<String,String>, Void, List<GBGame>> {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FILTER, SORT, LIMIT, FORMAT, FIELD_LIST})
    public @interface GBGamesServiceParameterName {}
    public static final String FILTER = "filter";
    public static final String SORT = "sort";
    public static final String LIMIT = "limit";
    public static final String FORMAT = "format";
    public static final String FIELD_LIST = "fieldList";

    private final String LOG_TAG = FindGBGamesAsyncTask.class.getSimpleName();
    private Context mContext;
    private IGamesLoadedCallback callback;

    /**
     * Default constructor
     * @param mContext application mContext
     */
    public FindGBGamesAsyncTask(Context mContext, IGamesLoadedCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected List<GBGame> doInBackground(HashMap<String, String>... hashMaps) {
        if (hashMaps.length == 0 || hashMaps[0].isEmpty())
            throw new IllegalArgumentException("Parameter hash cant be empty");

        HashMap<String, String> params = hashMaps[0];

        List<GBGame> gBGames = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.giant_bomb_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GiantBombClient service = retrofit.create(GiantBombClient.class);
        Call<GBGamesResponse> getGamesCall = service.getGames(params.get(FORMAT),
                params.get(FILTER),
                params.get(FIELD_LIST),
                params.get(SORT),
                params.get(LIMIT),
                BuildConfig.GIANT_BOMB_API_KEY);
        GBGamesResponse gbGamesResponse = null;
        try {
            Response<GBGamesResponse> response = getGamesCall.execute();
            Log.v(LOG_TAG, response.raw().toString());
            gbGamesResponse = response.body();
            gBGames = gbGamesResponse.getResults();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        }
        return gBGames;
    }

    @Override
    protected void onPostExecute(List<GBGame> gBGames) {
        super.onPostExecute(gBGames);
        callback.onGamesLoaded(GameFactoryFromGB.getInstance().createGamePreviewList(gBGames));
    }
}
