package com.eddev.android.gamesight.service;

/**
 * Created by Edison on 10/22/2016.
 */
public interface IGameSearchService {
    public void fetchUpcomingGamesPreview(IGamesLoadedCallback callback);
    public void searchGamesByName(String name, IGamesLoadedCallback callback);
}
