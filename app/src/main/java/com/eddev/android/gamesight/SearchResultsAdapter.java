package com.eddev.android.gamesight;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.eddev.android.gamesight.model.Game;

import java.util.List;

/**
 * Created by Edison on 10/22/2016.
 */

public class SearchResultsAdapter extends ArrayAdapter<Game> {
    public SearchResultsAdapter(Context context, int resource, List<Game> objects) {
        super(context, resource, objects);
    }
}
