package com.eddev.android.gamesight;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.eddev.android.gamesight.model.Game;

import java.util.ArrayList;

public class SearchableActivity extends ListActivity{

    private final String LOG_TAG = SearchableActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            findGames(query);
            Log.d(LOG_TAG, query);//(query);
        }
        setListAdapter(new SearchResultsAdapter(this,0, new ArrayList<Game>()));
    }

    private void findGames(String query) {
    }

    @Override
    public boolean onSearchRequested(){
        Log.d(LOG_TAG, "onSearchRequested()");
        return true;
    }
}
