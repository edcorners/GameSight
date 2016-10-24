package com.eddev.android.gamesight.service;

import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 10/15/2016.
 */

public class GBGameFactory {

    private static GBGameFactory instance = new GBGameFactory();

    public static GBGameFactory getInstance() {
        return instance;
    }

    private GBGameFactory() {
    }

    public Game createGamePreview(GBGame gBGame){
        Game newGame = new Game(gBGame.getId(),
                                gBGame.getThumb(),
                                gBGame.getName());
        return newGame;
    }

    public List<Game> createGamePreviewList(List<GBGame> GBGames){
        List<Game> newGamesList = new ArrayList<Game>();
        for (GBGame current : GBGames) {
            newGamesList.add(createGamePreview(current));
        }
        return newGamesList;
    }
}
