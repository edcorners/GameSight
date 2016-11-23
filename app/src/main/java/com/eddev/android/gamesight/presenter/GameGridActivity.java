package com.eddev.android.gamesight.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.GiantBombUtility;

public class GameGridActivity extends AppCompatActivity {

    public static final String GAME_GRID_FRAGMENT_TAG = "GGFT";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private String mCollection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.collection_key), mCollection);
        super.onSaveInstanceState(outState);
    }

}
