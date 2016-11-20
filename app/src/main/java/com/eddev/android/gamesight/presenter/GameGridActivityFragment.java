package com.eddev.android.gamesight.presenter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eddev.android.gamesight.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameGridActivityFragment extends Fragment {

    public GameGridActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_grid, container, false);
    }
}
