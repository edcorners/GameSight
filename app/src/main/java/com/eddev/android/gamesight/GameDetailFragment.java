package com.eddev.android.gamesight;

import android.content.Context;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eddev.android.gamesight.data.GameSightDatabaseService;
import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.model.Review;
import com.eddev.android.gamesight.model.Video;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGameLoadedCallback;
import com.eddev.android.gamesight.service.callback.IGameReviewsLoadedCallback;

import java.util.Date;
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
    @BindView(R.id.detail_content_game)
    LinearLayout mContentLinearLayout;
    @BindView(R.id.detail_reviews_layout)
    LinearLayout mReviewsLinearLayout;
    @BindView(R.id.detail_videos_linear_layout)
    LinearLayout mVideosLinearLayout;

    @BindView(R.id.detail_videos_progress_bar)
    ProgressBar mVideosProgressBar;
    @BindView(R.id.detail_reviews_progress_bar)
    ProgressBar mReviewsProgressBar;
    @BindView(R.id.detail_content_progress_bar)
    ProgressBar mContentProgressBar;

    private boolean mGameLoaded = false;
    private boolean mReviewsLoaded = false;

    private GameSightDatabaseService mGameSightDatabaseService;

    public GameDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, rootView);
        setMenuVisibility(false);
        mIGameSearchService = new GiantBombSearchService(getContext());
        mGameSightDatabaseService = new GameSightDatabaseService(getContext());

        if(savedInstanceState != null && savedInstanceState.getBoolean("gameLoaded")){
            mGame = savedInstanceState.getParcelable("currentGame");
            updateGameView();

            if(savedInstanceState.getBoolean("reviewsLoaded")){
                updateReviewsView();
            }else{
                mIGameSearchService.findReviewsByGameId(mGame.getId(), this);
            }
        }else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
                mIGameSearchService.findGameById(mGame.getId(), this);
                mIGameSearchService.findReviewsByGameId(mGame.getId(), this);
            }else{
                //TODO show error
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mGameLoaded & mReviewsLoaded){
            outState.putParcelable("currentGame", mGame);
            outState.putBoolean("gameLoaded", mGameLoaded);
            outState.putBoolean("reviewsLoaded", mReviewsLoaded);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_game_detail, menu);
        /*if(mGameLoaded && mReviewsLoaded) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(true);
            }
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite && mGameLoaded) {
            mGameSightDatabaseService.insertFavorite(mGame);
            item.setIcon(R.drawable.ic_favorite_white);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameLoaded(Game game, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.copyBasicFields(game);// Reviews not included
            updateGameView();
        }else{
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGameReviewLoaded(List<Review> reviews, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.setReviews(reviews);
            updateReviewsView();
        }else{
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateGameView() {
        if(mGame != null) {
            mCompletionTextView.setText(mGame.getCompletion() + "%");
            mDescriptionTextView.setText(mGame.getDescription());
            String publishers = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PUBLISHER));
            mPublisherTextView.setText(TextUtils.isEmpty(publishers) ? "Not available" : publishers);
            Date releaseDate = mGame.getReleaseDate();
            mReleaseDateTextView.setText(releaseDate != null ? Utility.dateFormat.format(releaseDate) : "Unknown");
            String genres = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.GENRE));
            mGenreTextView.setText(TextUtils.isEmpty(genres) ? "Not available" : genres);
            String platforms = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PLATFORM));
            mPlatformsTextView.setText(TextUtils.isEmpty(platforms) ? "Not available" : platforms);

            mContentLinearLayout.setVisibility(View.VISIBLE);
            mContentProgressBar.setVisibility(View.GONE);
            mGameLoaded = true;
            setMenuVisibility(mReviewsLoaded && mReviewsLoaded);

            updateVideosView();
        }
    }

    private void updateVideosView() {
        List<Video> videos = mGame.getVideos();
        if(videos.size()>0) {
            for (final Video current : videos) {
                createVideoView(current);
            }
        }else{
            setEmptyLayoutMessage(mVideosLinearLayout, "No videos available");
        }
        mVideosProgressBar.setVisibility(View.GONE);
        mVideosLinearLayout.setVisibility(View.VISIBLE);
    }

    private void createVideoView(final Video current) {
        final Context context = getContext();
        if(context != null) {
            TextView videoTitleTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_video, null, false);
            videoTitleTextView.setText(current.getName());
            videoTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                    context.startActivity(openVideoIntent);
                }
            });
            Drawable playIcon = ContextCompat.getDrawable(getActivity(), R.drawable.ic_play_circle_filled_black);
            playIcon.setBounds(0, 0, 60, 60);
            videoTitleTextView.setCompoundDrawables(playIcon, null, null, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                videoTitleTextView.setCompoundDrawableTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorAccent)));
            }
            mVideosLinearLayout.addView(videoTitleTextView);
        }
    }

    private void updateReviewsView() {
        if(mGame != null) {
            List<Review> reviews = mGame.getReviews();
            if (reviews.size() > 0) {
                for (Review current : reviews) {
                    createReviewView(current);
                }
            } else {
                setEmptyLayoutMessage(mReviewsLinearLayout, "No reviews available");
            }
        }
        mReviewsLinearLayout.setVisibility(View.VISIBLE);
        mReviewsProgressBar.setVisibility(View.GONE);
        mReviewsLoaded = true;
        setMenuVisibility(mReviewsLoaded && mReviewsLoaded);
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

    private void setEmptyLayoutMessage(LinearLayout layout, String message) {
        Context context = getContext();
        if(context != null){
            TextView emptyList = new TextView(context);
            emptyList.setText(message);
            emptyList.setTextColor(ContextCompat.getColor(context, R.color.emptyViewMessage));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER_HORIZONTAL ;
            emptyList.setLayoutParams(params);
            layout.addView(emptyList);
        }
    }

}
