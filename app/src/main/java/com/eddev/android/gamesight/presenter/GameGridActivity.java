package com.eddev.android.gamesight.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.model.Game;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameGridActivity extends AppCompatActivity implements GameGridActivityFragment.Callback {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final String GAME_GRID_FRAGMENT_TAG = "GGFT";
    private static final String GAME_DETAIL_FRAGMENT_TAG = "GDFT";

    @Nullable
    @BindView(R.id.game_detail_pane)
    FrameLayout detailContainerFrameLayout;
    boolean mTwoPane;
    private String mCollection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mCollection = getIntent().getStringExtra(getString(R.string.collection_key));
            Bundle arguments = new Bundle();
            arguments.putString(getString(R.string.collection_key), mCollection);
            getSupportActionBar().setTitle(GiantBombUtility.getTitle(mCollection));
            GameGridActivityFragment gridFragment = new GameGridActivityFragment();
            gridFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game_grid_container, gridFragment, GAME_GRID_FRAGMENT_TAG)
                    .commit();

        }else{
            mCollection = savedInstanceState.getString(getString(R.string.collection_key));
            getSupportActionBar().setTitle(GiantBombUtility.getTitle(mCollection));
        }

        if (detailContainerFrameLayout != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.game_detail_pane, new GameDetailFragment(), GAME_DETAIL_FRAGMENT_TAG).
                        commit();
            }
        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.collection_key), mCollection);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(Game game) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.parcelable_game_key), game);
            arguments.putBoolean(getString(R.string.two_pane_key), mTwoPane);
            GameDetailFragment detailFragment = new GameDetailFragment();
            detailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.game_detail_pane, detailFragment, GAME_DETAIL_FRAGMENT_TAG)
                    .commit();
        } else {
            Intent detailsIntent = new Intent(this, GameDetailActivity.class);
            detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
            startActivity(detailsIntent);
        }
    }
}
