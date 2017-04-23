package com.eddev.android.gamersight.service.callback;

import com.eddev.android.gamersight.model.Game;

/**
 * Created by Edison on 10/22/2016.
 */

public interface IGameLoadedCallback {
    void onGameLoaded(Game game, String error);
}
