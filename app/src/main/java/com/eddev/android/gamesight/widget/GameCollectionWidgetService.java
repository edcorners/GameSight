package com.eddev.android.gamesight.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.data.GameColumns;
import com.eddev.android.gamesight.data.GameSightProvider;
import com.eddev.android.gamesight.model.Game;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by ed on 11/25/16.
 */
public class GameCollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
/**
 * This is the factory that will provide data to the collection widget.
 */
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    public void onCreate() {
    }

    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }
    public int getCount() {
        return mCursor.getCount();
    }
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        int itemId = R.layout.widget_collection_item;
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), itemId);
        final Game current = new Game(mCursor);

        try {
            Bitmap b = Picasso.with(mContext).load(current.getImageUrl()).get();
            rv.setImageViewBitmap(R.id.widget_item_thumb, b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rv.setTextViewText(R.id.widget_scrim_text, GiantBombUtility.shortDateFormat.format(current.getReleaseDate()));

        Intent detailsIntent = new Intent();
        Bundle extras = new Bundle();
        extras.putParcelable(mContext.getString(R.string.parcelable_game_key), current);
        detailsIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item_layout, detailsIntent);

        return rv;
    }
    public RemoteViews getLoadingView() {
        // We aren't going to return a default loading view in this sample
        return null;
    }
    public int getViewTypeCount() {
        return 1;
    }
    public long getItemId(int position) {
        return position;
    }
    public boolean hasStableIds() {
        return true;
    }
    public void onDataSetChanged() {
        // Refresh the cursor
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(GameSightProvider.Games.CONTENT_URI,
                Game.GAME_PROJECTION,
                GameColumns.COLLECTION + "=?",
                new String[]{Game.TRACKING},
                null);
    }
}
