package com.eddev.android.gamesight;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eddev.android.gamesight.model.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Edison on 10/22/2016.
 */

public class SearchResultsAdapter extends ArrayAdapter<Game> {
    public SearchResultsAdapter(Context context, int resource, List<Game> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Game game = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_result, parent, false);

        ImageView image = (ImageView) rootView.findViewById(R.id.game_result_thumb);
        Picasso.with(getContext()).load(game.getThumbnailUrl()).into(image);
        TextView titleDate = (TextView)rootView.findViewById(R.id.game_title_date);
        titleDate.setText(game.getName());
        TextView description = (TextView)rootView.findViewById(R.id.game_description);
        description.setText(game.getDescription());

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsIntent = new Intent(getContext(), GameDetailActivity.class);
                detailsIntent.putExtra(getContext().getString(R.string.parcelable_game_key), game);
                getContext().startActivity(detailsIntent);
            }
        });

        return rootView;
    }
}
