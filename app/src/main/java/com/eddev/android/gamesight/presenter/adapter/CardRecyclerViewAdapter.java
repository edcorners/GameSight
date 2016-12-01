package com.eddev.android.gamesight.presenter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eddev.android.gamesight.GiantBombUtility;
import com.eddev.android.gamesight.R;
import com.eddev.android.gamesight.model.Game;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Edison on 10/10/2016.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder>{
    private List<Game> mDataset;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Game game, View view);
    }

    private final OnItemClickListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardRecyclerViewAdapter(List<Game> gameList, Context context, OnItemClickListener listener) {
        mDataset = gameList;
        mContext = context;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_thumbnail, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset.get(position));
        holder.bind(mDataset.get(position), mListener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mGameCoverImageView;
        public ViewHolder(View v) {
            super(v);
            mGameCoverImageView = (ImageView) v.findViewById(R.id.card_item_thumb);
        }

        public void bind(final Game game, final OnItemClickListener listener) {
            Picasso.with(itemView.getContext())
                    .load(game.getThumbnailUrl())
                    .placeholder(R.color.mainBackground)
                    .error(R.color.mainBackground)
                    .into(mGameCoverImageView,new Callback() {
                        @Override public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) mGameCoverImageView.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette);
                                }
                            });
                        }

                        @Override public void onError() {}
                    });
            String contentDescription = itemView.getContext().getString(R.string.game_thumb_content_description);
            String collectionName = itemView.getContext().getString(GiantBombUtility.getTitle(game.getCollection()));
            mGameCoverImageView.setContentDescription(String.format(contentDescription, game.getName(), collectionName));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(game, v);
                }
            });
        }

        private void applyPalette(Palette palette) {
            int primary = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);
            mGameCoverImageView.setBackgroundColor(palette.getDominantColor(primary));
        }
    }


}
