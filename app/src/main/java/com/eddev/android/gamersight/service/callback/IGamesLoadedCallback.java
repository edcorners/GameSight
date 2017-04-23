package com.eddev.android.gamersight.service.callback;

import com.eddev.android.gamersight.model.Game;

import java.util.List;

/**
 * Created by Edison on 10/22/2016.
 */

public interface IGamesLoadedCallback {
    void onGamesLoaded(List<Game> games, String error);
}
