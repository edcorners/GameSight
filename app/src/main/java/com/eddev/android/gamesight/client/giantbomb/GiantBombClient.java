package com.eddev.android.gamesight.client.giantbomb;

import com.eddev.android.gamesight.client.giantbomb.model.GBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Edison on 9/22/2016.
 */

public interface GiantBombClient {
    @GET("games")
    Call<GBResponse> getGames(@Query("format") String format,
                              @Query("filter") String filter,
                              @Query("field_list")String fieldList,
                              @Query("sort") String sort,
                              @Query("limit") String limit,
                              @Query("api_key") String apiKey);

    @GET("game/{id}")
    Call<GBResponse> getGame(@Path("id") int gameId,
                             @Query("format") String format,
                             @Query("fieldList") String fieldList,
                             @Query("api_key") String apiKey);
}
