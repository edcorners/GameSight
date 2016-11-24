package com.eddev.android.gamesight.presenter;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.data.GameColumns;
import com.eddev.android.gamesight.data.GameSightProvider;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.presenter.adapter.CardRecyclerViewAdapter;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, IGamesLoadedCallback{

    private static final int TRACKING_LOADER = 0;
    private static final int OWNED_LOADER = 1;
    public static final String DISCOVER_GAMES_LOADED_KEY = "discoverGamesLoaded";
    public static final String DISCOVER_GAMES_KEY = "discoverGames";
    public static final String TRACKING_GAMES_KEY = "trackingGames";
    public static final String TRACKING_GAMES_LOADED_KEY = "trackingGamesLoaded";
    public static final String OWNED_GAMES_KEY = "ownedGames";
    public static final String OWNED_GAMES_LOADED_KEY = "ownedGamesLoaded";
    public static final int DISCOVER_RESULTS_LIMIT = 8;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.discover_card_recycler_view)
    RecyclerView mDiscoverCardRecyclerView;
    private RecyclerView.Adapter mDiscoverCardAdapter;
    private RecyclerView.LayoutManager mDiscoverCardLayoutManager;
    @BindView(R.id.d_card_toolbar)
    Toolbar mDCardToolbar;
    @BindView(R.id.d_card_progress_bar)
    ProgressBar mDCardProgressBar;
    private List<Game> mDiscoverGames = new ArrayList<>();
    private boolean mDiscoverGamesLoaded = false;

    @BindView(R.id.tracking_card_recycler_view)
    RecyclerView mTrackingCardRecyclerView;
    private RecyclerView.Adapter mTrackingCardAdapter;
    private RecyclerView.LayoutManager mTrackingCardLayoutManager;
    @BindView(R.id.t_card_toolbar)
    Toolbar mTCardToolbar;
    @BindView(R.id.t_card_progress_bar)
    ProgressBar mTCardProgressBar;
    private List<Game> mTrackingGames = new ArrayList<>();
    private boolean mTrackingGamesLoaded = false;

    @BindView(R.id.owned_card_recycler_view)
    RecyclerView mOwnedCardRecyclerView;
    private RecyclerView.Adapter mOwnedCardAdapter;
    private RecyclerView.LayoutManager mOwnedCardLayoutManager;
    @BindView(R.id.o_card_toolbar)
    Toolbar mOCardToolbar;
    @BindView(R.id.o_card_progress_bar)
    ProgressBar mOCardProgressBar;
    private List<Game> mOwnedGames = new ArrayList<>();
    private boolean mOwnedGamesLoaded = false;

    private IGameSearchService mIGameSearchService;

    /**
     * Activity lifecycle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        ButterKnife.bind(this);

        initDiscoverCard();
        initTrackingCard();
        initOwnedCard();
        loadDiscoverData(savedInstanceState);
        loadTrackingData(savedInstanceState);
        loadOwnedData(savedInstanceState);

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(ContextCompat.getDrawable(this,R.drawable.ic_gamepad_white));
    }

    private void loadOwnedData(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getBoolean(OWNED_GAMES_LOADED_KEY)){
            mOwnedGamesLoaded = true;
            mOwnedGames = savedInstanceState.getParcelableArrayList(OWNED_GAMES_KEY);
            initOwnedCardRecyclerView();
        }else{
            getSupportLoaderManager().initLoader(OWNED_LOADER, null, this);
        }
    }

    private void loadTrackingData(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getBoolean(TRACKING_GAMES_LOADED_KEY)){
            mTrackingGamesLoaded = true;
            mTrackingGames = savedInstanceState.getParcelableArrayList(TRACKING_GAMES_KEY);
            initTrackingCardRecyclerView();
        }else{
            getSupportLoaderManager().initLoader(TRACKING_LOADER, null, this);
        }
    }

    private void loadDiscoverData(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getBoolean(DISCOVER_GAMES_LOADED_KEY)){
            mDiscoverGamesLoaded = true;
            mDiscoverGames = savedInstanceState.getParcelableArrayList(DISCOVER_GAMES_KEY);
            initDiscoverCardRecyclerView();
        }else{
            mIGameSearchService = new GiantBombSearchService(this);
            mIGameSearchService.fetchUpcomingGamesPreview(this, DISCOVER_RESULTS_LIMIT);
        }
    }

    private void initOwnedCard() {
        mOwnedCardRecyclerView.setHasFixedSize(true);
        mOwnedCardLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mOwnedCardRecyclerView.setLayoutManager(mOwnedCardLayoutManager);
        mOCardToolbar.setLogo(ContextCompat.getDrawable(this, R.drawable.ic_videogame_asset_white));
        mOCardToolbar.inflateMenu(R.menu.menu_owned_card);
        mOCardToolbar.setTitle("Grid View");
        mOCardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_o_grid_view) {
                    Intent gridIntent = new Intent(getApplicationContext(), GameGridActivity.class);
                    gridIntent.putExtra(getString(R.string.collection_key), Game.OWNED);
                    startActivity(gridIntent);
                }

                return true;
            }
        });
    }

    private void initTrackingCard() {
        mTrackingCardRecyclerView.setHasFixedSize(true);
        mTrackingCardLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrackingCardRecyclerView.setLayoutManager(mTrackingCardLayoutManager);
        mTCardToolbar.setLogo(ContextCompat.getDrawable(this, R.drawable.ic_location_searching_white));
        mTCardToolbar.inflateMenu(R.menu.menu_tracking_card);
        mTCardToolbar.setTitle("Grid View");
        mTCardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_t_grid_view) {
                    Intent gridIntent = new Intent(getApplicationContext(), GameGridActivity.class);
                    gridIntent.putExtra(getString(R.string.collection_key), Game.TRACKING);
                    startActivity(gridIntent);
                }

                return true;
            }
        });
    }

    private void initDiscoverCard() {
        mDiscoverCardRecyclerView.setHasFixedSize(true);
        mDiscoverCardLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDiscoverCardRecyclerView.setLayoutManager(mDiscoverCardLayoutManager);
        mDCardToolbar.setLogo(ContextCompat.getDrawable(this, R.drawable.ic_find_in_page_white));
        mDCardToolbar.inflateMenu(R.menu.menu_discover_card);
        mDCardToolbar.setTitle("Grid View");
        mDCardToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_d_grid_view) {
                    Intent gridIntent = new Intent(getApplicationContext(), GameGridActivity.class);
                    gridIntent.putExtra(getString(R.string.collection_key), Game.DISCOVER);
                    startActivity(gridIntent);
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mDiscoverGamesLoaded) {
            outState.putParcelableArrayList(DISCOVER_GAMES_KEY, (ArrayList<? extends Parcelable>) mDiscoverGames);
            outState.putBoolean(DISCOVER_GAMES_LOADED_KEY, mDiscoverGamesLoaded);
        }
        if(mTrackingGamesLoaded){
            outState.putParcelableArrayList(TRACKING_GAMES_KEY, (ArrayList<? extends Parcelable>) mTrackingGames);
            outState.putBoolean(TRACKING_GAMES_LOADED_KEY, mTrackingGamesLoaded);
        }
        if(mOwnedGamesLoaded){
            outState.putParcelableArrayList(OWNED_GAMES_KEY, (ArrayList<? extends Parcelable>) mOwnedGames);
            outState.putBoolean(OWNED_GAMES_LOADED_KEY, mOwnedGamesLoaded);
        }
        super.onSaveInstanceState(outState);
    }

    /**
    * Loader
    */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;

        switch (id) {
            case TRACKING_LOADER:
                Log.d(LOG_TAG, "onCreateLoader TRACKING_LOADER");
                cursorLoader = new CursorLoader(this,
                        GameSightProvider.Games.CONTENT_URI,
                        Game.GAME_PROJECTION,
                        GameColumns.COLLECTION + "=?",
                        new String[]{Game.TRACKING},
                        null);
                break;
            case OWNED_LOADER:
                Log.d(LOG_TAG, "onCreateLoader OWNED_LOADER");
                cursorLoader = new CursorLoader(this,
                        GameSightProvider.Games.CONTENT_URI,
                        Game.GAME_PROJECTION,
                        GameColumns.COLLECTION + "=?",
                        new String[]{Game.OWNED},
                        null);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null ) {
            switch (loader.getId()) {
                case TRACKING_LOADER:
                    Log.d(LOG_TAG, "LoadFinished TRACKING_LOADER ");
                    data.moveToPosition(-1); //Rewind cursor
                    mTrackingGames.clear();
                    //reviewsLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        Game current = new Game(data);
                        mTrackingGames.add(current);
                    }
                    initTrackingCardRecyclerView();
                    mTrackingGamesLoaded = true;
                    break;
                case OWNED_LOADER:
                    Log.d(LOG_TAG, "LoadFinished OWNED_LOADER ");
                    mOwnedGames.clear();
                    data.moveToPosition(-1); //Rewind cursor
                    //trailersLinearLayout.removeAllViews();
                    while (data.moveToNext()) {
                        Game current = new Game(data);
                        mOwnedGames.add(current);
                    }
                    initOwnedCardRecyclerView();
                    mOwnedGamesLoaded = true;
                    break;
            }
        }
    }

    private void initOwnedCardRecyclerView() {
        mOwnedCardAdapter = new CardRecyclerViewAdapter(mOwnedGames, this, new CardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Intent detailsIntent = new Intent(getApplicationContext(), GameDetailActivity.class);
                detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
                startActivity(detailsIntent);
            }
        });
        mOwnedCardRecyclerView.setAdapter(mOwnedCardAdapter);
        mOCardProgressBar.setVisibility(View.GONE);
        mOwnedCardRecyclerView.setVisibility(View.VISIBLE);
    }

    private void initTrackingCardRecyclerView() {
        mTrackingCardAdapter = new CardRecyclerViewAdapter(mTrackingGames, this, new CardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Intent detailsIntent = new Intent(getApplicationContext(), GameDetailActivity.class);
                detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
                startActivity(detailsIntent);
            }
        });
        mTrackingCardRecyclerView.setAdapter(mTrackingCardAdapter);
        mTCardProgressBar.setVisibility(View.GONE);
        mTrackingCardRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG,"onLoaderReset");
    }

    /**
     * Fetch upcoming GBGames callback
     */

    @Override
    public void onGamesLoaded(List<Game> games, String error) {
        if(!TextUtils.isEmpty(error)){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }else {
            mDiscoverGames = games;
            mDiscoverGamesLoaded = true;
            initDiscoverCardRecyclerView();
        }
    }

    private void initDiscoverCardRecyclerView() {
        mDiscoverCardAdapter = new CardRecyclerViewAdapter(mDiscoverGames, this, new CardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Intent detailsIntent = new Intent(getApplicationContext(), GameDetailActivity.class);
                detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
                startActivity(detailsIntent);
            }
        });
        mDiscoverCardRecyclerView.setAdapter(mDiscoverCardAdapter);
        mDCardProgressBar.setVisibility(View.GONE);
        mDiscoverCardRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(OWNED_LOADER, null, this);
        getSupportLoaderManager().restartLoader(TRACKING_LOADER, null, this);
        super.onResume();
    }
}
