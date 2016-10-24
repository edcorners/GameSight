package com.eddev.android.gamesight;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.service.GameSearchService;
import com.eddev.android.gamesight.service.GamesLoadedCallback;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, GamesLoadedCallback {

    @BindView(R.id.discover_card_recycler_view)
    RecyclerView mDiscoverCardRecyclerView;
    private RecyclerView.Adapter mDiscoverCardAdapter;
    private RecyclerView.LayoutManager mDiscoverCardLayoutManager;
    private Cursor mCursor;

    private GameSearchService mGameSearchService;

    /**
     * Activity lifecycle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ButterKnife.bind(this);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mDiscoverCardRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mDiscoverCardLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDiscoverCardRecyclerView.setLayoutManager(mDiscoverCardLayoutManager);

        mGameSearchService = new GiantBombSearchService(this);
        mGameSearchService.fetchUpcomingGamesPreview(this);

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
    * Loader
    */

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

    /**
     * Fetch upcoming GBGames callback
     */

    @Override
    public void onGamesLoaded(List<Game> games) {
        mDiscoverCardAdapter = new CardRecyclerViewAdapter(games, this);
        mDiscoverCardRecyclerView.setAdapter(mDiscoverCardAdapter);
    }
}
