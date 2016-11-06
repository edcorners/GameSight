package com.eddev.android.gamesight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.model.Review;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGameReviewsLoadedCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameDetailFragment extends Fragment implements IGameLoadedCallback, IGameReviewsLoadedCallback{

    private Game mGame;
    private IGameSearchService mIGameSearchService;

    @BindView(R.id.detail_completion_text_view)
    TextView mCompletionTextView;
    @BindView(R.id.detail_description_text_view)
    TextView mDescriptionTextView;
    @BindView(R.id.detail_publisher_text_view)
    TextView mPublisherTextView;
    @BindView(R.id.detail_release_date_text_view)
    TextView mReleaseDateTextView;
    @BindView(R.id.detail_genre_text_view)
    TextView mGenreTextView;
    @BindView(R.id.detail_platforms_linear_layout)
    LinearLayout mPlatformsLinearLayout;
    @BindView(R.id.detail_reviews_layout)
    LinearLayout mReviewsLinearLayout;
    @BindView(R.id.detail_videos_linear_layout)
    LinearLayout mVideosLinearLayout;

    public GameDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, rootView);
        mIGameSearchService = new GiantBombSearchService(getContext());

        Bundle arguments = getArguments();
        if (arguments != null) {
            mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
            loadFullGame();
        }
        return rootView;
    }

    private void loadFullGame() {
        mIGameSearchService.findGameById(mGame.getId(), this);
        mIGameSearchService.findReviewsByGameId(mGame.getId(), this);
    }

    @Override
    public void onGameLoaded(Game game) {
        mGame.copyBasicFields(game);// Reviews not included
        updateGameView();
    }

    @Override
    public void onGameReviewLoaded(List<Review> reviews) {
        mGame.setReviews(reviews);
        updateReviewsView(reviews);
    }

    private void updateGameView() {
        mCompletionTextView.setText(mGame.getCompletion()+"%");
        mDescriptionTextView.setText(mGame.getDescription());
        String publishers = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PUBLISHER));
        mPublisherTextView.setText(TextUtils.isEmpty(publishers) ? "Not available" : publishers);
        mReleaseDateTextView.setText(Utility.dateFormat.format(mGame.getReleaseDate()));
        String genres = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.GENRE));
        mGenreTextView.setText(TextUtils.isEmpty(genres) ? "Not available" : genres);
    }

    private void updateReviewsView(List<Review> reviews) {
        for(Review current : reviews) {
            LinearLayout reviewItemLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_review, null, false);
            ProperRatingBar ratingBar = (ProperRatingBar) reviewItemLinearLayout.findViewById(R.id.review_rating_bar);
            ratingBar.setRating((int)current.getScore());
            TextView reviewContent = (TextView) reviewItemLinearLayout.findViewById(R.id.review_content_text_view);
            reviewContent.setText(current.getDescription());
            TextView reviewDate = (TextView) reviewItemLinearLayout.findViewById(R.id.review_date_text_view);
            reviewDate.setText(Utility.dateTimeFormat.format(current.getDate()));
            TextView reviewAuthor = (TextView) reviewItemLinearLayout.findViewById(R.id.review_author_text_view);
            reviewAuthor.setText(current.getReviewer());
            mReviewsLinearLayout.addView(reviewItemLinearLayout);
        }

    }

}
