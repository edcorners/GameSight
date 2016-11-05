package com.eddev.android.gamesight.client.giantbomb;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.StringDef;
import android.util.Log;

import com.eddev.android.gamesight.BuildConfig;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.client.giantbomb.model.GBGameReviewResponse;
import com.eddev.android.gamesight.client.giantbomb.model.GBReview;
import com.eddev.android.gamesight.service.GameFactoryForGB;
import com.eddev.android.gamesight.service.IGameReviewsLoadedCallback;

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

public class FindGBGameReviewsAsyncTask extends AsyncTask<HashMap<String,String>, Void, List<GBReview>> {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FILTER, SORT, LIMIT, FORMAT, FIELD_LIST})
    public @interface GBGameReviewsServiceParameterName {}
    public static final String FILTER = "filter";
    public static final String SORT = "sort";
    public static final String LIMIT = "limit";
    public static final String FORMAT = "format";
    public static final String FIELD_LIST = "fieldList";

    private final String LOG_TAG = FindGBGameReviewsAsyncTask.class.getSimpleName();
    private Context mContext;
    private IGameReviewsLoadedCallback callback;

    /**
     * Default constructor
     * @param mContext application mContext
     */
    public FindGBGameReviewsAsyncTask(Context mContext, IGameReviewsLoadedCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected List<GBReview> doInBackground(HashMap<String, String>... hashMaps) {
        if (hashMaps.length == 0 || hashMaps[0].isEmpty())
            throw new IllegalArgumentException("Parameter hash cant be empty");

        HashMap<String, String> params = hashMaps[0];

        List<GBReview> gbReviews = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.giant_bomb_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GiantBombClient service = retrofit.create(GiantBombClient.class);
        Call<GBGameReviewResponse> getGamesCall = service.getReviews(params.get(FORMAT),
                params.get(FILTER),
                params.get(FIELD_LIST),
                params.get(SORT),
                params.get(LIMIT),
                BuildConfig.GIANT_BOMB_API_KEY);
        GBGameReviewResponse GBResponse = null;
        try {
            Response<GBGameReviewResponse> response = getGamesCall.execute();
            Log.v(LOG_TAG, response.raw().toString());
            GBResponse = response.body();
            gbReviews = GBResponse.getResults();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        }
        return gbReviews;
    }

    @Override
    protected void onPostExecute(List<GBReview> gbReviews) {
        super.onPostExecute(gbReviews);
        callback.onGameReviewLoaded(GameFactoryForGB.getInstance().createGameReviews(gbReviews));
    }
}
