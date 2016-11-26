package com.eddev.android.gamesight.presenter;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.data.GameSightProvider;
import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String CURRENT_GAME_KEY = "currentGame";
    public static final String PLATFORMS_LOADED_KEY = "platformsLoaded";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final String LOG_TAG = GameDetailActivity.class.getSimpleName();

    @BindView(R.id.details_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detail_backdrop)
    ImageView backdrop;
    @BindView(R.id.detail_platforms_linear_layout)
    LinearLayout mPlatformsLinearLayout;

    private Game mGame;
    private boolean mPlatformsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        mGame = getIntent().getParcelableExtra(getString(R.string.parcelable_game_key));

        if(mGame !=null) {
            if (savedInstanceState == null) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(getString(R.string.parcelable_game_key), mGame);
                GameDetailFragment detailFragment = new GameDetailFragment();
                detailFragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.game_detail_container, detailFragment)
                        .commit();

                if (mGame.isFavorite()) {
                    getSupportLoaderManager().initLoader(0, null, this);
                }else{
                    createPlatformsView();
                }
            } else {
                if (mGame.isFavorite() && !savedInstanceState.getBoolean(PLATFORMS_LOADED_KEY)) {
                    getSupportLoaderManager().initLoader(0, null, this);
                }else {
                    mGame = savedInstanceState.getParcelable(CURRENT_GAME_KEY);
                    createPlatformsView();
                }
            }

            collapsingToolbarLayout.setTitle(mGame.getName());
            collapsingToolbarLayout.setContentDescription(mGame.getName());
            boolean isRightToLeft = getResources().getBoolean(R.bool.is_right_to_left);
            if(isRightToLeft){
                collapsingToolbarLayout.setExpandedTitleGravity(Gravity.RIGHT | Gravity.BOTTOM);
                collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.RIGHT);
            }
            Picasso.with(this)
                    .load(mGame.getImageUrl())
                    .placeholder(R.color.mainBackground)
                    .error(R.color.mainBackground)
                    .into(backdrop, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) backdrop.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette);
                                }
                            });
                        }

                        @Override
                        public void onError() {
                        }
                    });
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
                FrameLayout platformFrame = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.item_platform, null, false);
                ImageView platformIcon = (ImageView) platformFrame.findViewById(R.id.platform_icon_image_view);
                platformIcon.setImageResource(platform);
                mPlatformsLinearLayout.addView(platformFrame);
            }
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_GAME_KEY, mGame);
        outState.putBoolean(PLATFORMS_LOADED_KEY, mPlatformsLoaded);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader ");
        CursorLoader cursorLoader = new CursorLoader(this,
                GameSightProvider.ClassificationAttributes.withGameId(String.valueOf(mGame.getId())),
                ClassificationAttribute.CLASSIFICATION_ATTR_PROJECTION,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, "LoadFinished  ");
        mGame.clearClassificationAttributes();
        data.moveToPosition(-1); //Rewind cursor
        while (data.moveToNext()) {
            mGame.addClassificationAttribute(new ClassificationAttribute(data));
        }
        createPlatformsView();
        mPlatformsLoaded = true;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}
