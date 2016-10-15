package com.eddev.android.gamesight;

import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;

import com.eddev.android.gamesight.client.giantbomb.GiantBombService;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBResponse;
import com.eddev.android.gamesight.model.GameFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Edison on 10/10/2016.
 */

public class FetchUpcomingGamesAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = FetchUpcomingGamesAsyncTask.class.getSimpleName();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Callback callback;
    private List<GBGame> gBGames = null;
    private GameFactory gameFactory = GameFactory.getInstance();

    public interface Callback{
        void onUpcomingGamesLoaded(List<com.eddev.android.gamesight.model.Game> games);
    }

    /**
     * Default constructor
     * @param context application context
     */
    public FetchUpcomingGamesAsyncTask(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date inThreeMonths = cal.getTime();
        String filter = "original_release_date:"+dateFormat.format(now)+"|"+dateFormat.format(inThreeMonths);
        String sort = "original_release_date:asc";
        String limit = "3";
        String format = "json";
        String fieldList = "id,name,image";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.giant_bomb_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GiantBombService service = retrofit.create(GiantBombService.class);
        Call<GBResponse> getGamesCall = service.getGames(format, filter, fieldList, sort, limit, BuildConfig.GIANT_BOMB_API_KEY);
        GBResponse GBResponse = null;
        try {
            Response<GBResponse> response = getGamesCall.execute();
            Log.v(LOG_TAG, response.raw().toString());
            GBResponse = response.body();
            gBGames = GBResponse.getResults();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        callback.onUpcomingGamesLoaded(gameFactory.createGamePreviewList(gBGames));
    }
}
