package com.eddev.android.gamesight;

import android.content.Context;
import android.os.AsyncTask;

import com.eddev.android.gamesight.client.giantbomb.GiantBombService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Edison on 10/10/2016.
 */

public class FetchUpcomingGamesAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = FetchUpcomingGamesAsyncTask.class.getSimpleName();
    private Context context;

    /**
     * Default constructor
     * @param context application context
     */
    public FetchUpcomingGamesAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.giant_bomb_api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GiantBombService service = retrofit.create(GiantBombService.class);
        return null;
    }
}
