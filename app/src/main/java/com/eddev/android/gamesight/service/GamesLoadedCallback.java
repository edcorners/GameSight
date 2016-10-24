package com.eddev.android.gamesight.service;

import com.eddev.android.gamesight.model.Game;

import java.util.List;

/**
 * Created by Edison on 10/22/2016.
 */

public interface GamesLoadedCallback {
    void onGamesLoaded(List<Game> games);
}
