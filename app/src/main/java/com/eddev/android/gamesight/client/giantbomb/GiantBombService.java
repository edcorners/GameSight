package com.eddev.android.gamesight.client.giantbomb;

import com.eddev.android.gamesight.client.giantbomb.model.RequestResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Edison on 9/22/2016.
 */

public interface GiantBombService {
    @GET("games")
    Call<RequestResults> getGames(@Query("format") String format,
                                  @Query("filter") String filter,
                                  @Query("sort") String sort,
                                  @Query("limit") String limit,
                                  @Query("api_key") String apiKey);

    @GET("game/{id}")
    Call<RequestResults> getGame(@Path("id") int gameId,
                                 @Query("format") String format,
                                 @Query("filter") String filter,
                                 @Query("api_key") String apiKey);
}
