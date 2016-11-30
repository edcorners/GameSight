package com.eddev.android.gamesight.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
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
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eddev.android.gamesight.AnalyticsApplication;
import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.LinkedHashSet;
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
    private static final int REVIEWS_LOADER = 1; //0 is taken by activity
    private static final int VIDEOS_LOADER = 2;
    private static final int CATTRIBUTES_LOADER = 3;

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

    @BindView(R.id.details_empty_text_view)
    TextView mEmptyTextView;

    @BindView(R.id.detail_publisher_layout)
    LinearLayout mPublisherLayout;
    @BindView(R.id.detail_rdate_layout)
    LinearLayout mReleaseDateLayout;
    @BindView(R.id.detail_genre_layout)
    LinearLayout mGenreLayout;
    @BindView(R.id.detail_platforms_text_layout)
    LinearLayout mPlatformsTextLayout;

    private boolean mGameLoaded = false;
    private boolean mReviewsLoaded = false;
    private boolean mVideosLoaded = false;

    private GameSightDatabaseService mGameSightDatabaseService;

    @BindView(R.id.toolbar_game_title_text_view)
    TextView mGameTitleTextView;
    @BindView(R.id.detail_backdrop)
    ImageView backdrop;
    @BindView(R.id.detail_platforms_linear_layout)
    LinearLayout mPlatformsLinearLayout;

    private boolean mTwoPane = false;
    private Tracker mTracker;

    public GameDetailFragment() {   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initActivityTransitions();
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
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        Bundle arguments = getArguments();
        if (arguments != null) {
            mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
            if(mGame != null && mGameSightDatabaseService.canRetrieveFromLocalCollection(mGame)) {
                loadFavoriteGame(savedInstanceState);
            }else if(mGame != null ){
                loadGameFromService(savedInstanceState);
            }
            initTwoPaneView(rootView, arguments);
        }else{
            mContentProgressBar.setVisibility(View.GONE);
        }
        return rootView;
    }


    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide(Gravity.TOP);
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getActivity().getWindow().setEnterTransition(transition);
            getActivity().getWindow().setReturnTransition(transition);
        }
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
            getActivity().getSupportLoaderManager().restartLoader(CATTRIBUTES_LOADER, null, this);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean(REVIEWS_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateReviewsView();
        }else {
            getActivity().getSupportLoaderManager().restartLoader(REVIEWS_LOADER, null, this);
        }

        if (savedInstanceState != null && savedInstanceState.getBoolean(VIDEOS_LOADED_KEY)) {
            mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
            updateVideosView();
        }else {
            getActivity().getSupportLoaderManager().restartLoader(VIDEOS_LOADER, null, this);
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
            for(int i=0;i<menu.size();i++) {
                if(menu.getItem(i).getItemId() == R.id.action_favorite) {
                    menu.getItem(i).setIcon(R.drawable.ic_favorite_white);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite && mGameLoaded) {
            if(!mGame.isFavorite()) {
                mGameSightDatabaseService.insertFavorite(mGame);
                GameReleaseNotification.scheduleNotification(getContext(), mGame);
                item.setIcon(R.drawable.ic_favorite_white);
                updateProgressCard();
            }else{
                mGameSightDatabaseService.removeFavorite(mGame);
                item.setIcon(R.drawable.ic_favorite_border_white);
                mGame.setCollection(Game.DISCOVER);
                mProgressCard.setVisibility(View.GONE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateProgressCard() {
        if(mGame.isInCollecion(Game.TRACKING)){
            mCollectonIcon.setImageResource(R.drawable.ic_location_searching_white);
            mProgressCard.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.trackingCardToolbarColor)));
            mCompletionSeekBar.setVisibility(View.GONE);
            Date expectedReleaseDate = mGame.getExpectedReleaseDate();
            String completionText = expectedReleaseDate != null ? String.format(getString(R.string.coming_out)," " + GiantBombUtility.shortDateFormat.format(expectedReleaseDate)):
                    getString(R.string.release_date_unknown);
            mCompletionTextView.setText(completionText);
            mProgressCard.setVisibility(View.VISIBLE);
            mProgressCard.setContentDescription(completionText);
        }else if (mGame.isInCollecion(Game.OWNED)){
            mCollectonIcon.setImageResource(R.drawable.ic_videogame_asset_white);
            mProgressCard.setCardBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.ownedCardToolbarColor)));
            mCompletionSeekBar.setVisibility(View.VISIBLE);
            mCompletionSeekBar.setProgress((int)mGame.getCompletion());
            String completionText = String.format(getString(R.string.completion_units_legend), (int)mGame.getCompletion());
            mCompletionTextView.setText(completionText);
            mProgressCard.setVisibility(View.VISIBLE);
            mProgressCard.setContentDescription(completionText);
        }

    }

    @Override
    public void onGameLoaded(Game game, String error) {
        if(TextUtils.isEmpty(error)){
            mGame.copyGBFetchedFields(game);// Reviews not included
            updateGameView();
            updateVideosView();
        }else{
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            mEmptyTextView.setVisibility(View.VISIBLE);
            mContentProgressBar.setVisibility(View.GONE);
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
    private void initTwoPaneView(View rootView, Bundle arguments) {
        mTwoPane = arguments.getBoolean(getString(R.string.two_pane_key));
        if(mTwoPane) {
            rootView.findViewById(R.id.detail_include_toolbar_image).setVisibility(mTwoPane ? View.VISIBLE : View.GONE);
            mGameTitleTextView.setText(mGame.getName());
            mGameTitleTextView.setContentDescription(mGame.getName());
            mGameTitleTextView.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(mGame.getImageUrl())
                    .placeholder(R.color.mainBackground)
                    .error(R.color.mainBackground)
                    .into(backdrop);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            mContentLinearLayout.setLayoutParams(layoutParams);
            mContentLinearLayout.setPadding(0, 0, 0, 0);
        }
    }

    private void createPlatformsView() {
        mPlatformsLinearLayout.removeAllViews();

        LinkedHashSet<Integer> platformsSet = new LinkedHashSet<>();
        for(int platform: mGame.getClassificationAttributeIds(ClassificationAttribute.PLATFORM)) {
            platformsSet.add(GiantBombUtility.getIconResourceForConsole(platform));
        }
        for(int platform: platformsSet) {
            if(platform != -1) {
                FrameLayout platformFrame = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_platform, null, false);
                ImageView platformIcon = (ImageView) platformFrame.findViewById(R.id.platform_icon_image_view);
                platformIcon.setImageResource(platform);
                mPlatformsLinearLayout.addView(platformFrame);
            }
        }
    }

    private void updateGameView() {
        if(mGame != null && isAdded()) {// isAdded avoids Fragment not attached to Activity exception
            mDescriptionTextView.setText(mGame.getDescription());
            mDescriptionTextView.setContentDescription(mGame.getDescription());

            String publishersCsv = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PUBLISHER));
            String publishersText = TextUtils.isEmpty(publishersCsv) ? getString(R.string.no_data_available) : publishersCsv;
            mPublisherTextView.setText(publishersText);
            mPublisherLayout.setContentDescription(getString(R.string.publisher_title)+". "+publishersText);

            Date releaseDate = mGame.getReleaseDate();
            String releaseDateText = releaseDate != null ? GiantBombUtility.shortDateFormat.format(releaseDate) : "Unknown";
            mReleaseDateTextView.setText(releaseDateText);
            mReleaseDateLayout.setContentDescription(getString(R.string.release_date_title)+". "+releaseDateText);

            String genresCsv = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.GENRE));
            String genresText = TextUtils.isEmpty(genresCsv) ? getString(R.string.no_data_available) : genresCsv;
            mGenreTextView.setText(genresText);
            mGenreLayout.setContentDescription(getString(R.string.genre_title)+". "+genresText);

            String platformsCsv = TextUtils.join(", ", mGame.getClassificationAttributeValues(ClassificationAttribute.PLATFORM));
            String platformsText = TextUtils.isEmpty(platformsCsv) ? getString(R.string.no_data_available) : platformsCsv;
            mPlatformsTextView.setText(platformsText);
            mPlatformsTextLayout.setContentDescription(getString(R.string.platforms_title)+". "+platformsText);


            mCompletionSeekBar.setOnSeekBarChangeListener(this);
            if (mGame.isFavorite()){
                updateProgressCard();
            }

            mContentLinearLayout.setVisibility(View.VISIBLE);
            mContentProgressBar.setVisibility(View.GONE);
            mGameLoaded = true;
            setMenuVisibility(mReviewsLoaded && mReviewsLoaded && mVideosLoaded);
        }
    }

    private void updateVideosView() {
        List<Video> videos = mGame.getVideos();
        if(isAdded()) {// isAdded avoids Fragment not attached to Activity exception
            mVideosLinearLayout.removeAllViews();
            if (videos.size() > 0) {
                for (final Video current : videos) {
                    createVideoView(current);
                }
            } else {
                setEmptyLayoutMessage(mVideosLinearLayout, getString(R.string.videos_empty));
            }
            mVideosProgressBar.setVisibility(View.GONE);
            mVideosLinearLayout.setVisibility(View.VISIBLE);
            mVideosLoaded = true;
            setMenuVisibility(mReviewsLoaded && mReviewsLoaded && mVideosLoaded);
        }
    }

    private void createVideoView(final Video current) {
        final Context context = getContext();
        if(context != null) {
            RelativeLayout videoRelativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_video, null, false);
            TextView videoTitleTextView = (TextView) videoRelativeLayout.findViewById(R.id.video_title_text_view);
            videoTitleTextView.setText(current.getName());
            videoTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                    context.startActivity(openVideoIntent);
                }
            });
            ImageView videoIcon = (ImageView) videoRelativeLayout.findViewById(R.id.item_video_icon);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(mGame.isInCollecion(Game.TRACKING)){
                    videoIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.trackingCardToolbarColor)));
                }else if(mGame.isInCollecion(Game.OWNED)){
                    videoIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.ownedCardToolbarColor)));
                }else{
                    videoIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.discoverCardToolbarColor)));
                }
            }
            videoTitleTextView.setContentDescription(String.format(getString(R.string.video_content_description), current.getName()));
            mVideosLinearLayout.addView(videoRelativeLayout);
        }
    }

    private void updateReviewsView() {
        mReviewsLinearLayout.removeAllViews();
        if(mGame != null && isAdded()) { // isAdded avoids Fragment not attached to Activity exception
            List<Review> reviews = mGame.getReviews();
            if (reviews.size() > 0) {
                for (Review current : reviews) {
                    createReviewView(current);
                }
            } else{
                setEmptyLayoutMessage(mReviewsLinearLayout, getString(R.string.reviews_empty));
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
        reviewDate.setText(GiantBombUtility.shortDateFormat.format(current.getDate()));
        TextView reviewAuthor = (TextView) reviewItemLinearLayout.findViewById(R.id.review_author_text_view);
        reviewAuthor.setText(current.getReviewer());
        reviewItemLinearLayout.setContentDescription(String.format(getString(R.string.review_content_description),current.getReviewer(), (int)current.getScore(), current.getDescription()));
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
                Log.d(LOG_TAG, "onCreateLoader CATTRIBUTES_LOADER");
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
                    Log.d(LOG_TAG, "LoadFinished CATTRIBUTES_LOADER ");
                    mGame.clearClassificationAttributes();
                    data.moveToPosition(-1); //Rewind cursor
                    //trailersLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        mGame.addClassificationAttribute(new ClassificationAttribute(data));
                    }
                    updateGameView();
                    if(mTwoPane){
                        createPlatformsView();
                    }
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
        mCompletionTextView.setText(progress+ getString(R.string.completion_units_legend));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mGameSightDatabaseService.updateProgress(mGame);
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, getString(R.string.setting_screen_name_text) + getString(R.string.detail_screen_name));
        mTracker.setScreenName(getString(R.string.detail_screen_name));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }
}
