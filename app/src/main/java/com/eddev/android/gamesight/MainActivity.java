package com.eddev.android.gamesight;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.discover_card_recycler_view)
    private RecyclerView mDiscoverCardRecyclerView;
    private RecyclerView.Adapter mDiscoverCardAdapter;
    private RecyclerView.LayoutManager mDiscoverCardLayoutManager;
    private Cursor mCursor;

    /**
     * Activity lifecycle
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        ButterKnife.bind(this);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mDiscoverCardRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mDiscoverCardLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mDiscoverCardRecyclerView.setLayoutManager(mDiscoverCardLayoutManager);

        // specify an adapter (see also next example)
        mDiscoverCardAdapter = new CardRecyclerViewAdapter(new String[]{});
        mDiscoverCardRecyclerView.setAdapter(mDiscoverCardAdapter);
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
