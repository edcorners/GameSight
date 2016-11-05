package com.eddev.android.gamesight.service;

import android.support.annotation.NonNull;

import com.eddev.android.gamesight.Utility;
import com.eddev.android.gamesight.client.giantbomb.model.GBGame;
import com.eddev.android.gamesight.client.giantbomb.model.GBGenre;
import com.eddev.android.gamesight.client.giantbomb.model.GBPlatform;
import com.eddev.android.gamesight.client.giantbomb.model.GBPublisher;
import com.eddev.android.gamesight.client.giantbomb.model.GBReview;
import com.eddev.android.gamesight.client.giantbomb.model.GBVideo;
import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.model.Video;

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

    public Game createGame(GBGame gBGame) {
        List<ClassificationAttribute> classificationAttributes = getClassificationAttributes(gBGame);
        List<Video> videos = getVideos(gBGame);

        Game newGame = new Game(gBGame.getId(),
                gBGame.getDeck(),
                Utility.getDate(gBGame.getExpectedReleaseDate()),
                gBGame.getCover(),
                gBGame.getThumb(),
                gBGame.getName(),
                classificationAttributes,
                gBGame.getNumberOfUserReviews(),
                Utility.getDateTime(gBGame.getOriginalReleaseDate()),
                null,
                videos,
                0D,
                null
        );
        return newGame;
    }

    @NonNull
    private List<Video> getVideos(GBGame gBGame) {
        List<Video> videos = new ArrayList<>();
        for(GBVideo current : gBGame.getVideos()){
            Video newVideo = new Video(current.getId(), current.getName(), current.getSiteDetailUrl());
            videos.add(newVideo);
        }
        return videos;
    }

    @NonNull
    private List<ClassificationAttribute> getClassificationAttributes(GBGame gBGame) {
        List<ClassificationAttribute> classificationAttributes = new ArrayList<>();

        for (GBPlatform current: gBGame.getPlatforms()) {
            ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                    ClassificationAttribute.PLATFORM,
                    current.getName());
            classificationAttributes.add(classificationAttribute);
        }

        for (GBGenre current: gBGame.getGenres()) {
            ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                    ClassificationAttribute.GENRE,
                    current.getName());
            classificationAttributes.add(classificationAttribute);
        }

        for (GBPublisher current: gBGame.getPublishers()) {
            ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                    ClassificationAttribute.PUBLISHER,
                    current.getName());
            classificationAttributes.add(classificationAttribute);
        }
        return classificationAttributes;
    }

    public Game createGameReviews(List<GBReview> gbReviews) {
        return null;
    }
}
