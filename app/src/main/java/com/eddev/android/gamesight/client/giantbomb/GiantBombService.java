package com.eddev.android.gamesight.client.giantbomb;

import com.eddev.android.gamesight.client.giantbomb.model.Games;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.format;

/**
 * Created by Edison on 9/22/2016.
 */

public interface GiantBombService {
    @GET("games")
    Call<Games> getGames(@Query("format") String format,
                         @Query("filter") String filter,
                         @Query("sort") String sort,
                         @Query("limit") String limit,
                         @Query("api_key") String apiKey);

    @GET("game/{id}")
    Call<Games> getGame(@Path("id") int gameId,
                        @Query("format") String format,
                        @Query("filter") String filter,
                        @Query("api_key") String apiKey);
}
