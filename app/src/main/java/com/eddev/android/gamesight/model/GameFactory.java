package com.eddev.android.gamesight.model;

import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 10/15/2016.
 */

public class GameFactory {

    private static GameFactory instance = new GameFactory();

    public static GameFactory getInstance() {
        return instance;
    }

    private GameFactory() {
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
