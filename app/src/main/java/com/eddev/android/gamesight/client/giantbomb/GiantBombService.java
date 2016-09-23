package com.eddev.android.gamesight.client.giantbomb;

import com.eddev.android.gamesight.client.giantbomb.model.Games;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Edison on 9/22/2016.
 */

public interface GiantBombService {
    @GET("games")
    Call<Games> getGames(@Query("format") String format,
                         @Query("filter") String filter,
                         @Query("api_key") String apiKey);
    //TODO add full list of params
}
