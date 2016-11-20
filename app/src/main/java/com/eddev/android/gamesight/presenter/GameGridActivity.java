package com.eddev.android.gamesight.presenter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.presenter.adapter.GridRecyclerViewAdapter;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameGridActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, IGamesLoadedCallback{

    public static final int DISCOVER_RESULTS_LIMIT = 15;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.grid_recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    @BindView(R.id.grid_progress_bar)
    ProgressBar mProgressBar;

    private IGameSearchService mIGameSearchService;

    private List<Game> mGames = new ArrayList<>();
    private String mCollection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        if(TextUtils.isEmpty(mCollection)) {
            mCollection = getIntent().getStringExtra(getString(R.string.collection_key));
        }

        if(mCollection.equals(Game.DISCOVER)){
            mIGameSearchService = new GiantBombSearchService(this);
            mIGameSearchService.fetchUpcomingGamesPreview(this, DISCOVER_RESULTS_LIMIT);
        }

        //mRecyclerViewLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

    }

    @Override
    public void onGamesLoaded(List<Game> games, String error) {
        if(!TextUtils.isEmpty(error)){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }else {
            mGames = games;
            //mDiscoverGamesLoaded = true;
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        mRecyclerViewAdapter = new GridRecyclerViewAdapter(mGames, this, new GridRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Intent detailsIntent = new Intent(getApplicationContext(), GameDetailActivity.class);
                detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
                startActivity(detailsIntent);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
