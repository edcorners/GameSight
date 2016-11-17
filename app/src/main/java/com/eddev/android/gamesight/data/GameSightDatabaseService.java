package com.eddev.android.gamesight.data;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.util.Log;

import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.model.Review;
import com.eddev.android.gamesight.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ed on 11/12/16.
 */

public class GameSightDatabaseService {
    private Context mContext;
    private final String LOG_TAG = GameSightDatabaseService.class.getSimpleName();

    /**
     * Basic constructor
     */
    public GameSightDatabaseService(Context context) {
        this.mContext = context;
    }

    public void insertFavorite(Game game){
        game.calculateCollection();
        upsertGame(game);
        ArrayList<ContentProviderOperation> batchUpserts = upsertVideos(game.getVideos(), game.getId());
        batchUpserts.addAll(upsertReviews(game.getReviews(), game.getId()));
        batchUpserts.addAll(upsertClassificationAttributes(game.getClassificationAttributes()));
        try {
            ContentProviderResult[] results = mContext.getContentResolver().applyBatch(GameSightProvider.AUTHORITY, batchUpserts);
            Log.v(LOG_TAG, results.toString());
        } catch (RemoteException | OperationApplicationException e) {
            Log.e(LOG_TAG, "Error applying batch videos update", e);
        }
        deleteInsertClassificationByGame(game);
    }

    private void upsertGame(Game game) {
        int gameId = game.getId();
        Cursor cursor = mContext.getContentResolver().query(GameSightProvider.Games.withGameId(gameId),
                null,
                null,
                null,
                null);
        if (cursor != null) {
            ContentValues gameCV = game.toContentValues();
            if (cursor.getCount() > 0) {
                mContext.getContentResolver().update(GameSightProvider.Games.CONTENT_URI, gameCV, GameColumns.GAME_ID + "="+ gameId, null);
            } else {
                mContext.getContentResolver().insert(GameSightProvider.Games.CONTENT_URI, gameCV);
            }
            cursor.close();
        }
    }

    private ArrayList<ContentProviderOperation> upsertVideos(List<Video> videos, int gameId) {
        ArrayList<ContentProviderOperation> batchUpserts = new ArrayList<>();
        ContentProviderOperation.Builder builder = null;

        if(videos != null){
            for (Video current : videos) {
                int videoId = current.getId();
                Cursor cursor = mContext.getContentResolver().query(GameSightProvider.Videos.withGameId(gameId),
                        null,
                        null,
                        null,
                        null);
                if (cursor != null) {
                    ContentValues currentCV = current.toContentValues(gameId);
                    if (cursor.getCount() > 0) {
                        builder = ContentProviderOperation.newUpdate(GameSightProvider.Videos.withGameId(videoId)).withValues(currentCV);
                    } else {
                        builder = ContentProviderOperation.newInsert(GameSightProvider.Videos.CONTENT_URI).withValues(currentCV);
                    }
                    batchUpserts.add(builder.build());
                    cursor.close();
                }
            }
        }
        return batchUpserts;

    }

    private ArrayList<ContentProviderOperation> upsertReviews(List<Review> reviews, int gameId) {
        ArrayList<ContentProviderOperation> batchUpserts = new ArrayList<>();
        ContentProviderOperation.Builder builder = null;

        if(reviews != null){
            for (Review current : reviews) {
                int reviewId = current.getId();
                Cursor cursor = mContext.getContentResolver().query(GameSightProvider.Reviews.withGameId(gameId),
                        null,
                        null,
                        null,
                        null);
                if (cursor != null) {
                    ContentValues currentCV = current.toContentValues(gameId);
                    if (cursor.getCount() > 0) {
                        builder = ContentProviderOperation.newUpdate(GameSightProvider.Reviews.withGameId(reviewId)).withValues(currentCV);
                    } else {
                        builder = ContentProviderOperation.newInsert(GameSightProvider.Reviews.CONTENT_URI).withValues(currentCV);
                    }
                    batchUpserts.add(builder.build());
                    cursor.close();
                }
            }
        }
        return batchUpserts;
    }

    private  ArrayList<ContentProviderOperation> upsertClassificationAttributes(List<ClassificationAttribute> classificationAttributes) {
        ArrayList<ContentProviderOperation> batchUpserts = new ArrayList<>();
        ContentProviderOperation.Builder builder = null;

        if(classificationAttributes != null){
            for (ClassificationAttribute current : classificationAttributes) {
                int classificationId = current.getId();
                Cursor cursor = mContext.getContentResolver().query(GameSightProvider.ClassificationAttributes.withClassificationAttributeId(classificationId),
                        null,
                        null,
                        null,
                        null);
                if (cursor != null) {
                    ContentValues currentCV = current.toContentValues(classificationId);
                    if (cursor.getCount() > 0) {
                        builder = ContentProviderOperation.newUpdate(GameSightProvider.ClassificationAttributes.withClassificationAttributeId(classificationId)).withValues(currentCV);
                    } else {
                        builder = ContentProviderOperation.newInsert(GameSightProvider.ClassificationAttributes.CONTENT_URI).withValues(currentCV);
                    }
                    batchUpserts.add(builder.build());
                    cursor.close();
                }
            }
        }
        return batchUpserts;
    }

    private void deleteInsertClassificationByGame(Game game) {
        ArrayList<ContentProviderOperation> batchUpserts = new ArrayList<>();
        ContentProviderOperation.Builder builder = null;
        ContentValues[] contentValues = game.getClassificationByGameContentValues();
        if(contentValues != null){
            mContext.getContentResolver().delete(GameSightProvider.ClassificationByGame.CONTENT_URI, ClassificationByGameColumns.GAME_ID + " = "+game.getId(), null);
            mContext.getContentResolver().bulkInsert(GameSightProvider.ClassificationByGame.CONTENT_URI, contentValues);
        }
    }

    public void removeFavorite(Game mGame) {
        mContext.getContentResolver().delete(GameSightProvider.Games.CONTENT_URI, GameColumns.GAME_ID +" = "+ mGame.getId(), null);
    }

    public void updateProgress(Game mGame) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GameColumns.COMPLETION, mGame.getCompletion());
        mContext.getContentResolver().update(GameSightProvider.Games.CONTENT_URI,
                    contentValues,
                    GameColumns.GAME_ID +" = "+ mGame.getId(), null);
    }
}
