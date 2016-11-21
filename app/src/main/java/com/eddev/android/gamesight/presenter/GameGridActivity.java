package com.eddev.android.gamesight.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.Utility;

public class GameGridActivity extends AppCompatActivity {

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
            getSupportActionBar().setTitle(Utility.getTitle(mCollection));
            GameGridActivityFragment gridFragment = new GameGridActivityFragment();
            gridFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game_grid_container, gridFragment)
                    .commit();
        }else{
            mCollection = savedInstanceState.getString(getString(R.string.collection_key));
            getSupportActionBar().setTitle(Utility.getTitle(mCollection));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.collection_key), mCollection);
        super.onSaveInstanceState(outState);
    }

}
