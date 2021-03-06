package com.eddev.android.gamesight.presenter;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eddev.android.gamesight.AnalyticsApplication;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.presenter.adapter.SearchRecyclerViewAdapter;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchableActivity extends AppCompatActivity implements IGamesLoadedCallback {

    private final String LOG_TAG = SearchableActivity.class.getSimpleName();
    private IGameSearchService mIGameSearchService;

    @BindView(R.id.search_results_recycler_view)
    RecyclerView mSearchResultsRecyclerView;
    @BindView(R.id.search_progress_bar)
    ProgressBar mSearchProgressBar;
    @BindView(R.id.search_empty_text_view)
    TextView mEmptyTextView;

    private RecyclerView.Adapter mSearchRecyclerViewAdapter;
    private RecyclerView.LayoutManager mSearchRecyclerViewLayoutManager;
    private List<Game> mResults;
    private boolean mResultsLoaded = false;
    private String mQuery;
    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initActivityTransitions();

        final View rootView = getLayoutInflater().inflate(R.layout.activity_search, null);
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AppCompatActivity activity = (AppCompatActivity) SearchableActivity.this;
                    activity.supportStartPostponedEnterTransition();
                }
                return false;
            }
        });
        ButterKnife.bind(this);
        mIGameSearchService = new GiantBombSearchService(this);
        mSearchResultsRecyclerView.setHasFixedSize(true);
        mSearchRecyclerViewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSearchResultsRecyclerView.setLayoutManager(mSearchRecyclerViewLayoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null){
            mResults = savedInstanceState.getParcelableArrayList("results");
            mQuery = savedInstanceState.getString("query");
            onGamesLoaded(mResults, null);
            getSupportActionBar().setTitle("Search: "+mQuery);
        }else{
            handleIntent(getIntent());
        }

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode transition = new Explode();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, getString(R.string.setting_screen_name_text) + getString(R.string.search_screen_name));
        mTracker.setScreenName(getString(R.string.search_screen_name));
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            mQuery = query;
            getSupportActionBar().setTitle("Search: "+query);
            Log.d(LOG_TAG, query);
            mIGameSearchService.searchGamesByName(query, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mResultsLoaded) {
            outState.putParcelableArrayList("results", (ArrayList<? extends Parcelable>) mResults);
            outState.putString("query", mQuery);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSearchRequested(){
        Log.d(LOG_TAG, "onSearchRequested()");
        return true;
    }

    @Override
    public void onGamesLoaded(List<Game> games, String error) {
        mResults = games;
        mResultsLoaded = true;
        if(TextUtils.isEmpty(error)) {
            if(!games.isEmpty()){
                mEmptyTextView.setVisibility(View.GONE);
                mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(mResults, this, new SearchRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Game game) {
                        Intent detailsIntent = new Intent(getApplicationContext(), GameDetailActivity.class);
                        detailsIntent.putExtra(getString(R.string.parcelable_game_key), game);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchableActivity.this);
                        startActivity(detailsIntent, options.toBundle());
                    }
                });
                mSearchResultsRecyclerView.setAdapter(mSearchRecyclerViewAdapter);
            }else{
                mEmptyTextView.setText(R.string.no_search_results);
                mEmptyTextView.setVisibility(View.VISIBLE);
            }
        }else{
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            mEmptyTextView.setVisibility(View.VISIBLE);
        }
        mSearchProgressBar.setVisibility(View.GONE);
    }

}
