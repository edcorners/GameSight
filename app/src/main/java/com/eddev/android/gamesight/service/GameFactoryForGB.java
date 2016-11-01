package com.eddev.android.gamesight.service;

import com.eddev.android.gamesight.Utility;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 10/15/2016.
 */

public class GameFactoryForGB {

    private static GameFactoryForGB instance = new GameFactoryForGB();

    public static GameFactoryForGB getInstance() {
        return instance;
    }

    private GameFactoryForGB() {
    }

    public Game createGamePreview(GBGame gBGame){
        Game newGame = new Game(gBGame.getId(),
                gBGame.getDeck(),
                Utility.getDate(gBGame.getExpectedReleaseDate()),
                gBGame.getCover(),
                gBGame.getThumb(),
                gBGame.getName(),
                null,
                gBGame.getNumberOfUserReviews(),
                Utility.getDateTime(gBGame.getOriginalReleaseDate()),
                null,
                null,
                0D,
                null
        );
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
