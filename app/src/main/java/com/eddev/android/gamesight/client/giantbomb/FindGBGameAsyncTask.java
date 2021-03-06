package com.eddev.android.gamesight.client.giantbomb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.eddev.android.gamesight.BuildConfig;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBGameResponse;
import com.eddev.android.gamesight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamesight.service.factory.GameFactoryFromGB;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eddev.android.gamesight.client.giantbomb.FindGBGamesAsyncTask.FILTER;
import static com.eddev.android.gamesight.client.giantbomb.FindGBGamesAsyncTask.LIMIT;

/**
 * Created by Edison on 10/10/2016.
 */

public class FindGBGameAsyncTask extends AsyncTask<HashMap<String,String>, Void, GBGame> {

    public static final int OK_RESPONSE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FILTER, LIMIT, FORMAT, FIELD_LIST, GAME_ID})
    public @interface GBGameServiceParameterName {}
    public static final String FORMAT = "format";
    public static final String FIELD_LIST = "fieldList";
    public static final String GAME_ID = "id";

    private final String LOG_TAG = FindGBGameAsyncTask.class.getSimpleName();
    private Context mContext;
    private IGameLoadedCallback mCallback;
    private String mError = null;

    /**
     * Default constructor
     * @param context application context
     *
     */
    public FindGBGameAsyncTask(Context context, IGameLoadedCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    @Override
    protected GBGame doInBackground(HashMap<String, String>... hashMaps) {
        if (hashMaps.length == 0 || hashMaps[0].isEmpty())
            throw new IllegalArgumentException("Parameter hash cant be empty");

        HashMap<String, String> params = hashMaps[0];

        GBGame gBGame = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.giant_bomb_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GiantBombClient service = retrofit.create(GiantBombClient.class);
        Call<GBGameResponse> getGamesCall = service.getGame(Integer.parseInt(params.get(GAME_ID)),
                params.get(FORMAT),
                params.get(FIELD_LIST),
                BuildConfig.GIANT_BOMB_API_KEY);
        GBGameResponse gbGamesResponse = null;
        try {
            Response<GBGameResponse> response = getGamesCall.execute();
            Log.d(LOG_TAG, response.raw().toString());
            if(response.body().getStatusCode() != OK_RESPONSE) {
                mError = response.body().getError();
            }else {
                gbGamesResponse = response.body();
                gBGame = gbGamesResponse.getResults();
            }
        } catch (IOException e) {
            mError = mContext.getString(R.string.service_down_text);
            Log.e(LOG_TAG, "Error ", e);
        }catch (Exception e) {
            mError = mContext.getString(R.string.failed_to_connect_text);
            Log.e(LOG_TAG, "Error ", e);
        }
        return gBGame;
    }

    @Override
    protected void onPostExecute(GBGame gBGame) {
        super.onPostExecute(gBGame);
        mCallback.onGameLoaded(GameFactoryFromGB.getInstance().createGame(gBGame), mError);
    }
}
