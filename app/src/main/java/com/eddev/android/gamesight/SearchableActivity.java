package com.eddev.android.gamesight;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;
import com.eddev.android.gamesight.service.GiantBombSearchService;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends ListActivity implements IGamesLoadedCallback {

    private final String LOG_TAG = SearchableActivity.class.getSimpleName();
    private IGameSearchService mIGameSearchService;
    private SearchResultsAdapter mSearchResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIGameSearchService = new GiantBombSearchService(this);
        mSearchResultsAdapter = new SearchResultsAdapter(this, 0, new ArrayList<Game>());
        setListAdapter(mSearchResultsAdapter);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(LOG_TAG, query);
            mIGameSearchService.searchGamesByName(query, this);
        }
    }

    private void findGames(String query) {

    }

    public void back(View view){
        finish();
    }

    @Override
    public boolean onSearchRequested(){
        Log.d(LOG_TAG, "onSearchRequested()");
        return true;
    }

    @Override
    public void onGamesLoaded(List<Game> games) {
        mSearchResultsAdapter.addAll(games);
    }
}
