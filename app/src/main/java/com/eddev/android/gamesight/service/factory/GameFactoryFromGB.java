package com.eddev.android.gamesight.service.factory;

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
import com.eddev.android.gamesight.model.Review;
import com.eddev.android.gamesight.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edison on 10/15/2016.
 */

public class GameFactoryFromGB {

    private static GameFactoryFromGB instance = new GameFactoryFromGB();

    public static GameFactoryFromGB getInstance() {
        return instance;
    }

    private GameFactoryFromGB() {
    }

    public Game createGamePreview(GBGame gBGame){
        List<ClassificationAttribute> classificationAttributes = getClassificationAttributes(gBGame);
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
                null,
                0D,
                null
        );
        return newGame;
    }

    public List<Game> createGamePreviewList(List<GBGame> gBGames){
        List<Game> newGamesList = new ArrayList<Game>();
        if(gBGames != null) {
            for (GBGame current : gBGames) {
                newGamesList.add(createGamePreview(current));
            }
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

        if(gBGame.getPlatforms() != null) {
            for (GBPlatform current : gBGame.getPlatforms()) {
                ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                        ClassificationAttribute.PLATFORM,
                        current.getName());
                classificationAttributes.add(classificationAttribute);
            }
        }

        if(gBGame.getGenres() != null) {
            for (GBGenre current : gBGame.getGenres()) {
                ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                        ClassificationAttribute.GENRE,
                        current.getName());
                classificationAttributes.add(classificationAttribute);
            }
        }

        if(gBGame.getPublishers() != null) {
            for (GBPublisher current : gBGame.getPublishers()) {
                ClassificationAttribute classificationAttribute = new ClassificationAttribute(current.getId(),
                        ClassificationAttribute.PUBLISHER,
                        current.getName());
                classificationAttributes.add(classificationAttribute);
            }
        }
        return classificationAttributes;
    }

    public List<Review> createGameReviews(List<GBReview> gbReviews) {
        List<Review> reviews = new ArrayList<>();
        if(gbReviews != null) {
            for (GBReview current : gbReviews) {
                Review newReview = new Review(0, current.getDeck(), Utility.getDateTime(current.getPublishDate()), current.getScore(), current.getReviewer());
                reviews.add(newReview);
            }
        }
        return reviews;
    }
}
