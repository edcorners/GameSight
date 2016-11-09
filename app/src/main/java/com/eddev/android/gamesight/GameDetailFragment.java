package com.eddev.android.gamesight;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.model.Review;
import com.eddev.android.gamesight.model.Video;
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

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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
    @BindView(R.id.detail_platforms_text_view)
    TextView mPlatformsTextView;
    @BindView(R.id.detail_platforms_linear_layout)
    LinearLayout mPlatformsLinearLayout;
    @BindView(R.id.detail_reviews_layout)
    LinearLayout mReviewsLinearLayout;
    @BindView(R.id.detail_videos_linear_layout)
    LinearLayout mVideosLinearLayout;

    private boolean mGameLoaded = false;
    private boolean mReviewsLoaded = false;

    public GameDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, rootView);
        mIGameSearchService = new GiantBombSearchService(getContext());

        if(savedInstanceState != null){
            mGame = savedInstanceState.getParcelable("currentGame");
            mReviewsLoaded = true;
            mGameLoaded = true;
            updateGameView();
            updateReviewsView();
        }else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
                loadFullGame();
            }else{
                //TODO show error
            }
        }
        return rootView;
    }

    private void loadFullGame() {
        mIGameSearchService.findGameById(mGame.getId(), this);
        mIGameSearchService.findReviewsByGameId(mGame.getId(), this);
    }

    @Override
    public void onGameLoaded(Game game, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.copyBasicFields(game);// Reviews not included
            mGameLoaded = true;
            updateGameView();
        }else{
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameReviewLoaded(List<Review> reviews, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.setReviews(reviews);
            mReviewsLoaded = true;
            updateReviewsView();
        }else{
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGameView() {
        mCompletionTextView.setText(mGame.getCompletion()+"%");
        mDescriptionTextView.setText(mGame.getDescription());
        String publishers = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PUBLISHER));
        mPublisherTextView.setText(TextUtils.isEmpty(publishers) ? "Not available" : publishers);
        mReleaseDateTextView.setText(Utility.dateFormat.format(mGame.getReleaseDate()));
        String genres = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.GENRE));
        mGenreTextView.setText(TextUtils.isEmpty(genres) ? "Not available" : genres);
        String platforms = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PLATFORM));
        mPlatformsTextView.setText(TextUtils.isEmpty(platforms) ? "Not available" : platforms);

        updateVideosView();
    }

    private void updateVideosView() {
        List<Video> videos = mGame.getVideos();
        if(videos.size()>0) {
            for (final Video current : videos) {
                createVideoView(current);
            }
        }else{
            setEmptyListMessage(mVideosLinearLayout, "No videos available");
        }
    }

    private void createVideoView(final Video current) {
        TextView videoTitleTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_video, null, false);
        videoTitleTextView.setText(current.getName());
        videoTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                getContext().startActivity(openVideoIntent);
            }
        });
        Drawable playIcon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_play_circle_filled_black_24dp);
        playIcon.setBounds(0, 0, 60, 60);
        videoTitleTextView.setCompoundDrawables(playIcon, null, null, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            videoTitleTextView.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorAccent)));
        }
        mVideosLinearLayout.addView(videoTitleTextView);
    }

    private void updateReviewsView() {
        List<Review> reviews = mGame.getReviews();
        if(reviews.size() > 0) {
            for (Review current : reviews) {
                createReviewView(current);
            }
        }else{
            setEmptyListMessage(mReviewsLinearLayout, "No reviews available");
        }
    }

    private void createReviewView(Review current) {
        LinearLayout reviewItemLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_review, null, false);
        ProperRatingBar ratingBar = (ProperRatingBar) reviewItemLinearLayout.findViewById(R.id.review_rating_bar);
        ratingBar.setRating((int) current.getScore());
        TextView reviewContent = (TextView) reviewItemLinearLayout.findViewById(R.id.review_content_text_view);
        reviewContent.setText(current.getDescription());
        TextView reviewDate = (TextView) reviewItemLinearLayout.findViewById(R.id.review_date_text_view);
        reviewDate.setText(Utility.dateTimeFormat.format(current.getDate()));
        TextView reviewAuthor = (TextView) reviewItemLinearLayout.findViewById(R.id.review_author_text_view);
        reviewAuthor.setText(current.getReviewer());
        mReviewsLinearLayout.addView(reviewItemLinearLayout);
    }

    private void setEmptyListMessage(LinearLayout layout, String message) {
        TextView emptyList = new TextView(getContext());
        emptyList.setText(message);
        emptyList.setTextColor(ContextCompat.getColor(getContext(), R.color.emptyViewMessage));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_HORIZONTAL ;
        emptyList.setLayoutParams(params);
        layout.addView(emptyList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mGameLoaded & mReviewsLoaded){
            outState.putParcelable("currentGame", mGame);
        }
        super.onSaveInstanceState(outState);
    }

}
