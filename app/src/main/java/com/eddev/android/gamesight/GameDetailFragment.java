package com.eddev.android.gamesight;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eddev.android.gamesight.data.GameSightDatabaseService;
import com.eddev.android.gamesight.data.GameSightProvider;
import com.eddev.android.gamesight.data.ReviewColumns;
import com.eddev.android.gamesight.data.VideoColumns;
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
public class GameDetailFragment extends Fragment implements IGameLoadedCallback,
        IGameReviewsLoadedCallback, LoaderManager.LoaderCallbacks<Cursor>,SeekBar.OnSeekBarChangeListener{

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final String LOG_TAG = GameDetailFragment.class.getSimpleName();
    private static final int REVIEWS_LOADER = 0;
    private static final int VIDEOS_LOADER = 1;
    private static final int CATTRIBUTES_LOADER = 2;

    public static final String GAME_LOADED_KEY = "gameLoaded";
    public static final String CURRENT_GAME_KEY = "currentGame";
    public static final String REVIEWS_LOADED_KEY = "reviewsLoaded";
    private static final String VIDEOS_LOADED_KEY = "videosLoaded";

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

    @BindView(R.id.detail_seek_bar)
    AppCompatSeekBar mCompletionSeekBar;
    @BindView(R.id.detail_progress_card)
    CardView mProgressCard;
    @BindView(R.id.detail_collection_icon)
    ImageView mCollectonIcon;


    private boolean mGameLoaded = false;
    private boolean mReviewsLoaded = false;
    private boolean mVideosLoaded = false;

    private GameSightDatabaseService mGameSightDatabaseService;

    public GameDetailFragment() {   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, rootView);
        setMenuVisibility(false);
        mIGameSearchService = new GiantBombSearchService(getContext());
        mGameSightDatabaseService = new GameSightDatabaseService(getContext());

        Bundle arguments = getArguments();
        if (arguments != null) {
            mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
            if(mGame.isFavorite()) {
                loadFavoriteGame(savedInstanceState);
            }else {
                loadGameFromService(savedInstanceState);
            }
        }

        return rootView;
    }

    private void loadGameFromService(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(GAME_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateGameView();
            updateVideosView();
        }else {
            mIGameSearchService.findGameById(mGame.getId(), this);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean(REVIEWS_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateReviewsView();
        }else {
            mIGameSearchService.findReviewsByGameId(mGame.getId(), this);
        }
    }

    private void loadFavoriteGame(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.getBoolean(GAME_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateGameView();
        }else {
            getActivity().getSupportLoaderManager().initLoader(CATTRIBUTES_LOADER, null, this);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean(REVIEWS_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateReviewsView();
        }else {
            getActivity().getSupportLoaderManager().initLoader(REVIEWS_LOADER, null, this);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean(VIDEOS_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateVideosView();
        }else {
            getActivity().getSupportLoaderManager().initLoader(VIDEOS_LOADER, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mGameLoaded){
            outState.putParcelable(CURRENT_GAME_KEY, mGame);
            outState.putBoolean(GAME_LOADED_KEY, mGameLoaded);
        }
        if(mReviewsLoaded){
            outState.putParcelable(CURRENT_GAME_KEY, mGame);
            outState.putBoolean(REVIEWS_LOADED_KEY, mReviewsLoaded);
        }
        if(mVideosLoaded){
            outState.putParcelable(CURRENT_GAME_KEY, mGame);
            outState.putBoolean(VIDEOS_LOADED_KEY, mVideosLoaded);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_game_detail, menu);
        if(mGame.isFavorite()) {
            menu.getItem(1).setIcon(R.drawable.ic_favorite_white);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite && mGameLoaded) {
            if(!mGame.isFavorite()) {
                mGameSightDatabaseService.insertFavorite(mGame);
                item.setIcon(R.drawable.ic_favorite_white);
                updateProgressCard();
            }else{
                mGameSightDatabaseService.removeFavorite(mGame);
                item.setIcon(R.drawable.ic_favorite_border_white);
                mProgressCard.setVisibility(View.GONE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateProgressCard() {
        if(mGame.isInCollecion(Game.TRACKING)){
            mCollectonIcon.setImageResource(R.drawable.ic_location_searching_white);
            mProgressCard.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.tracking_card_toolbar )));
            mCompletionSeekBar.setVisibility(View.GONE);
            Date expectedReleaseDate = mGame.getExpectedReleaseDate();
            String completionText = expectedReleaseDate != null ? " Coming out "+Utility.shortDateFormat.format(expectedReleaseDate):
                    "Release date unknown";
            mCompletionTextView.setText(completionText);
        }else{
            mCollectonIcon.setImageResource(R.drawable.ic_videogame_asset_white);
            mProgressCard.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.owned_card_toolbar )));
            mCompletionSeekBar.setVisibility(View.VISIBLE);
            mCompletionSeekBar.setProgress((int)mGame.getCompletion());
            mCompletionTextView.setText((int)mGame.getCompletion() + "% complete");
        }
        mProgressCard.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGameLoaded(Game game, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.copyGBFetchedFields(game);// Reviews not included
            updateGameView();
            updateVideosView();
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

    /**
     * UI Init
     */

    private void updateGameView() {
        if(mGame != null) {
            mDescriptionTextView.setText(mGame.getDescription());
            String publishers = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PUBLISHER));
            mPublisherTextView.setText(TextUtils.isEmpty(publishers) ? "Not available" : publishers);
            Date releaseDate = mGame.getReleaseDate();
            mReleaseDateTextView.setText(releaseDate != null ? Utility.shortDateFormat.format(releaseDate) : "Unknown");
            String genres = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.GENRE));
            mGenreTextView.setText(TextUtils.isEmpty(genres) ? "Not available" : genres);
            String platforms = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PLATFORM));
            mPlatformsTextView.setText(TextUtils.isEmpty(platforms) ? "Not available" : platforms);

            mCompletionSeekBar.setOnSeekBarChangeListener(this);
            if (mGame.isFavorite()){
                updateProgressCard();
            }

            /*mPlatformsLinearLayout.removeAllViews();
            for(int platform: mGame.getClassificationAttributeIds(ClassificationAttribute.PLATFORM)) {
                ImageView platformIcon = new ImageView(getActivity());
                platformIcon.setImageResource(Utility.getIconResourceForConsole(platform));
                mPlatformsLinearLayout.addView(platformIcon);
            }*/

            mContentLinearLayout.setVisibility(View.VISIBLE);
            mContentProgressBar.setVisibility(View.GONE);
            mGameLoaded = true;
            setMenuVisibility(mReviewsLoaded && mReviewsLoaded && mVideosLoaded);
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
        mVideosLoaded = true;
        setMenuVisibility(mReviewsLoaded && mReviewsLoaded && mVideosLoaded);
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
        setMenuVisibility(mReviewsLoaded && mReviewsLoaded && mVideosLoaded);
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

    /**
     *  Loaders
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;

        switch (id) {
            case REVIEWS_LOADER:
                Log.d(LOG_TAG, "onCreateLoader TRACKING_LOADER");
                cursorLoader = new CursorLoader(getActivity(),
                        GameSightProvider.Reviews.CONTENT_URI,
                        Review.REVIEW_PROJECTION,
                        ReviewColumns.GAME_ID + "=?",
                        new String[]{String.valueOf(mGame.getId())},
                        null);
                break;
            case VIDEOS_LOADER:
                Log.d(LOG_TAG, "onCreateLoader OWNED_LOADER");
                cursorLoader = new CursorLoader(getActivity(),
                        GameSightProvider.Videos.CONTENT_URI,
                        Video.VIDEO_PROJECTION,
                        VideoColumns.GAME_ID + "=?",
                        new String[]{String.valueOf(mGame.getId())},
                        null);
                break;
            case CATTRIBUTES_LOADER:
                Log.d(LOG_TAG, "onCreateLoader OWNED_LOADER");
                cursorLoader = new CursorLoader(getActivity(),
                        GameSightProvider.ClassificationAttributes.withGameId(String.valueOf(mGame.getId())),
                        ClassificationAttribute.CLASSIFICATION_ATTR_PROJECTION,
                        null,
                        null,
                        null);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null ) {
            switch (loader.getId()) {
                case REVIEWS_LOADER:
                    Log.d(LOG_TAG, "LoadFinished TRACKING_LOADER ");
                    data.moveToPosition(-1); //Rewind cursor
                    mGame.clearReviews();
                    mReviewsLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        mGame.addReview(new Review(data));
                    }
                    updateReviewsView();
                    mReviewsLoaded = true;
                    break;
                case VIDEOS_LOADER:
                    Log.d(LOG_TAG, "LoadFinished OWNED_LOADER ");
                    mGame.clearVideos();
                    data.moveToPosition(-1); //Rewind cursor
                    mVideosLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        mGame.addVideo(new Video(data));
                    }
                    updateVideosView();
                    mVideosLoaded = true;
                    break;
                case CATTRIBUTES_LOADER:
                    Log.d(LOG_TAG, "LoadFinished OWNED_LOADER ");
                    mGame.clearClassificationAttributes();
                    data.moveToPosition(-1); //Rewind cursor
                    //trailersLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        mGame.addClassificationAttribute(new ClassificationAttribute(data));
                    }
                    updateGameView();
                    mGameLoaded = true;
                    break;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {  }

    /**
     * Seek bar listener
     * */

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mGame.setCompletion(progress);
        mCompletionTextView.setText(progress+"% complete");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mGameSightDatabaseService.updateProgress(mGame);
    }
}
