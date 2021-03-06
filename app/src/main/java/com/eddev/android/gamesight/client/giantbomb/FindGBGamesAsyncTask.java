package com.eddev.android.gamesight.client.giantbomb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.eddev.android.gamesight.BuildConfig;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBGamesResponse;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;
import com.eddev.android.gamesight.service.factory.GameFactoryFromGB;

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

    public static final int OK_RESPONSE = 1;

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
    private IGamesLoadedCallback mCallback;
    private String mError = null;

    /**
     * Default constructor
     * @param context application mContext
     */
    public FindGBGamesAsyncTask(Context context, IGamesLoadedCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
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
            if(response.body().getStatusCode() != OK_RESPONSE) {
                mError = response.body().getError();
            }else {
                gbGamesResponse = response.body();
                gBGames = gbGamesResponse.getResults();
            }
        } catch (IOException e) {
            mError = mContext.getString(R.string.service_down_text);
            Log.e(LOG_TAG, "Error ", e);
        }catch (Exception e) {
            mError = mContext.getString(R.string.failed_to_connect_text);
            Log.e(LOG_TAG, "Error ", e);
        }
        return gBGames;
    }

    @Override
    protected void onPostExecute(List<GBGame> gBGames) {
        super.onPostExecute(gBGames);
        mCallback.onGamesLoaded(GameFactoryFromGB.getInstance().createGamePreviewList(gBGames), mError);
    }
}
