package com.eddev.android.gamesight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eddev.android.gamesight.model.Game;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameDetailFragment extends Fragment {

    private Game mGame;


    @BindView(R.id.detail_completion_text_view)
    TextView completionTextView;
    @BindView(R.id.detail_genre_text_view)
    TextView genreTextView;

    public GameDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_detail, container, false);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGame = arguments.getParcelable(getString(R.string.parcelable_game_key));
            loadFullGame();
        }
        return rootView;
    }

    private void loadFullGame() {

    }
}
