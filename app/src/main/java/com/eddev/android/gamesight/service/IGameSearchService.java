package com.eddev.android.gamesight.service;

import com.eddev.android.gamesight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGameReviewsLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;

/**
 * Created by Edison on 10/22/2016.
 */
public interface IGameSearchService {
    public void fetchUpcomingGamesPreview(IGamesLoadedCallback callback);
    public void searchGamesByName(String name, IGamesLoadedCallback callback);
    public void findGameById(int id, IGameLoadedCallback callback);
    public void findReviewsByGameId(int id, IGameReviewsLoadedCallback callback);
}
