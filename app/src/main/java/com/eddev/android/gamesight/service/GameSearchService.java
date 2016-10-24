package com.eddev.android.gamesight.service;

import com.eddev.android.gamesight.client.giantbomb.SearchGBGamesAsyncTask;

/**
 * Created by Edison on 10/22/2016.
 */
public interface GameSearchService {
    public void fetchUpcomingGamesPreview(GamesLoadedCallback callback);
    public void searchGamesByName(String name, GamesLoadedCallback callback);
}
