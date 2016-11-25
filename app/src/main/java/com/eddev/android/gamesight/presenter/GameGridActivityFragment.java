package com.eddev.android.gamesight.presenter;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.data.ClassificationAttributeColumns;
import com.eddev.android.gamesight.data.GameColumns;
import com.eddev.android.gamesight.data.GameSightDatabase;
import com.eddev.android.gamesight.data.GameSightProvider;
import com.eddev.android.gamesight.model.ClassificationAttribute;
import com.eddev.android.gamesight.model.Game;
import com.eddev.android.gamesight.presenter.adapter.GridRecyclerViewAdapter;
import com.eddev.android.gamesight.service.GiantBombSearchService;
import com.eddev.android.gamesight.service.IGameSearchService;
import com.eddev.android.gamesight.service.callback.IGamesLoadedCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameGridActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        IGamesLoadedCallback, FilterByConsoleDialog.FilterByConsoleDialogListener {

    private static final String FILTER_BY_CONSOLE_DIALOG = "FBCD";
    public static final String FILTER_SELECTED_ITEMS = "filterSelectedItems";
    public static final String LOADED_GAMES_KEY = "loadedGames";

    private final String LOG_TAG = GameGridActivityFragment.class.getSimpleName();

    public static final int DISCOVER_RESULTS_LIMIT = 20;
    private static final int TRACKING_GRID_LOADER = 10;
    private static final int OWNED_GRID_LOADER = 11;
    private static final int GAME_BY_CONSOLE_LOADER = 12;

    @BindView(R.id.grid_recycler_view)
    RecyclerView mRecyclerView;
    private GridRecyclerViewAdapter mRecyclerViewAdapter;
    @BindView(R.id.grid_progress_bar)
    ProgressBar mProgressBar;

    private IGameSearchService mIGameSearchService;
    private String mCollection = null;

    private List<Game> mGames = new ArrayList<>();
    private boolean mScrimEnabled = false;
    private ArrayList<String> mConsoleFilterSelectedItems;
    private String mFilterPlatformIds = null;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when a game has been selected.
         */
        public void onItemSelected(Game game);
    }

    public GameGridActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_game_grid, container, false);
        ButterKnife.bind(this, root);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mCollection = arguments.getString(getString(R.string.collection_key));
            if(savedInstanceState != null) {
                mGames = savedInstanceState.getParcelableArrayList(LOADED_GAMES_KEY);
                mConsoleFilterSelectedItems = new ArrayList<>(Arrays.asList(GiantBombUtility.getConsolesArrayResourceAsStringArray(getContext())));
                //mConsoleFilterSelectedItems = savedInstanceState.getStringArrayList(FILTER_SELECTED_ITEMS);
                initRecyclerView();
            }else{
                if (mCollection.equals(Game.DISCOVER)) {
                    mIGameSearchService = new GiantBombSearchService(getContext());
                    mIGameSearchService.fetchUpcomingGamesPreview(this, DISCOVER_RESULTS_LIMIT);
                }else if(mCollection.equals(Game.TRACKING)){
                    getActivity().getSupportLoaderManager().initLoader(TRACKING_GRID_LOADER, null, this);
                }
                else if(mCollection.equals(Game.OWNED)){
                    getActivity().getSupportLoaderManager().initLoader(OWNED_GRID_LOADER, null, this);
                }
                mConsoleFilterSelectedItems = new ArrayList<>(Arrays.asList(GiantBombUtility.getConsolesArrayResourceAsStringArray(getContext())));
            }
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.collection_key), mCollection);
        outState.putParcelableArrayList(LOADED_GAMES_KEY, (ArrayList<? extends Parcelable>) mGames);
        outState.putStringArrayList(FILTER_SELECTED_ITEMS, mConsoleFilterSelectedItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_game_grid, menu);
        if(mCollection.equals(Game.TRACKING)) {
            menu.getItem(1).setVisible(true);
        }else if(mCollection.equals(Game.OWNED)) {
            menu.getItem(2).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ((id == R.id.action_show_date || id == R.id.action_show_progress) && mGames.size() > 0) {
            if(!mScrimEnabled) {
                updateScrimVisibility(true);
            }else{
                updateScrimVisibility(false);
            }
            return true;
        }else if(id == R.id.action_filter){
            FilterByConsoleDialog dialog = new FilterByConsoleDialog();
            dialog.setListener(this);
            dialog.setSelectedItems(mConsoleFilterSelectedItems);
            dialog.show(getActivity().getSupportFragmentManager(), FILTER_BY_CONSOLE_DIALOG);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateScrimVisibility(boolean visible) {
        List<Game> games = mRecyclerViewAdapter.getDataset();
        for (Game game : games) {
            game.setScrimVisible(visible);
        }
        mRecyclerViewAdapter.setDataset(games);
        mRecyclerViewAdapter.notifyDataSetChanged();
        mScrimEnabled = visible;
    }

    @Override
    public void onGamesLoaded(List<Game> games, String error) {
        if(!TextUtils.isEmpty(error)){
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        }else {
            mGames = games;
            initRecyclerView();
            setMenuVisibility(true);
        }
    }

    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new GridRecyclerViewAdapter(mGames, getContext(), new GridRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                ((Callback) getActivity()).onItemSelected(game);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        CursorLoader cursorLoader = null;
        switch (id) {
            case TRACKING_GRID_LOADER:
                Log.d(LOG_TAG, "onCreateLoader TRACKING_GRID_LOADER");
                cursorLoader = new CursorLoader(getContext(),
                        GameSightProvider.Games.CONTENT_URI,
                        Game.GAME_PROJECTION,
                        GameColumns.COLLECTION + "=?",
                        new String[]{Game.TRACKING},
                        null);
                break;
            case OWNED_GRID_LOADER:
                Log.d(LOG_TAG, "onCreateLoader OWNED_GRID_LOADER");
                cursorLoader = new CursorLoader(getContext(),
                        GameSightProvider.Games.CONTENT_URI,
                        Game.GAME_PROJECTION,
                        GameColumns.COLLECTION + "=?",
                        new String[]{Game.OWNED},
                        null);
                break;
            case GAME_BY_CONSOLE_LOADER:
                Log.d(LOG_TAG, "onCreateLoader GAME_BY_CONSOLE_LOADER");
                cursorLoader = new CursorLoader(getContext(),
                        GameSightProvider.Games.withClassification(ClassificationAttribute.PLATFORM) ,
                        Game.GAME_PROJECTION,
                        GameSightDatabase.GAMES +"."+ GameColumns.COLLECTION +" =? and "+
                        GameSightDatabase.CLASSIFICATION_ATTRIBUTES +"."+ ClassificationAttributeColumns.CLASSIFICATION_ID + " IN ("+mFilterPlatformIds+")",
                        new String[]{mCollection},
                        null);
                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            switch (loader.getId()) {
                case TRACKING_GRID_LOADER:
                    Log.d(LOG_TAG, "LoadFinished TRACKING_GRID_LOADER ");
                    onGamesLoaderFinished(data);
                    break;
                case OWNED_GRID_LOADER:
                    Log.d(LOG_TAG, "LoadFinished OWNED_GRID_LOADER ");
                    onGamesLoaderFinished(data);
                    break;
                case GAME_BY_CONSOLE_LOADER:
                    Log.d(LOG_TAG, "LoadFinished GAME_BY_CONSOLE_LOADER ");
                    List<Game> filteredGames = new ArrayList<>();
                    data.moveToPosition(-1); //Rewind cursor
                    while (data.moveToNext()){
                        Game current = new Game(data);
                        current.setScrimVisible(mScrimEnabled);
                        filteredGames.add(current);
                    }
                    mRecyclerViewAdapter.setDataset(filteredGames);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    break;
            }
            setMenuVisibility(true);
        }
    }

    private void onGamesLoaderFinished(Cursor data) {
        mGames.clear();
        data.moveToPosition(-1); //Rewind cursor
        while (data.moveToNext()) {
            Game current = new Game(data);
            mGames.add(current);
        }
        initRecyclerView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    @Override
    public void onFilterByConsoleClick(ArrayList<String> selectedItems) {
        if(mCollection.equals(Game.DISCOVER)) {
            applyFilter(selectedItems);
        }else{
            mFilterPlatformIds = GiantBombUtility.getPlatformIdsByConsoles(getContext(), selectedItems);
            getActivity().getSupportLoaderManager().restartLoader(GAME_BY_CONSOLE_LOADER, null, this);
        }
    }

    private void applyFilter(ArrayList<String> selectedItems) {
        mConsoleFilterSelectedItems = selectedItems;
        List<Game> filteredGames = new ArrayList<>();
        for (Game current : mGames) {
            if (current.isForAnyOfConsoles(getContext(), mConsoleFilterSelectedItems)) {
                filteredGames.add(current);
            }
        }
        mRecyclerViewAdapter.setDataset(filteredGames);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelFilterClick() {

    }
}
