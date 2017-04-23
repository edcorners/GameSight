package com.eddev.android.gamersight.service;

import android.support.annotation.NonNull;

import com.eddev.android.gamersight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamersight.service.callback.IGameReviewsLoadedCallback;
import com.eddev.android.gamersight.service.callback.IGamesLoadedCallback;

/**
 * Created by Edison on 10/22/2016.
 */
public interface IGameSearchService {
    public void fetchUpcomingGamesPreview(@NonNull IGamesLoadedCallback callback, int limit);
    public void searchGamesByName(String name, @NonNull IGamesLoadedCallback callback);
    public void findGameById(int id, @NonNull IGameLoadedCallback callback);
    public void findReviewsByGameId(int id, @NonNull IGameReviewsLoadedCallback callback);
}
