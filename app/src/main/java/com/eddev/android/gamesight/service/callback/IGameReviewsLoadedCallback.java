package com.eddev.android.gamesight.service.callback;

import com.eddev.android.gamesight.model.Review;

import java.util.List;

/**
 * Created by Edison on 10/22/2016.
 */

public interface IGameReviewsLoadedCallback {
    void onGameReviewLoaded(List<Review> reviews);
}
